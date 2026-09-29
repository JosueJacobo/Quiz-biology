package com.example.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey val email: String,
    val username: String,
    val avatarId: String = "sprout",
    val isCreator: Boolean = false,
    val totalScore: Int = 0,
    val totalStars: Int = 0,
    val levelsCompleted: Int = 0,
    val correctAnswersCount: Int = 0,
    val totalAnsweredCount: Int = 0,
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "level_progress", primaryKeys = ["userEmail", "levelNumber"])
data class LevelProgress(
    val userEmail: String,
    val levelNumber: Int,
    val starsEarned: Int, // 1 to 3
    val highestScore: Int,
    val isCompleted: Boolean = true,
    val completedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "questions")
data class Question(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val levelNumber: Int,
    val category: String, // "Botánica", "Zoología", "Fisiología", "Ecología"
    val questionText: String,
    val optionA: String,
    val optionB: String,
    val optionC: String,
    val optionD: String,
    val correctOptionIndex: Int, // 0 for A, 1 for B, 2 for C, 3 for D
    val explanation: String,
    val isCustom: Boolean = false,
    val authorEmail: String = "sistema"
)

data class LeaderboardPlayer(
    val id: String,
    val name: String,
    val email: String,
    val score: Int,
    val stars: Int,
    val levelReached: Int,
    val avatarEmoji: String,
    val country: String,
    val tier: String,
    val isUser: Boolean = false,
    val rank: Int = 0
)

enum class BiomeWorld(
    val id: Int,
    val title: String,
    val subtitle: String,
    val startLevel: Int,
    val endLevel: Int,
    val iconEmoji: String,
    val themeColorHex: Long
) {
    BOTANY_FUNDAMENTALS(
        id = 1,
        title = "Reino Plantae y Botánica",
        subtitle = "Raíces, hojas, flores, fotosíntesis y tejidos",
        startLevel = 1,
        endLevel = 20,
        iconEmoji = "🌿",
        themeColorHex = 0xFF2E7D32
    ),
    ZOOLOGY_INVERTEBRATES(
        id = 2,
        title = "Zoología: Invertebrados",
        subtitle = "Insectos, arácnidos, moluscos y vida marina",
        startLevel = 21,
        endLevel = 40,
        iconEmoji = "🦋",
        themeColorHex = 0xFF00897B
    ),
    ZOOLOGY_VERTEBRATES(
        id = 3,
        title = "Zoología: Vertebrados",
        subtitle = "Peces, anfibios, reptiles, aves y mamíferos",
        startLevel = 41,
        endLevel = 60,
        iconEmoji = "🦅",
        themeColorHex = 0xFF3949AB
    ),
    ANATOMY_PHYSIOLOGY(
        id = 4,
        title = "Anatomía y Fisiología Comparada",
        subtitle = "Sentidos animales, tropismos y digestión",
        startLevel = 61,
        endLevel = 80,
        iconEmoji = "🔬",
        themeColorHex = 0xFF8E24AA
    ),
    ECOLOGY_MASTERY(
        id = 5,
        title = "Ecología, Simbiosis y Maestría",
        subtitle = "Redes tróficas, mutualismo y desafíos finales",
        startLevel = 81,
        endLevel = 100,
        iconEmoji = "🌲",
        themeColorHex = 0xFF1B5E20
    );

    companion object {
        fun forLevel(level: Int): BiomeWorld {
            return entries.firstOrNull { level in it.startLevel..it.endLevel } ?: BOTANY_FUNDAMENTALS
        }
    }
}
