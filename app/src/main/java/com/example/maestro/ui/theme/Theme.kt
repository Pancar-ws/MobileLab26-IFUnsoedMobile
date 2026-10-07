package com.example.maestro.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = BmkgSecondary,
    onPrimary = Color.Black,
    primaryContainer = BmkgPrimary,
    onPrimaryContainer = Color.White,
    secondary = BmkgSecondary,
    onSecondary = Color.Black,
    tertiary = BmkgTertiary,
    onTertiary = Color.White,
    background = BmkgDarkBg,
    onBackground = BmkgDarkTextPrimary,
    surface = BmkgDarkSurface,
    onSurface = BmkgDarkTextPrimary,
    surfaceVariant = BmkgDarkCard,
    onSurfaceVariant = BmkgDarkTextSecondary,
    outline = BmkgDarkBorder
)

private val LightColorScheme = lightColorScheme(
    primary = BmkgPrimary,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFE0EDF8),
    onPrimaryContainer = BmkgPrimary,
    secondary = BmkgSecondary,
    onSecondary = Color.White,
    tertiary = BmkgTertiary,
    onTertiary = Color.White,
    background = BmkgLightBg,
    onBackground = BmkgLightTextPrimary,
    surface = BmkgLightSurface,
    onSurface = BmkgLightTextPrimary,
    surfaceVariant = Color(0xFFF1F5F9),
    onSurfaceVariant = BmkgLightTextSecondary,
    outline = BmkgLightBorder
)

@Composable
fun MaestroTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}