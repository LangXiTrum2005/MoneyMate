package com.example.moneymate.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val HomeColorScheme = lightColorScheme(
    primary = PrimaryPurple,
    onPrimary = Color.White,
    background = PageBackground,
    onBackground = Ink,
    surface = Color.White,
    onSurface = Ink,
    onSurfaceVariant = MutedInk
)

@Composable
fun MoneyMateTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = HomeColorScheme, typography = Typography, content = content)
}
