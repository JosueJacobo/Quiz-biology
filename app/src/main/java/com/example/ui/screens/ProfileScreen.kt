package com.example.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.example.data.models.LevelProgress
import com.example.data.models.User
import com.example.ui.theme.BioAmber
import com.example.ui.theme.BioCorrectGreen
import com.example.ui.theme.BioEmerald
import com.example.ui.theme.BioForestGreen
import com.example.ui.theme.BioGoldStar

data class AchievementBadge(
    val id: String,
    val title: String,
    val description: String,
    val iconEmoji: String,
    val isUnlocked: Boolean
)

@Composable
fun ProfileScreen(
    user: User?,
    progressList: List<LevelProgress>,
    onOpenAuthDialog: () -> Unit,
    onResetProgress: () -> Unit
) {
    var showResetConfirm by remember { mutableStateOf(false) }

    val levelsCompleted = progressList.count { it.isCompleted }
    val totalStars = progressList.sumOf { it.starsEarned }
    val totalScore = progressList.sumOf { it.highestScore }
    val totalAnswered = user?.totalAnsweredCount ?: 0
    val totalCorrect = user?.correctAnswersCount ?: 0
    val accuracy = if (totalAnswered > 0) ((totalCorrect * 100) / totalAnswered) else 0

    val badges = listOf(
        AchievementBadge(
            id = "b1",
            title = "Primer Brote",
            description = "Supera el Nivel 1 de Botánica",
            iconEmoji = "🌱",
            isUnlocked = levelsCompleted >= 1
        ),
        AchievementBadge(
            id = "b2",
            title = "Maestro de la Clorofila",
            description = "Supera los primeros 10 niveles de plantas",
            iconEmoji = "🌿",
            isUnlocked = levelsCompleted >= 10
        ),
        AchievementBadge(
            id = "b3",
            title = "Entomólogo de Campo",
            description = "Alcanza el Nivel 30 en Invertebrados",
            iconEmoji = "🦋",
            isUnlocked = levelsCompleted >= 30
        ),
        AchievementBadge(
            id = "b4",
            title = "Explorador de Vertebrados",
            description = "Alcanza el Nivel 50 de Zoología",
            iconEmoji = "🦅",
            isUnlocked = levelsCompleted >= 50
        ),
        AchievementBadge(
            id = "b5",
            title = "Sabio Fisiólogo",
            description = "Alcanza el Nivel 80 de Sistemas Vivos",
            iconEmoji = "🔬",
            isUnlocked = levelsCompleted >= 80
        ),
        AchievementBadge(
            id = "b6",
            title = "Guardián de la Biosfera",
            description = "Completa los 100 niveles del quiz",
            iconEmoji = "👑",
            isUnlocked = levelsCompleted >= 100
        )
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
            .testTag("profile_screen")
    ) {
        // User Profile Header Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(56.dp)
                                .clip(CircleShape)
                                .background(BioForestGreen),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = if (user?.isCreator == true) "🌳" else "🌱",
                                fontSize = 30.sp
                            )
                        }

                        Spacer(modifier = Modifier.width(14.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = user?.username ?: "Naturalista",
                                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                                )
                                if (user?.isCreator == true) {
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Surface(
                                        shape = RoundedCornerShape(8.dp),
                                        color = BioAmber.copy(alpha = 0.2f)
                                    ) {
                                        Text(
                                            text = "👑 Creador",
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = BioForestGreen)
                                        )
                                    }
                                }
                            }
                            Text(
                                text = user?.email ?: "Sin cuenta vinculada",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        IconButton(onClick = onOpenAuthDialog) {
                            Icon(imageVector = Icons.Default.Edit, contentDescription = "Cambiar cuenta")
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Button(
                        onClick = onOpenAuthDialog,
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primaryContainer, contentColor = MaterialTheme.colorScheme.onPrimaryContainer),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Cambiar Cuenta o Iniciar con Creador")
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

        // Stats Overview Row
        item {
            Text(
                text = "ESTADÍSTICAS DE APRENDIZAJE",
                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = BioForestGreen),
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                StatCard(
                    modifier = Modifier.weight(1f),
                    title = "Niveles",
                    value = "$levelsCompleted / 100",
                    emoji = "🗺️"
                )
                StatCard(
                    modifier = Modifier.weight(1f),
                    title = "Estrellas",
                    value = "$totalStars / 300",
                    emoji = "⭐"
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                StatCard(
                    modifier = Modifier.weight(1f),
                    title = "Puntuación",
                    value = "$totalScore pts",
                    emoji = "🏆"
                )
                StatCard(
                    modifier = Modifier.weight(1f),
                    title = "Precisión",
                    value = "$accuracy%",
                    emoji = "🎯"
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "INSIGNIAS Y LOGROS BIOLÓGICOS",
                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = BioForestGreen),
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        // Badges List
        items(badges) { badge ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (badge.isUnlocked) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                ),
                elevation = CardDefaults.cardElevation(if (badge.isUnlocked) 1.dp else 0.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(if (badge.isUnlocked) BioEmerald.copy(alpha = 0.15f) else MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(badge.iconEmoji, fontSize = 22.sp)
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = badge.title,
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = if (badge.isUnlocked) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.outline
                            )
                        )
                        Text(
                            text = badge.description,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    if (badge.isUnlocked) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = "Desbloqueado",
                            tint = BioCorrectGreen,
                            modifier = Modifier.size(22.dp)
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = "Bloqueado",
                            tint = MaterialTheme.colorScheme.outline,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }

        // Reset Section
        item {
            Spacer(modifier = Modifier.height(24.dp))
            OutlinedButton(
                onClick = { showResetConfirm = true },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.error)
            ) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("Reiniciar Todo Mi Progreso")
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }

    if (showResetConfirm) {
        AlertDialog(
            onDismissRequest = { showResetConfirm = false },
            title = { Text("¿Reiniciar Progreso?") },
            text = { Text("Esta acción restablecerá todas las estrellas y niveles completados para empezar la ruta desde el Nivel 1.") },
            confirmButton = {
                Button(
                    onClick = {
                        onResetProgress()
                        showResetConfirm = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) {
                    Text("Sí, Reiniciar")
                }
            },
            dismissButton = {
                OutlinedButton(onClick = { showResetConfirm = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}

@Composable
fun StatCard(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    emoji: String
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(emoji, fontSize = 24.sp)
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = value,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
            }
        }
    }
}
