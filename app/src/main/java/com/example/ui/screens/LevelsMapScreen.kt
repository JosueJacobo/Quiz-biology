package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.models.BiomeWorld
import com.example.data.models.LevelProgress
import com.example.data.models.User
import com.example.ui.theme.BioAmber
import com.example.ui.theme.BioEmerald
import com.example.ui.theme.BioForestGreen
import com.example.ui.theme.BioGoldStar

// Descriptive titles for the 100 biological and botanical levels
val LevelTitles = mapOf(
    1 to "Anatomía de la Hoja",
    2 to "Sistemas Radiculares",
    3 to "El Tallo y Conducción",
    4 to "Xilema y Floema",
    5 to "La Flor y Órganos",
    6 to "Estomas y Transpiración",
    7 to "Clorofila y Plastos",
    8 to "Fase Fotosintética",
    9 to "Frutos y Semillas",
    10 to "Gimnospermas y Coníferas",
    11 to "Angiospermas",
    12 to "Briófitas y Musgos",
    13 to "Pteridófitas y Helechos",
    14 to "Fitohormonas",
    15 to "Polinización Biótica",
    16 to "Plantas Carnívoras",
    17 to "Metabolismo CAM",
    18 to "Pared Celular y Celulosa",
    19 to "Germinación Radicular",
    20 to "Anillos de Crecimiento",
    21 to "Poríferos y Esponjas",
    22 to "Cnidarios y Medusas",
    23 to "Platelmintos Planos",
    24 to "Nemátodos Cilíndricos",
    25 to "Anélidos y Clitelo",
    26 to "Caracoles y Rádula",
    27 to "Bivalvos y Filtración",
    28 to "Cefalópodos y Tintas",
    29 to "Exoesqueleto de Quitina",
    30 to "Anatomía del Insecto",
    31 to "Metamorfosis Holometábola",
    32 to "Arácnidos y Quelíceros",
    33 to "Crustáceos y Branquias",
    34 to "Miriápodos y Patas",
    35 to "Sistema Ambulacral",
    36 to "Estrellas y Erizos",
    37 to "Omatidios Compuestos",
    38 to "Feromonas Químicas",
    39 to "Insectos Sociales",
    40 to "Bioluminiscencia Fría",
    41 to "Tiburones y Cartílago",
    42 to "Peces Óseos y Vejiga",
    43 to "La Línea Lateral",
    44 to "Anfibios Anuros",
    45 to "Urodelos y Neotenia",
    46 to "Respiración Cutánea",
    47 to "Escamas de Queratina",
    48 to "Quelonios y Caparazón",
    49 to "Crocodilios y Corazón",
    50 to "Ectotermia Térmica",
    51 to "Estructura de Plumas",
    52 to "Huesos Neumáticos",
    53 to "Morfología de Picos",
    54 to "Orientación Magnética",
    55 to "Monotremas Ovíparos",
    56 to "Marsupiales y Bolsa",
    57 to "Mamíferos Placentarios",
    58 to "Murciélagos y Patagio",
    59 to "Cetáceos y Espiráculo",
    60 to "Visión Binocular",
    61 to "Circulación Hemolinfa",
    62 to "Corazón de Peces",
    63 to "Tráqueas de Insectos",
    64 to "Estómago de Rumiantes",
    65 to "Red Nerviosa Difusa",
    66 to "Melón de Ecolocalización",
    67 to "Fosetas Infrarrojas",
    68 to "Órgano de Jacobson",
    69 to "Hibernación y Letargo",
    70 to "Mimetismo Batesiano",
    71 to "Cripsis de Insecto Palo",
    72 to "Neurotoxinas Animales",
    73 to "Antocianinas Vegetales",
    74 to "Fototropismo y Auxinas",
    75 to "Sismonastia Mimosa",
    76 to "Taninos Protectores",
    77 to "Rizomas Subterráneos",
    78 to "Partenogénesis",
    79 to "Glándulas de Sal",
    80 to "Termogénesis Floral",
    81 to "Productores Autótrofos",
    82 to "Regla del 10% Trófico",
    83 to "Hongos Descomponedores",
    84 to "Micorrizas Simbióticas",
    85 to "Líquenes Mutualistas",
    86 to "Peces Payaso y Anémonas",
    87 to "Comensalismo Rémora",
    88 to "Haustorios Parásitos",
    89 to "Zooxantelas de Coral",
    90 to "Espinas de Cactáceas",
    91 to "Agua Metabólica Desierto",
    92 to "Permafrost de la Tundra",
    93 to "Canopia de Selva Lluviosa",
    94 to "Adaptación Abisal Marina",
    95 to "Pez Piedra Venenoso",
    96 to "Epífitas del Dosel",
    97 to "Tensión-Cohesión Hídrica",
    98 to "Neumatóforos del Manglar",
    99 to "Jerarquía Taxonómica",
    100 to "Maestro de la Biosfera"
)

@Composable
fun LevelsMapScreen(
    user: User?,
    progressList: List<LevelProgress>,
    onStartLevel: (Int) -> Unit
) {
    var selectedBiomeFilter by remember { mutableStateOf<BiomeWorld?>(null) }

    val progressMap = remember(progressList) {
        progressList.associateBy { it.levelNumber }
    }

    val maxCompleted = progressList.filter { it.isCompleted }.maxOfOrNull { it.levelNumber } ?: 0
    val highestUnlockedLevel = (maxCompleted + 1).coerceAtMost(100)
    val totalStars = progressList.sumOf { it.starsEarned }

    val allLevels = (1..100).toList()
    val displayedLevels = remember(selectedBiomeFilter) {
        if (selectedBiomeFilter == null) {
            allLevels
        } else {
            allLevels.filter { it in selectedBiomeFilter!!.startLevel..selectedBiomeFilter!!.endLevel }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Grand Header Card
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(20.dp),
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
                        Text(
                            text = "Camino Biológico",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = BioForestGreen
                            )
                        )
                        Text(
                            text = "100 Niveles • 5,000+ Preguntas (mín. 50 por nivel)",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = BioGoldStar.copy(alpha = 0.15f)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = null,
                                tint = BioGoldStar,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "$totalStars / 300",
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Progress Bar
                val progressPercent = (maxCompleted.toFloat() / 100f).coerceIn(0f, 1f)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Progreso Global",
                        style = MaterialTheme.typography.labelMedium
                    )
                    Text(
                        text = "$maxCompleted / 100 Niveles",
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
                LinearProgressIndicator(
                    progress = { progressPercent },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .clip(RoundedCornerShape(4.dp)),
                    color = BioEmerald,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant
                )
            }
        }

        // Biome Filter Carousel
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            item {
                FilterChip(
                    selected = selectedBiomeFilter == null,
                    onClick = { selectedBiomeFilter = null },
                    label = { Text("Todos (1-100)") },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = BioForestGreen,
                        selectedLabelColor = Color.White
                    )
                )
            }
            items(BiomeWorld.entries) { biome ->
                FilterChip(
                    selected = selectedBiomeFilter == biome,
                    onClick = {
                        selectedBiomeFilter = if (selectedBiomeFilter == biome) null else biome
                    },
                    label = { Text("${biome.iconEmoji} ${biome.startLevel}-${biome.endLevel}") },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = Color(biome.themeColorHex),
                        selectedLabelColor = Color.White
                    )
                )
            }
        }

        // 100 Levels Vertical Grid
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .fillMaxSize()
                .testTag("levels_grid")
        ) {
            items(displayedLevels) { levelNum ->
                val progress = progressMap[levelNum]
                val stars = progress?.starsEarned ?: 0
                // Level is unlocked if: level 1, or previous level completed, or user is creator!
                val isUnlocked = levelNum == 1 || (levelNum <= highestUnlockedLevel) || (user?.isCreator == true)
                val title = LevelTitles[levelNum] ?: "Nivel $levelNum"
                val biome = BiomeWorld.forLevel(levelNum)

                LevelGridCard(
                    levelNumber = levelNum,
                    title = title,
                    stars = stars,
                    isUnlocked = isUnlocked,
                    biomeEmoji = biome.iconEmoji,
                    onClick = {
                        if (isUnlocked) {
                            onStartLevel(levelNum)
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun LevelGridCard(
    levelNumber: Int,
    title: String,
    stars: Int,
    isUnlocked: Boolean,
    biomeEmoji: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("level_card_$levelNumber")
            .clip(RoundedCornerShape(16.dp))
            .clickable(enabled = isUnlocked) { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isUnlocked) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
        ),
        elevation = CardDefaults.cardElevation(if (isUnlocked) 2.dp else 0.dp),
        border = if (isUnlocked && stars > 0) {
            CardDefaults.outlinedCardBorder().copy(brush = Brush.linearGradient(listOf(BioEmerald, BioAmber)))
        } else null
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Level Number Badge
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(
                            if (isUnlocked) BioForestGreen else MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "$levelNumber",
                        color = Color.White,
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                    )
                }

                // Biome icon or lock
                if (isUnlocked) {
                    Text(biomeEmoji, fontSize = 18.sp)
                } else {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Bloqueado",
                        tint = MaterialTheme.colorScheme.outline,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = if (isUnlocked) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.outline
                ),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(4.dp))

            Surface(
                color = if (isUnlocked) BioEmerald.copy(alpha = 0.12f) else MaterialTheme.colorScheme.outline.copy(alpha = 0.1f),
                shape = RoundedCornerShape(4.dp)
            ) {
                Text(
                    text = "50+ preguntas",
                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp, fontWeight = FontWeight.Medium),
                    color = if (isUnlocked) BioEmerald else MaterialTheme.colorScheme.outline,
                    modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Stars Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row {
                    for (i in 1..3) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = if (i <= stars) BioGoldStar else MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }

                if (isUnlocked) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = "Jugar",
                        tint = BioEmerald,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}
