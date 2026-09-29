package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.models.Question
import com.example.data.models.User
import com.example.ui.theme.BioAmber
import com.example.ui.theme.BioCorrectGreen
import com.example.ui.theme.BioEmerald
import com.example.ui.theme.BioForestGreen

@Composable
fun AddQuestionScreen(
    currentUser: User?,
    customQuestions: List<Question>,
    onLoginAsCreator: () -> Unit,
    onAddQuestion: (
        level: Int,
        category: String,
        questionText: String,
        optA: String,
        optB: String,
        optC: String,
        optD: String,
        correctIdx: Int,
        explanation: String
    ) -> Unit,
    onDeleteQuestion: (Question) -> Unit
) {
    val isCreator = currentUser?.isCreator == true

    var levelNumber by remember { mutableIntStateOf(1) }
    var category by remember { mutableStateOf("Botánica") }
    var questionText by remember { mutableStateOf("") }
    var optionA by remember { mutableStateOf("") }
    var optionB by remember { mutableStateOf("") }
    var optionC by remember { mutableStateOf("") }
    var optionD by remember { mutableStateOf("") }
    var correctOptionIndex by remember { mutableIntStateOf(0) }
    var explanation by remember { mutableStateOf("") }
    var formError by remember { mutableStateOf<String?>(null) }

    val categories = listOf("Botánica", "Zoología", "Fisiología", "Ecología")

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
            .testTag("add_question_screen")
    ) {
        // Top Banner
        item {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.colorScheme.surface,
                shadowElevation = 2.dp
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(BioForestGreen),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(if (isCreator) "✍️" else "🔒", fontSize = 22.sp)
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "Taller de Preguntas",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = BioForestGreen
                            )
                        )
                        Text(
                            text = if (isCreator) "Añade preguntas a cualquier nivel de botánica o zoología" else "Acceso exclusivo para la cuenta del autor",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        // If Not Creator: Show Unlock Card
        if (!isCreator) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = BioAmber.copy(alpha = 0.15f))
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(imageVector = Icons.Default.Lock, contentDescription = null, tint = BioForestGreen, modifier = Modifier.size(36.dp))
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Autorización de Creador Requerida",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Para añadir preguntas a los 100 niveles, debes acceder con la cuenta de autor: emiliojacobg@gmail.com",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(14.dp))
                        Button(
                            onClick = onLoginAsCreator,
                            modifier = Modifier.testTag("unlock_creator_mode_btn"),
                            colors = ButtonDefaults.buttonColors(containerColor = BioForestGreen)
                        ) {
                            Text("Acceder como Emilio (emiliojacobg@gmail.com)")
                        }
                    }
                }
            }
        } else {
            // Form for Creator
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "NUEVA PREGUNTA DIDÁCTICA",
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = BioForestGreen)
                        )
                        Spacer(modifier = Modifier.height(10.dp))

                        // Level Selector (1 to 100)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text(
                                    text = "Asignar a Nivel: $levelNumber de 100",
                                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                                )
                                Text(
                                    text = LevelTitles[levelNumber] ?: "Nivel $levelNumber",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            Row {
                                Button(
                                    onClick = { if (levelNumber > 1) levelNumber-- },
                                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surfaceVariant, contentColor = MaterialTheme.colorScheme.onSurfaceVariant),
                                    modifier = Modifier.size(38.dp)
                                ) {
                                    Text("-", fontWeight = FontWeight.Bold)
                                }
                                Spacer(modifier = Modifier.width(6.dp))
                                Button(
                                    onClick = { if (levelNumber < 100) levelNumber++ },
                                    colors = ButtonDefaults.buttonColors(containerColor = BioForestGreen),
                                    modifier = Modifier.size(38.dp)
                                ) {
                                    Text("+", fontWeight = FontWeight.Bold)
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Category Chips
                        Text(text = "Categoría:", style = MaterialTheme.typography.labelMedium)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            categories.forEach { cat ->
                                FilterChip(
                                    selected = category == cat,
                                    onClick = { category = cat },
                                    label = { Text(cat, fontSize = 12.sp) }
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Question Statement
                        OutlinedTextField(
                            value = questionText,
                            onValueChange = { questionText = it; formError = null },
                            label = { Text("Enunciado de la Pregunta") },
                            placeholder = { Text("Ej: ¿Qué estructura vegetal absorbe agua y nutrientes del suelo?") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("input_question_text"),
                            minLines = 2
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = "Opciones de Respuesta (Marca el círculo de la correcta):",
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold)
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        // Options A, B, C, D
                        val optionInputs = listOf(
                            Triple("A", optionA) { text: String -> optionA = text },
                            Triple("B", optionB) { text: String -> optionB = text },
                            Triple("C", optionC) { text: String -> optionC = text },
                            Triple("D", optionD) { text: String -> optionD = text }
                        )

                        optionInputs.forEachIndexed { index, (letter, value, setter) ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 3.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    selected = correctOptionIndex == index,
                                    onClick = { correctOptionIndex = index },
                                    modifier = Modifier.testTag("radio_correct_$index")
                                )
                                OutlinedTextField(
                                    value = value,
                                    onValueChange = { setter(it); formError = null },
                                    label = { Text("Opción $letter") },
                                    modifier = Modifier
                                        .weight(1f)
                                        .testTag("input_option_$letter"),
                                    singleLine = true
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        // Explanation
                        OutlinedTextField(
                            value = explanation,
                            onValueChange = { explanation = it; formError = null },
                            label = { Text("Explicación Didáctica (¿Por qué es correcta?)") },
                            placeholder = { Text("Ej: Las raíces primarias y sus pelos absorbentes permiten el ingreso osmótico del agua...") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("input_question_explanation"),
                            minLines = 2
                        )

                        if (formError != null) {
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = formError!!,
                                color = MaterialTheme.colorScheme.error,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            onClick = {
                                if (questionText.isBlank() || optionA.isBlank() || optionB.isBlank() || optionC.isBlank() || optionD.isBlank()) {
                                    formError = "Por favor completa la pregunta y las 4 opciones de respuesta."
                                    return@Button
                                }
                                onAddQuestion(
                                    levelNumber,
                                    category,
                                    questionText,
                                    optionA,
                                    optionB,
                                    optionC,
                                    optionD,
                                    correctOptionIndex,
                                    if (explanation.isBlank()) "Respuesta correcta validada por el creador." else explanation
                                )
                                // Clear form
                                questionText = ""
                                optionA = ""
                                optionB = ""
                                optionC = ""
                                optionD = ""
                                explanation = ""
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp)
                                .testTag("submit_new_question_btn"),
                            colors = ButtonDefaults.buttonColors(containerColor = BioForestGreen),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Icon(imageVector = Icons.Default.Add, contentDescription = null)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Guardar Pregunta en el Nivel $levelNumber")
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "PREGUNTAS CREADAS POR TI (${customQuestions.size})",
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = BioForestGreen),
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            // List of custom questions
            if (customQuestions.isEmpty()) {
                item {
                    Text(
                        text = "Aún no has añadido preguntas personalizadas. ¡Usa el formulario superior para añadir preguntas a cualquiera de los 100 niveles!",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                items(customQuestions) { q ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(BioEmerald),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "${q.levelNumber}",
                                    color = Color.White,
                                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = q.questionText,
                                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                                    maxLines = 2
                                )
                                Text(
                                    text = "${q.category} • Correcta: ${when (q.correctOptionIndex) { 0 -> q.optionA; 1 -> q.optionB; 2 -> q.optionC; else -> q.optionD }}",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            IconButton(onClick = { onDeleteQuestion(q) }) {
                                Icon(imageVector = Icons.Default.Delete, contentDescription = "Eliminar", tint = MaterialTheme.colorScheme.error)
                            }
                        }
                    }
                }
            }
        }
    }
}
