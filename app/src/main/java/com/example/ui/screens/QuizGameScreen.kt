package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.models.Question
import com.example.ui.theme.BioAmber
import com.example.ui.theme.BioCorrectGreen
import com.example.ui.theme.BioCorrectGreenLight
import com.example.ui.theme.BioEmerald
import com.example.ui.theme.BioForestGreen
import com.example.ui.theme.BioGoldStar
import com.example.ui.theme.BioWrongRed
import com.example.ui.theme.BioWrongRedLight
import com.example.ui.viewmodel.ActiveQuizState

@Composable
fun QuizGameScreen(
    state: ActiveQuizState,
    onSelectOption: (Int) -> Unit,
    onProceedNext: () -> Unit,
    onRepeatLevel: () -> Unit,
    onPlayNextLevel: () -> Unit,
    onExitQuiz: () -> Unit
) {
    BackHandler {
        onExitQuiz()
    }

    val currentQ = state.questions.getOrNull(state.currentIndex)
    val totalQ = state.questions.size
    val levelTitle = LevelTitles[state.levelNumber] ?: "Nivel ${state.levelNumber}"

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // Header Bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(
                        onClick = onExitQuiz,
                        modifier = Modifier.testTag("quiz_back_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver al mapa"
                        )
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "Nivel ${state.levelNumber}",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = BioForestGreen
                                )
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            val tier = com.example.data.models.DifficultyTier.forLevel(state.levelNumber)
                            Surface(
                                color = Color(tier.colorHex).copy(alpha = 0.15f),
                                shape = RoundedCornerShape(4.dp)
                            ) {
                                Text(
                                    text = "${tier.iconEmoji} ${tier.shortName}",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 10.sp
                                    ),
                                    color = Color(tier.colorHex),
                                    modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp)
                                )
                            }
                        }
                        Text(
                            text = levelTitle,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                // Points & Streak Chip
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (state.currentStreak > 1) {
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = BioAmber.copy(alpha = 0.2f),
                            modifier = Modifier.padding(end = 8.dp)
                        ) {
                            Text(
                                text = "🔥 x${state.currentStreak}",
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold)
                            )
                        }
                    }
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = MaterialTheme.colorScheme.primaryContainer
                    ) {
                        Text(
                            text = "+${state.currentPoints} pts",
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Progress Bar of Questions
            val progressVal = if (totalQ > 0) ((state.currentIndex + 1).toFloat() / totalQ) else 0f
            LinearProgressIndicator(
                progress = { progressVal },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp)),
                color = BioEmerald,
                trackColor = MaterialTheme.colorScheme.surfaceVariant
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (currentQ != null) {
                // Question Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = MaterialTheme.colorScheme.surfaceVariant
                            ) {
                                Text(
                                    text = "Pregunta ${state.currentIndex + 1} de $totalQ",
                                    style = MaterialTheme.typography.labelSmall,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }

                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = BioEmerald.copy(alpha = 0.15f)
                            ) {
                                Text(
                                    text = "🌿 ${currentQ.category}",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = BioForestGreen,
                                        fontWeight = FontWeight.SemiBold
                                    ),
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        Text(
                            text = currentQ.questionText,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.SemiBold,
                                lineHeight = 24.sp
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Options List
                val options = listOf(
                    0 to currentQ.optionA,
                    1 to currentQ.optionB,
                    2 to currentQ.optionC,
                    3 to currentQ.optionD
                )

                val optionLetters = listOf("A", "B", "C", "D")

                options.forEach { (index, text) ->
                    val isSelected = state.selectedOptionIndex == index
                    val isCorrect = index == currentQ.correctOptionIndex

                    val (containerColor, borderColor, textColor) = when {
                        !state.isAnswerRevealed -> {
                            Triple(
                                MaterialTheme.colorScheme.surface,
                                MaterialTheme.colorScheme.outline.copy(alpha = 0.2f),
                                MaterialTheme.colorScheme.onSurface
                            )
                        }
                        isCorrect -> {
                            Triple(BioCorrectGreenLight, BioCorrectGreen, BioCorrectGreen)
                        }
                        isSelected && !isCorrect -> {
                            Triple(BioWrongRedLight, BioWrongRed, BioWrongRed)
                        }
                        else -> {
                            Triple(
                                MaterialTheme.colorScheme.surface.copy(alpha = 0.6f),
                                MaterialTheme.colorScheme.outline.copy(alpha = 0.15f),
                                MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                            )
                        }
                    }

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 5.dp)
                            .testTag("option_button_$index")
                            .clip(RoundedCornerShape(14.dp))
                            .border(
                                width = if (state.isAnswerRevealed && (isCorrect || isSelected)) 2.dp else 1.dp,
                                color = borderColor,
                                shape = RoundedCornerShape(14.dp)
                            )
                            .clickable(enabled = !state.isAnswerRevealed) {
                                onSelectOption(index)
                            },
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = containerColor),
                        elevation = CardDefaults.cardElevation(if (isSelected || isCorrect) 2.dp else 0.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(CircleShape)
                                    .background(
                                        when {
                                            state.isAnswerRevealed && isCorrect -> BioCorrectGreen
                                            state.isAnswerRevealed && isSelected -> BioWrongRed
                                            else -> MaterialTheme.colorScheme.surfaceVariant
                                        }
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                if (state.isAnswerRevealed) {
                                    if (isCorrect) {
                                        Icon(
                                            imageVector = Icons.Default.Check,
                                            contentDescription = null,
                                            tint = Color.White,
                                            modifier = Modifier.size(18.dp)
                                        )
                                    } else if (isSelected) {
                                        Icon(
                                            imageVector = Icons.Default.Close,
                                            contentDescription = null,
                                            tint = Color.White,
                                            modifier = Modifier.size(18.dp)
                                        )
                                    } else {
                                        Text(
                                            text = optionLetters[index],
                                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
                                        )
                                    }
                                } else {
                                    Text(
                                        text = optionLetters[index],
                                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Text(
                                text = text,
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = if (isSelected || isCorrect) FontWeight.Bold else FontWeight.Normal,
                                    color = textColor
                                ),
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }

                // Didactic Explanation Card when Answer is Revealed
                AnimatedVisibility(
                    visible = state.isAnswerRevealed,
                    enter = fadeIn() + slideInVertically()
                ) {
                    Column(modifier = Modifier.padding(top = 14.dp)) {
                        Surface(
                            shape = RoundedCornerShape(16.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
                            border = CardDefaults.outlinedCardBorder()
                        ) {
                            Row(
                                modifier = Modifier.padding(14.dp),
                                verticalAlignment = Alignment.Top
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Lightbulb,
                                    contentDescription = "Nota Didáctica",
                                    tint = BioAmber,
                                    modifier = Modifier.size(24.dp)
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Column {
                                    Text(
                                        text = "Fundamento Biológico:",
                                        style = MaterialTheme.typography.labelMedium.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = BioForestGreen
                                        )
                                    )
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = currentQ.explanation,
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            onClick = onProceedNext,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                                .testTag("quiz_next_button"),
                            shape = RoundedCornerShape(14.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = BioForestGreen)
                        ) {
                            Text(
                                text = if (state.currentIndex + 1 < totalQ) "Siguiente Pregunta" else "Ver Resultado del Nivel",
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                            )
                        }
                    }
                }
            }
        }

        // Level Completed Celebration Dialog
        if (state.isLevelCompleted) {
            AlertDialog(
                onDismissRequest = { /* Force explicit user action */ },
                title = {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = if (state.finalStars > 0) "¡Nivel Completado!" else "¡Sigue Practicando!",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = BioForestGreen
                            ),
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        // Stars display
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            for (i in 1..3) {
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = null,
                                    tint = if (i <= state.finalStars) BioGoldStar else MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
                                    modifier = Modifier.size(36.dp)
                                )
                            }
                        }
                    }
                },
                text = {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Aciertos: ${state.correctAnswersCount} / ${state.questions.size}",
                            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "+${state.currentPoints} Puntos obtenidos",
                            style = MaterialTheme.typography.bodyMedium,
                            color = BioEmerald
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        if (state.finalStars > 0 && state.levelNumber < 100) {
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = BioCorrectGreenLight
                            ) {
                                Text(
                                    text = "🔓 ¡Nivel ${state.levelNumber + 1} desbloqueado en la Ruta!",
                                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold, color = BioForestGreen),
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                                )
                            }
                        }
                    }
                },
                confirmButton = {
                    if (state.finalStars > 0 && state.levelNumber < 100) {
                        Button(
                            onClick = onPlayNextLevel,
                            modifier = Modifier.testTag("quiz_play_next_btn"),
                            colors = ButtonDefaults.buttonColors(containerColor = BioForestGreen)
                        ) {
                            Text("Siguiente Nivel")
                        }
                    } else {
                        Button(
                            onClick = onExitQuiz,
                            modifier = Modifier.testTag("quiz_finish_map_btn"),
                            colors = ButtonDefaults.buttonColors(containerColor = BioForestGreen)
                        ) {
                            Text("Volver al Mapa")
                        }
                    }
                },
                dismissButton = {
                    OutlinedButton(
                        onClick = onRepeatLevel,
                        modifier = Modifier.testTag("quiz_repeat_btn")
                    ) {
                        Icon(imageVector = Icons.Default.Replay, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Reintentar")
                    }
                }
            )
        }
    }
}
