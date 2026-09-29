package com.example.data.questions.generator

import com.example.data.models.Question

object LevelQuestionBank {

    /**
     * Returns a guaranteed bank of at least 50 comprehensive, verified biological questions
     * for any requested level from 1 to 100.
     * All questions focus exclusively on factual biology and botany (physiology, anatomy,
     * ecology, taxonomy, biochemistry) with 0 questions on evolution.
     */
    fun getQuestionsForLevel(levelNumber: Int): List<Question> {
        val normalizedLevel = levelNumber.coerceIn(1, 100)
        return when (normalizedLevel) {
            in 1..20 -> Biome1BotanyBank.getQuestions(normalizedLevel)
            in 21..40 -> Biome2InvertebratesBank.getQuestions(normalizedLevel)
            in 41..60 -> Biome3VertebratesBank.getQuestions(normalizedLevel)
            in 61..80 -> Biome4PhysiologyBank.getQuestions(normalizedLevel)
            in 81..100 -> Biome5EcologyBank.getQuestions(normalizedLevel)
            else -> Biome1BotanyBank.getQuestions(1)
        }
    }

    /**
     * Total number of questions guaranteed per level.
     */
    const val MINIMUM_QUESTIONS_PER_LEVEL = 50
}
