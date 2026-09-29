package com.example.data.questions

import com.example.data.models.Question

object DefaultQuestions {
    val allQuestions: List<Question> by lazy {
        defaultQuestionsPart1 + defaultQuestionsPart2 + defaultQuestionsPart3 + defaultQuestionsPart4
    }
}
