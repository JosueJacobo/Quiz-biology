package com.example.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.FormatListNumbered
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.example.ui.theme.BioEmerald
import com.example.ui.viewmodel.AppScreen

@Composable
fun BottomNavBar(
    currentScreen: AppScreen,
    isCreator: Boolean,
    onNavigate: (AppScreen) -> Unit
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface
    ) {
        // Tab 1: 100 Levels Map
        NavigationBarItem(
            modifier = Modifier.testTag("nav_levels_map"),
            selected = currentScreen == AppScreen.LEVELS_MAP,
            onClick = { onNavigate(AppScreen.LEVELS_MAP) },
            icon = {
                Icon(
                    imageVector = Icons.Default.FormatListNumbered,
                    contentDescription = "Ruta 100 Niveles"
                )
            },
            label = { Text("Ruta (100)") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = BioEmerald,
                indicatorColor = MaterialTheme.colorScheme.primaryContainer
            )
        )

        // Tab 2: Leaderboard
        NavigationBarItem(
            modifier = Modifier.testTag("nav_leaderboard"),
            selected = currentScreen == AppScreen.LEADERBOARD,
            onClick = { onNavigate(AppScreen.LEADERBOARD) },
            icon = {
                Icon(
                    imageVector = Icons.Default.EmojiEvents,
                    contentDescription = "Clasificación en Tiempo Real"
                )
            },
            label = { Text("Ranking") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = BioEmerald,
                indicatorColor = MaterialTheme.colorScheme.primaryContainer
            )
        )

        // Tab 3: Creator Workshop / Add Question
        NavigationBarItem(
            modifier = Modifier.testTag("nav_add_question"),
            selected = currentScreen == AppScreen.ADD_QUESTION,
            onClick = { onNavigate(AppScreen.ADD_QUESTION) },
            icon = {
                Icon(
                    imageVector = Icons.Default.AddCircleOutline,
                    contentDescription = "Taller de Preguntas"
                )
            },
            label = { Text(if (isCreator) "Taller (+)" else "Preguntas") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = BioEmerald,
                indicatorColor = MaterialTheme.colorScheme.primaryContainer
            )
        )

        // Tab 4: Profile & Progress
        NavigationBarItem(
            modifier = Modifier.testTag("nav_profile"),
            selected = currentScreen == AppScreen.PROFILE,
            onClick = { onNavigate(AppScreen.PROFILE) },
            icon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Mi Perfil"
                )
            },
            label = { Text("Mi Perfil") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = BioEmerald,
                indicatorColor = MaterialTheme.colorScheme.primaryContainer
            )
        )
    }
}
