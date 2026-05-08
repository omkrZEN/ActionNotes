package com.omkar.hadpad.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(

    primary = ActionPurple,
    secondary = ActionPurpleLight,

    background = Background,
    surface = Surface,

    onPrimary = Surface,
    onSecondary = TextPrimary,

    onBackground = TextPrimary,
    onSurface = TextPrimary,

    surfaceVariant = SurfaceSoft,
    outline = BorderLight
)

private val DarkColorScheme = darkColorScheme(

    primary = ActionPurple,

    background = Color(0xFF121218),
    surface = Color(0xFF1A1A24),

    onBackground = Color.White,
    onSurface = Color.White
)

@Composable
fun ActionNotesTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {

    val colorScheme =
        if (darkTheme) DarkColorScheme
        else LightColorScheme

    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}