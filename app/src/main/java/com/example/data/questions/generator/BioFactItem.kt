package com.example.data.questions.generator

import com.example.data.models.Question

data class BioFactItem(
    val name: String,
    val organOrContext: String,
    val compositionOrType: String,
    val functionOrRole: String,
    val keyDetail: String,
    val distractors: List<String>
)

object BioQuestionBuilder {

    fun build50QuestionsForLevel(
        level: Int,
        category: String,
        items: List<BioFactItem>
    ): List<Question> {
        val result = mutableListOf<Question>()
        var counter = 0

        // For each item in the 10 items list, generate 5 distinct perspectives:
        for (itemIndex in items.indices) {
            val item = items[itemIndex]
            val otherItems = items.filterIndexed { index, _ -> index != itemIndex }

            // Perspective 0: Function question
            val distractorFunctions = otherItems.map { it.functionOrRole }.shuffled().take(3)
            result.add(
                createQuestion(
                    level = level,
                    category = category,
                    slot = counter++,
                    questionText = "¿Qué función principal desempeña ${item.name} en ${item.organOrContext}?",
                    correct = item.functionOrRole,
                    w1 = distractorFunctions.getOrElse(0) { "Sintetizar quitina extracelular" },
                    w2 = distractorFunctions.getOrElse(1) { "Secretar néctar azucarado" },
                    w3 = distractorFunctions.getOrElse(2) { "Almacenar glucógeno hepático" },
                    explanation = "${item.name} (${item.organOrContext}) cumple la función esencial de ${item.functionOrRole}. ${item.keyDetail}."
                )
            )

            // Perspective 1: Identification by role
            val distractorNames = if (item.distractors.size >= 3) {
                item.distractors.take(3)
            } else {
                otherItems.map { it.name }.shuffled().take(3)
            }
            result.add(
                createQuestion(
                    level = level,
                    category = category,
                    slot = counter++,
                    questionText = "¿Cómo se denomina la estructura u elemento que se encarga de ${item.functionOrRole} en ${item.organOrContext}?",
                    correct = item.name,
                    w1 = distractorNames.getOrElse(0) { "Parénquima" },
                    w2 = distractorNames.getOrElse(1) { "Epidermis" },
                    w3 = distractorNames.getOrElse(2) { "Mesófilo" },
                    explanation = "Corresponde a ${item.name}, componente clave de ${item.organOrContext} que se encarga de ${item.functionOrRole}."
                )
            )

            // Perspective 2: Composition or structural type
            val distractorCompositions = otherItems.map { it.compositionOrType }.shuffled().take(3)
            result.add(
                createQuestion(
                    level = level,
                    category = category,
                    slot = counter++,
                    questionText = "¿Cuál es la naturaleza o composición característica de ${item.name} (${item.organOrContext})?",
                    correct = item.compositionOrType,
                    w1 = distractorCompositions.getOrElse(0) { "Lípidos fosforilados de membrana" },
                    w2 = distractorCompositions.getOrElse(1) { "Sales de carbonato de calcio puro" },
                    w3 = distractorCompositions.getOrElse(2) { "Glucoproteínas mucilaginosas" },
                    explanation = "${item.name} está constituido(a) o clasificado(a) biológicamente como: ${item.compositionOrType}."
                )
            )

            // Perspective 3: Location or context
            val distractorContexts = otherItems.map { it.organOrContext }.shuffled().take(3)
            result.add(
                createQuestion(
                    level = level,
                    category = category,
                    slot = counter++,
                    questionText = "¿En qué tejido, órgano o región biológica se ubica específicamente ${item.name}?",
                    correct = item.organOrContext,
                    w1 = distractorContexts.getOrElse(0) { "Médula del tallo leñoso" },
                    w2 = distractorContexts.getOrElse(1) { "Cavidad celómica anterior" },
                    w3 = distractorContexts.getOrElse(2) { "Glándulas antenales basales" },
                    explanation = "${item.name} se ubica anatómicamente en ${item.organOrContext}. ${item.keyDetail}."
                )
            )

            // Perspective 4: Key scientific detail / diagnostic property
            val distractorDetails = otherItems.map { it.keyDetail }.shuffled().take(3)
            result.add(
                createQuestion(
                    level = level,
                    category = category,
                    slot = counter++,
                    questionText = "Respecto a ${item.organOrContext}, ¿cuál de las siguientes afirmaciones describe con precisión a ${item.name}?",
                    correct = item.keyDetail,
                    w1 = distractorDetails.getOrElse(0) { "Carece totalmente de conexiones con otros tejidos circundantes" },
                    w2 = distractorDetails.getOrElse(1) { "Solo se activa durante el reposo invernal vegetativo" },
                    w3 = distractorDetails.getOrElse(2) { "Reemplaza al núcleo celular durante la mitosis" },
                    explanation = "Es correcto: ${item.keyDetail}. ${item.name} es vital en ${item.organOrContext}."
                )
            )
        }

        return result
    }

    private fun createQuestion(
        level: Int,
        category: String,
        slot: Int,
        questionText: String,
        correct: String,
        w1: String,
        w2: String,
        w3: String,
        explanation: String
    ): Question {
        // Ensure options are distinct
        val distinctDistractors = linkedSetOf<String>()
        distinctDistractors.add(w1)
        if (w2 != w1) distinctDistractors.add(w2) else distinctDistractors.add("$w2 (secundario)")
        if (w3 != w1 && w3 != w2) distinctDistractors.add(w3) else distinctDistractors.add("$w3 (alternativo)")
        while (distinctDistractors.size < 3) {
            distinctDistractors.add("Estructura residual ${distinctDistractors.size + 1}")
        }
        val distList = distinctDistractors.take(3)

        val correctIndex = slot % 4
        val opts = mutableListOf<String>()
        var distIndex = 0
        for (i in 0..3) {
            if (i == correctIndex) {
                opts.add(correct)
            } else {
                opts.add(distList[distIndex++])
            }
        }

        return Question(
            id = (level.toLong() * 1000L) + slot.toLong(),
            levelNumber = level,
            category = category,
            questionText = questionText,
            optionA = opts[0],
            optionB = opts[1],
            optionC = opts[2],
            optionD = opts[3],
            correctOptionIndex = correctIndex,
            explanation = explanation,
            isCustom = false,
            authorEmail = "sistema"
        )
    }
}
