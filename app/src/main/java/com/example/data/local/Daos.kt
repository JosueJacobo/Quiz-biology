package com.example.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.models.LevelProgress
import com.example.data.models.Question
import com.example.data.models.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    fun getUserFlow(email: String): Flow<User?>

    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): User?

    @Query("SELECT * FROM users ORDER BY totalScore DESC")
    fun getAllUsersFlow(): Flow<List<User>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Update
    suspend fun updateUser(user: User)
}

@Dao
interface LevelProgressDao {
    @Query("SELECT * FROM level_progress WHERE userEmail = :userEmail")
    fun getAllProgressForUser(userEmail: String): Flow<List<LevelProgress>>

    @Query("SELECT * FROM level_progress WHERE userEmail = :userEmail AND levelNumber = :levelNumber LIMIT 1")
    suspend fun getProgressForLevel(userEmail: String, levelNumber: Int): LevelProgress?

    @Query("SELECT COALESCE(MAX(levelNumber), 1) FROM level_progress WHERE userEmail = :userEmail AND isCompleted = 1")
    fun getHighestCompletedLevelFlow(userEmail: String): Flow<Int?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateProgress(progress: LevelProgress)

    @Query("DELETE FROM level_progress WHERE userEmail = :userEmail")
    suspend fun resetProgressForUser(userEmail: String)
}

@Dao
interface QuestionDao {
    @Query("SELECT * FROM questions WHERE levelNumber = :levelNumber ORDER BY isCustom DESC, id ASC")
    fun getQuestionsForLevel(levelNumber: Int): Flow<List<Question>>

    @Query("SELECT * FROM questions WHERE levelNumber = :levelNumber")
    suspend fun getQuestionsForLevelSync(levelNumber: Int): List<Question>

    @Query("SELECT * FROM questions WHERE isCustom = 1 ORDER BY id DESC")
    fun getCustomQuestionsFlow(): Flow<List<Question>>

    @Query("SELECT COUNT(*) FROM questions")
    suspend fun getQuestionCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuestion(question: Question): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(questions: List<Question>)

    @Delete
    suspend fun deleteQuestion(question: Question)
}
