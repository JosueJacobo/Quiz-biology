package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.models.LevelProgress
import com.example.data.models.Question
import com.example.data.models.User

@Database(
    entities = [
        User::class,
        LevelProgress::class,
        Question::class
    ],
    version = 1,
    exportSchema = false
)
abstract class BioQuizDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun levelProgressDao(): LevelProgressDao
    abstract fun questionDao(): QuestionDao

    companion object {
        @Volatile
        private var INSTANCE: BioQuizDatabase? = null

        fun getDatabase(context: Context): BioQuizDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BioQuizDatabase::class.java,
                    "bioquiz_realm_db"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}
