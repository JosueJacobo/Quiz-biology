package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.models.LeaderboardPlayer
import com.example.data.models.LevelProgress
import com.example.data.models.Question
import com.example.data.models.User
import com.example.data.repository.BioQuizRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class AppScreen {
    LEVELS_MAP,
    QUIZ_GAME,
    LEADERBOARD,
    ADD_QUESTION,
    PROFILE
}

data class ActiveQuizState(
    val levelNumber: Int = 1,
    val questions: List<Question> = emptyList(),
    val currentIndex: Int = 0,
    val selectedOptionIndex: Int? = null,
    val isAnswerRevealed: Boolean = false,
    val correctAnswersCount: Int = 0,
    val currentPoints: Int = 0,
    val currentStreak: Int = 0,
    val isLevelCompleted: Boolean = false,
    val finalStars: Int = 0
)

class QuizViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = BioQuizRepository(application)

    private val _currentScreen = MutableStateFlow(AppScreen.LEVELS_MAP)
    val currentScreen: StateFlow<AppScreen> = _currentScreen.asStateFlow()

    val currentUser: StateFlow<User?> = repository.getCurrentUserFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val allProgress: StateFlow<List<LevelProgress>> = repository.getAllProgressFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val leaderboard: StateFlow<List<LeaderboardPlayer>> = repository.getLeaderboardFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val customQuestions: StateFlow<List<Question>> = repository.getCustomQuestionsFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _quizState = MutableStateFlow(ActiveQuizState())
    val quizState: StateFlow<ActiveQuizState> = _quizState.asStateFlow()

    private val _showAuthDialog = MutableStateFlow(false)
    val showAuthDialog: StateFlow<Boolean> = _showAuthDialog.asStateFlow()

    private val _snackMessage = MutableStateFlow<String?>(null)
    val snackMessage: StateFlow<String?> = _snackMessage.asStateFlow()

    fun navigateTo(screen: AppScreen) {
        _currentScreen.value = screen
    }

    fun openAuthDialog() {
        _showAuthDialog.value = true
    }

    fun closeAuthDialog() {
        _showAuthDialog.value = false
    }

    fun clearSnack() {
        _snackMessage.value = null
    }

    fun startLevel(levelNumber: Int) {
        viewModelScope.launch {
            val questions = repository.getQuestionsForLevel(levelNumber)
            if (questions.isNotEmpty()) {
                _quizState.value = ActiveQuizState(
                    levelNumber = levelNumber,
                    questions = questions,
                    currentIndex = 0,
                    selectedOptionIndex = null,
                    isAnswerRevealed = false,
                    correctAnswersCount = 0,
                    currentPoints = 0,
                    currentStreak = 0,
                    isLevelCompleted = false,
                    finalStars = 0
                )
                _currentScreen.value = AppScreen.QUIZ_GAME
            } else {
                _snackMessage.value = "No hay preguntas disponibles para el nivel $levelNumber"
            }
        }
    }

    fun selectOption(optionIndex: Int) {
        val current = _quizState.value
        if (current.isAnswerRevealed || current.questions.isEmpty()) return

        val currentQ = current.questions.getOrNull(current.currentIndex) ?: return
        val isCorrect = optionIndex == currentQ.correctOptionIndex

        val newStreak = if (isCorrect) current.currentStreak + 1 else 0
        val pointsToAdd = if (isCorrect) 100 + (newStreak * 25) else 0
        val newCorrectCount = if (isCorrect) current.correctAnswersCount + 1 else current.correctAnswersCount

        _quizState.value = current.copy(
            selectedOptionIndex = optionIndex,
            isAnswerRevealed = true,
            correctAnswersCount = newCorrectCount,
            currentPoints = current.currentPoints + pointsToAdd,
            currentStreak = newStreak
        )
    }

    fun proceedToNextQuestion() {
        val current = _quizState.value
        val nextIndex = current.currentIndex + 1

        if (nextIndex < current.questions.size) {
            _quizState.value = current.copy(
                currentIndex = nextIndex,
                selectedOptionIndex = null,
                isAnswerRevealed = false
            )
        } else {
            // Level completed!
            val total = current.questions.size
            val correct = current.correctAnswersCount
            val stars = when {
                correct == total -> 3
                correct >= (total * 0.6) -> 2
                correct > 0 -> 1
                else -> 0
            }

            _quizState.value = current.copy(
                isLevelCompleted = true,
                finalStars = stars
            )

            // Save progress to database
            viewModelScope.launch {
                repository.recordLevelResult(
                    levelNumber = current.levelNumber,
                    starsEarned = stars,
                    pointsEarned = current.currentPoints,
                    correctCount = correct,
                    totalQuestions = total
                )
            }
        }
    }

    fun repeatLevel() {
        startLevel(_quizState.value.levelNumber)
    }

    fun playNextLevel() {
        val nextLevel = _quizState.value.levelNumber + 1
        if (nextLevel <= 100) {
            startLevel(nextLevel)
        } else {
            _currentScreen.value = AppScreen.LEVELS_MAP
        }
    }

    fun switchUser(email: String, username: String, isCreator: Boolean) {
        viewModelScope.launch {
            repository.switchUser(email, username, isCreator)
            _showAuthDialog.value = false
            _snackMessage.value = "Sesión iniciada como $username"
        }
    }

    fun loginAsCreatorShortcut() {
        switchUser(
            email = "emiliojacobg@gmail.com",
            username = "Emilio (Creador)",
            isCreator = true
        )
    }

    fun addCustomQuestion(
        levelNumber: Int,
        category: String,
        questionText: String,
        optionA: String,
        optionB: String,
        optionC: String,
        optionD: String,
        correctIndex: Int,
        explanation: String
    ) {
        viewModelScope.launch {
            val q = Question(
                levelNumber = levelNumber,
                category = category,
                questionText = questionText.trim(),
                optionA = optionA.trim(),
                optionB = optionB.trim(),
                optionC = optionC.trim(),
                optionD = optionD.trim(),
                correctOptionIndex = correctIndex,
                explanation = explanation.trim(),
                isCustom = true,
                authorEmail = currentUser.value?.email ?: "emiliojacobg@gmail.com"
            )
            repository.addCustomQuestion(q)
            _snackMessage.value = "¡Pregunta añadida con éxito al Nivel $levelNumber!"
            _currentScreen.value = AppScreen.LEVELS_MAP
        }
    }

    fun deleteCustomQuestion(question: Question) {
        viewModelScope.launch {
            repository.deleteCustomQuestion(question)
            _snackMessage.value = "Pregunta eliminada"
        }
    }

    fun resetUserProgress() {
        viewModelScope.launch {
            repository.resetAllProgress()
            _snackMessage.value = "Progreso reiniciado a Nivel 1"
        }
    }
}
