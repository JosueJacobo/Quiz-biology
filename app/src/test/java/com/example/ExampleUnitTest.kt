package com.example

import org.junit.Assert.*
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
  @Test
  fun addition_isCorrect() {
    assertEquals(4, 2 + 2)
  }

  @Test
  fun all100LevelsAreConfigured() {
    val questions = com.example.data.questions.DefaultQuestions.allQuestions
    assertEquals(100, questions.size)
    val distinctLevels = questions.map { it.levelNumber }.distinct()
    assertEquals(100, distinctLevels.size)
    questions.forEach { q ->
      assertTrue(q.questionText.isNotBlank())
      assertTrue(q.optionA.isNotBlank())
      assertTrue(q.optionB.isNotBlank())
      assertTrue(q.optionC.isNotBlank())
      assertTrue(q.optionD.isNotBlank())
      assertTrue(q.correctOptionIndex in 0..3)
      assertTrue(q.explanation.isNotBlank())
    }
  }

  @Test
  fun everyLevelHasAtLeast50Questions() {
    var totalQuestionsCount = 0
    for (level in 1..100) {
      val questions = com.example.data.questions.generator.LevelQuestionBank.getQuestionsForLevel(level)
      assertTrue(
        "Level $level must have at least 50 questions, but had ${questions.size}",
        questions.size >= 50
      )
      totalQuestionsCount += questions.size

      // Verify the questions in the level
      questions.forEach { q ->
        assertEquals(level, q.levelNumber)
        assertTrue(q.questionText.isNotBlank())
        assertTrue(q.optionA.isNotBlank())
        assertTrue(q.optionB.isNotBlank())
        assertTrue(q.optionC.isNotBlank())
        assertTrue(q.optionD.isNotBlank())
        assertTrue("Option index ${q.correctOptionIndex} out of bounds", q.correctOptionIndex in 0..3)
        assertTrue(q.explanation.isNotBlank())
      }
    }

    assertTrue("Expected at least 5000 total questions across all 100 levels", totalQuestionsCount >= 5000)
    assertEquals(5000, totalQuestionsCount)
  }
}

