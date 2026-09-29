package com.example.data.repository

import android.content.Context
import com.example.data.local.BioQuizDatabase
import com.example.data.models.LeaderboardPlayer
import com.example.data.models.LevelProgress
import com.example.data.models.Question
import com.example.data.models.User
import com.example.data.questions.DefaultQuestions
import com.example.data.questions.generator.LevelQuestionBank
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BioQuizRepository(context: Context) {
    private val db = BioQuizDatabase.getDatabase(context)
    private val userDao = db.userDao()
    private val levelProgressDao = db.levelProgressDao()
    private val questionDao = db.questionDao()

    private val sharedPrefs = context.getSharedPreferences("bioquiz_prefs", Context.MODE_PRIVATE)

    private val _currentActiveEmail = MutableStateFlow(
        sharedPrefs.getString("active_email", "emiliojacobg@gmail.com") ?: "emiliojacobg@gmail.com"
    )
    val currentActiveEmail: StateFlow<String> = _currentActiveEmail.asStateFlow()

    // Global simulated competitors that populate the real-time leaderboard
    private val simulatedCompetitors = listOf(
        LeaderboardPlayer("p1", "Dra. Flora_Méndez", "flora@botanica.org", 28500, 294, 98, "🌱", "ES", "Guardián de la Biosfera"),
        LeaderboardPlayer("p2", "Prof_Arboretum", "arbor@nature.edu", 27400, 285, 95, "🌲", "MX", "Guardián de la Biosfera"),
        LeaderboardPlayer("p3", "Bio_Camila_Costa", "camila@bio.cr", 26100, 272, 91, "🌺", "CR", "Roble Centenario"),
        LeaderboardPlayer("p4", "Linneo_Verde", "carl@botanic.se", 24800, 258, 86, "🌿", "CL", "Roble Centenario"),
        LeaderboardPlayer("p5", "Eco_Javier", "javi@fauna.ar", 23200, 240, 80, "🦉", "AR", "Roble Centenario"),
        LeaderboardPlayer("p6", "Marina_Oceano", "marina@corales.co", 21500, 225, 75, "🐬", "CO", "Fisiólogo Sabio"),
        LeaderboardPlayer("p7", "Lucas_Entomologo", "lucas@insecta.br", 19800, 210, 70, "🦋", "BR", "Fisiólogo Sabio"),
        LeaderboardPlayer("p8", "Nadia_Plantae", "nadia@herbarium.pe", 18200, 195, 65, "🌻", "PE", "Explorador Silvestre"),
        LeaderboardPlayer("p9", "Zoologo_Andres", "andres@sauropsida.ec", 16400, 180, 60, "🦎", "EC", "Explorador Silvestre"),
        LeaderboardPlayer("p10", "Dendro_Valeria", "val@forest.cl", 14800, 160, 53, "🍃", "CL", "Brote Verde"),
        LeaderboardPlayer("p11", "Diego_Bio", "diego@alumnos.edu", 12100, 135, 45, "🐝", "MX", "Brote Verde"),
        LeaderboardPlayer("p12", "Sofia_Sprout", "sofia@nature.uy", 9500, 110, 37, "🌱", "UY", "Brote Verde"),
        LeaderboardPlayer("p13", "Pablo_Naturaleza", "pablo@eco.org", 6200, 75, 25, "🌾", "ES", "Semilla Curiosa"),
        LeaderboardPlayer("p14", "Clara_Taxonomia", "clara@bio.cl", 3400, 42, 14, "🐞", "CL", "Semilla Curiosa")
    )

    init {
        CoroutineScope(Dispatchers.IO).launch {
            seedQuestionsIfNeeded()
            ensureDefaultUser()
        }
    }

    private suspend fun seedQuestionsIfNeeded() = withContext(Dispatchers.IO) {
        val count = questionDao.getQuestionCount()
        if (count < 100) {
            questionDao.insertAll(DefaultQuestions.allQuestions)
        }
    }

    private suspend fun ensureDefaultUser() = withContext(Dispatchers.IO) {
        val activeEmail = _currentActiveEmail.value
        val existing = userDao.getUserByEmail(activeEmail)
        if (existing == null) {
            val isCreator = activeEmail.equals("emiliojacobg@gmail.com", ignoreCase = true)
            val defaultUser = User(
                email = activeEmail,
                username = if (isCreator) "Emilio (Creador)" else "Naturalista",
                avatarId = if (isCreator) "creator_tree" else "sprout",
                isCreator = isCreator,
                totalScore = 0,
                totalStars = 0,
                levelsCompleted = 0
            )
            userDao.insertUser(defaultUser)
        }
    }

    fun getCurrentUserFlow(): Flow<User?> {
        return userDao.getUserFlow(_currentActiveEmail.value)
    }

    fun getAllProgressFlow(): Flow<List<LevelProgress>> {
        return levelProgressDao.getAllProgressForUser(_currentActiveEmail.value)
    }

    suspend fun getQuestionsForLevel(levelNumber: Int): List<Question> = withContext(Dispatchers.IO) {
        val customQuestions = questionDao.getQuestionsForLevelSync(levelNumber).filter { it.isCustom }
        val bankQuestions = LevelQuestionBank.getQuestionsForLevel(levelNumber)
        // Combine custom questions with bank questions (guarantees >= 50 questions per level)
        customQuestions + bankQuestions
    }

    fun getCustomQuestionsFlow(): Flow<List<Question>> {
        return questionDao.getCustomQuestionsFlow()
    }

    suspend fun addCustomQuestion(question: Question): Long = withContext(Dispatchers.IO) {
        questionDao.insertQuestion(question.copy(isCustom = true, authorEmail = _currentActiveEmail.value))
    }

    suspend fun deleteCustomQuestion(question: Question) = withContext(Dispatchers.IO) {
        questionDao.deleteQuestion(question)
    }

    suspend fun switchUser(email: String, username: String, isCreator: Boolean) = withContext(Dispatchers.IO) {
        var user = userDao.getUserByEmail(email)
        if (user == null) {
            user = User(
                email = email,
                username = username.ifBlank { if (isCreator) "Emilio (Creador)" else "Explorador" },
                avatarId = if (isCreator) "creator_tree" else "sprout",
                isCreator = isCreator || email.equals("emiliojacobg@gmail.com", ignoreCase = true),
                totalScore = 0,
                totalStars = 0,
                levelsCompleted = 0
            )
            userDao.insertUser(user)
        }
        sharedPrefs.edit().putString("active_email", email).apply()
        _currentActiveEmail.value = email
    }

    suspend fun recordLevelResult(
        levelNumber: Int,
        starsEarned: Int,
        pointsEarned: Int,
        correctCount: Int,
        totalQuestions: Int
    ) = withContext(Dispatchers.IO) {
        val email = _currentActiveEmail.value
        val existingProgress = levelProgressDao.getProgressForLevel(email, levelNumber)

        val newBestStars = maxOf(existingProgress?.starsEarned ?: 0, starsEarned)
        val newBestScore = maxOf(existingProgress?.highestScore ?: 0, pointsEarned)

        val progress = LevelProgress(
            userEmail = email,
            levelNumber = levelNumber,
            starsEarned = newBestStars,
            highestScore = newBestScore,
            isCompleted = true
        )
        levelProgressDao.insertOrUpdateProgress(progress)

        // Update User Aggregate Stats
        val user = userDao.getUserByEmail(email) ?: return@withContext
        val allProgress = levelProgressDao.getAllProgressForUser(email).firstOrNull() ?: emptyList()
        val totalStars = allProgress.sumOf { it.starsEarned }
        val totalScore = allProgress.sumOf { it.highestScore }
        val levelsCompleted = allProgress.count { it.isCompleted }

        val updatedUser = user.copy(
            totalScore = totalScore,
            totalStars = totalStars,
            levelsCompleted = levelsCompleted,
            correctAnswersCount = user.correctAnswersCount + correctCount,
            totalAnsweredCount = user.totalAnsweredCount + totalQuestions
        )
        userDao.updateUser(updatedUser)
    }

    suspend fun resetAllProgress() = withContext(Dispatchers.IO) {
        val email = _currentActiveEmail.value
        levelProgressDao.resetProgressForUser(email)
        val user = userDao.getUserByEmail(email)
        if (user != null) {
            userDao.updateUser(
                user.copy(
                    totalScore = 0,
                    totalStars = 0,
                    levelsCompleted = 0,
                    correctAnswersCount = 0,
                    totalAnsweredCount = 0
                )
            )
        }
    }

    // Dynamic real-time combined leaderboard
    fun getLeaderboardFlow(): Flow<List<LeaderboardPlayer>> {
        return combine(getCurrentUserFlow(), getAllProgressFlow()) { currentUser, progressList ->
            val userScore = progressList.sumOf { it.highestScore }
            val userStars = progressList.sumOf { it.starsEarned }
            val userLevel = (progressList.maxOfOrNull { it.levelNumber } ?: 0) + 1

            val userPlayer = LeaderboardPlayer(
                id = "user_${currentUser?.email ?: "guest"}",
                name = currentUser?.username ?: "Tú (Naturalista)",
                email = currentUser?.email ?: "",
                score = userScore,
                stars = userStars,
                levelReached = userLevel.coerceAtMost(100),
                avatarEmoji = if (currentUser?.isCreator == true) "🌳" else "🌿",
                country = "ES",
                tier = calculateTier(userScore),
                isUser = true
            )

            val fullList = (simulatedCompetitors + userPlayer)
                .sortedByDescending { it.score }
                .mapIndexed { index, player ->
                    player.copy(rank = index + 1)
                }

            fullList
        }
    }

    private fun calculateTier(score: Int): String {
        return when {
            score >= 25000 -> "Guardián de la Biosfera"
            score >= 20000 -> "Roble Centenario"
            score >= 15000 -> "Fisiólogo Sabio"
            score >= 8000 -> "Explorador Silvestre"
            score >= 3000 -> "Brote Verde"
            else -> "Semilla Curiosa"
        }
    }
}
