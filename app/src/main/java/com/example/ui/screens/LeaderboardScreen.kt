package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.models.LeaderboardPlayer
import com.example.data.models.User
import com.example.ui.theme.BioAmber
import com.example.ui.theme.BioEmerald
import com.example.ui.theme.BioForestGreen
import com.example.ui.theme.BioGoldStar

@Composable
fun LeaderboardScreen(
    currentUser: User?,
    leaderboard: List<LeaderboardPlayer>
) {
    val top1 = leaderboard.getOrNull(0)
    val top2 = leaderboard.getOrNull(1)
    val top3 = leaderboard.getOrNull(2)

    val userInBoard = leaderboard.firstOrNull { it.isUser }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Header
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.surface,
            shadowElevation = 2.dp
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "Clasificación Global",
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = BioForestGreen
                                )
                            )
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(top = 2.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .clip(CircleShape)
                                    .background(BioEmerald)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "En Tiempo Real • Temporada Botánica",
                                style = MaterialTheme.typography.labelSmall.copy(color = BioEmerald)
                            )
                        }
                    }

                    Icon(
                        imageVector = Icons.Default.EmojiEvents,
                        contentDescription = null,
                        tint = BioAmber,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .testTag("leaderboard_list")
        ) {
            // Podium Card (Top 3)
            item {
                Spacer(modifier = Modifier.height(14.dp))
                PodiumSection(top1 = top1, top2 = top2, top3 = top3)
                Spacer(modifier = Modifier.height(14.dp))
            }

            // User Pinned Card
            if (userInBoard != null) {
                item {
                    Text(
                        text = "TU POSICIÓN EN VIVO",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        ),
                        modifier = Modifier.padding(bottom = 6.dp)
                    )

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(2.dp, BioEmerald, RoundedCornerShape(16.dp))
                            .testTag("current_user_rank_card"),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f))
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Rank Number
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(BioForestGreen),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "#${userInBoard.rank}",
                                    style = MaterialTheme.typography.titleSmall.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                )
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = userInBoard.name,
                                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Surface(
                                        shape = RoundedCornerShape(6.dp),
                                        color = BioForestGreen.copy(alpha = 0.15f)
                                    ) {
                                        Text(
                                            text = "TÚ",
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = BioForestGreen)
                                        )
                                    }
                                }
                                Text(
                                    text = "${userInBoard.tier} • Nivel ${userInBoard.levelReached}/100",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }

                            Column(horizontalAlignment = Alignment.End) {
                                Text(
                                    text = "${userInBoard.score} pts",
                                    style = MaterialTheme.typography.titleSmall.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = BioForestGreen
                                    )
                                )
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(imageVector = Icons.Default.Star, contentDescription = null, tint = BioGoldStar, modifier = Modifier.size(14.dp))
                                    Spacer(modifier = Modifier.width(2.dp))
                                    Text(
                                        text = "${userInBoard.stars} estrellas",
                                        style = MaterialTheme.typography.labelSmall
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(18.dp))
                    Text(
                        text = "TABLA DE LÍDERES COMPLETA",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        ),
                        modifier = Modifier.padding(bottom = 6.dp)
                    )
                }
            }

            // Ranking items
            items(leaderboard) { player ->
                LeaderboardRow(player = player)
                Spacer(modifier = Modifier.height(8.dp))
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
fun PodiumSection(
    top1: LeaderboardPlayer?,
    top2: LeaderboardPlayer?,
    top3: LeaderboardPlayer?
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.Bottom
        ) {
            // #2 Silver
            if (top2 != null) {
                PodiumColumn(
                    player = top2,
                    rank = 2,
                    crownEmoji = "🥈",
                    heightDp = 100,
                    badgeColor = Color(0xFF90A4AE)
                )
            }

            // #1 Gold
            if (top1 != null) {
                PodiumColumn(
                    player = top1,
                    rank = 1,
                    crownEmoji = "👑",
                    heightDp = 125,
                    badgeColor = BioGoldStar
                )
            }

            // #3 Bronze
            if (top3 != null) {
                PodiumColumn(
                    player = top3,
                    rank = 3,
                    crownEmoji = "🥉",
                    heightDp = 85,
                    badgeColor = Color(0xFFBCAAA4)
                )
            }
        }
    }
}

@Composable
fun PodiumColumn(
    player: LeaderboardPlayer,
    rank: Int,
    crownEmoji: String,
    heightDp: Int,
    badgeColor: Color
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(95.dp)
    ) {
        Text(crownEmoji, fontSize = 20.sp)
        Text(player.avatarEmoji, fontSize = 28.sp)
        Text(
            text = player.name.substringBefore("@").take(11),
            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
            maxLines = 1
        )
        Text(
            text = "${player.score} pts",
            style = MaterialTheme.typography.labelSmall.copy(color = BioEmerald, fontWeight = FontWeight.SemiBold)
        )

        Spacer(modifier = Modifier.height(6.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(heightDp.dp)
                .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
                .background(badgeColor.copy(alpha = 0.35f)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "#$rank",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            )
        }
    }
}

@Composable
fun LeaderboardRow(player: LeaderboardPlayer) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("leaderboard_row_${player.rank}"),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (player.isUser) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f) else MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "#${player.rank}",
                style = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = when (player.rank) {
                        1 -> BioAmber
                        2 -> Color(0xFF78909C)
                        3 -> Color(0xFF8D6E63)
                        else -> MaterialTheme.colorScheme.onSurfaceVariant
                    }
                ),
                modifier = Modifier.width(32.dp)
            )

            Text(player.avatarEmoji, fontSize = 22.sp)

            Spacer(modifier = Modifier.width(10.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = player.name,
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
                    )
                    if (player.isUser) {
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("(Tú)", style = MaterialTheme.typography.labelSmall.copy(color = BioForestGreen, fontWeight = FontWeight.Bold))
                    }
                }
                Text(
                    text = "${player.tier} • Nivel ${player.levelReached}",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "${player.score}",
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = BioForestGreen
                    )
                )
                Text(
                    text = "⭐ ${player.stars}",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
