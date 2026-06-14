package com.example.infinite_scroll_app.ui.theme

import android.app.Activity
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
    primary = LightTeal,
    onPrimary = Color(0xFF00201C),
    primaryContainer = DarkPanelAlt,
    onPrimaryContainer = DarkInk,
    secondary = DarkMutedInk,
    onSecondary = DarkCanvas,
    tertiary = DarkAmber,
    background = DarkCanvas,
    onBackground = DarkInk,
    surface = DarkPanel,
    onSurface = DarkInk,
    surfaceVariant = DarkPanelAlt,
    onSurfaceVariant = DarkMutedInk,
    outline = DarkLine,
)

private val LightColorScheme = lightColorScheme(
    primary = DeepTeal,
    onPrimary = Color.White,
    primaryContainer = SoftTeal,
    onPrimaryContainer = Ink,
    secondary = MutedInk,
    onSecondary = Color.White,
    tertiary = WarmAmber,
    background = Canvas,
    onBackground = Ink,
    surface = Panel,
    onSurface = Ink,
    surfaceVariant = PanelAlt,
    onSurfaceVariant = MutedInk,
    outline = Line,
)

@Composable
fun InfinitescrollappTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
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
