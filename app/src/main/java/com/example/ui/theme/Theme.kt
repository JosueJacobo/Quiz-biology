package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = BioEmeraldLight,
    onPrimary = Color.Black,
    primaryContainer = BioEmerald,
    onPrimaryContainer = Color.White,
    secondary = BioGoldStar,
    onSecondary = Color.Black,
    tertiary = BioLeafTeal,
    background = BioDarkBackground,
    onBackground = BioTextPrimaryDark,
    surface = BioDarkSurface,
    onSurface = BioTextPrimaryDark,
    surfaceVariant = BioDarkSurfaceVariant,
    onSurfaceVariant = BioTextSecondaryDark
)

private val LightColorScheme = lightColorScheme(
    primary = BioForestGreen,
    onPrimary = Color.White,
    primaryContainer = BioCorrectGreenLight,
    onPrimaryContainer = BioForestGreen,
    secondary = BioAmber,
    onSecondary = Color.Black,
    tertiary = BioLeafTeal,
    background = BioBackgroundLight,
    onBackground = BioTextPrimaryLight,
    surface = BioSurfaceLight,
    onSurface = BioTextPrimaryLight,
    surfaceVariant = BioSurfaceVariantLight,
    onSurfaceVariant = BioTextSecondaryLight
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Use our handcrafted botanical theme for cohesive immersion
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
