package com.example.one_menu.feature.common.view.theme

import android.app.Activity
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.safeContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat

private val colorsTheme = lightColors(
    primary = primaryColor,
    background = backgroundColor,
)

@Composable
fun OneMenuTheme(
     content: @Composable ()->Unit
) {
    val activity = LocalContext.current as? Activity
    SideEffect {
        activity?.window?.let {window->
            WindowCompat.setDecorFitsSystemWindows(window, false)
            WindowCompat
                .getInsetsController(window, window.decorView).isAppearanceLightStatusBars = true
        }
    }
    MaterialTheme(
        colors =colorsTheme,
        shapes = shapes,
        typography = Typography,
        content = content,
    )
}