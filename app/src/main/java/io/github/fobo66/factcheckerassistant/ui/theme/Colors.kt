package io.github.fobo66.factcheckerassistant.ui.theme

import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color

private const val ORANGE_COLOR_CODE = 0xFFFF5622L
private const val SECONDARY_ORANGE_COLOR_CODE = 0xFFF44336L
private const val DARK_ORANGE_COLOR_CODE = 0xFFD84315L
private const val SECONDARY_DARK_ORANGE_COLOR_CODE = 0xFFc41b00L

private val orange = Color(ORANGE_COLOR_CODE)
private val secondaryOrange = Color(SECONDARY_ORANGE_COLOR_CODE)

private val darkOrange = Color(DARK_ORANGE_COLOR_CODE)
private val secondaryDarkOrange = Color(SECONDARY_DARK_ORANGE_COLOR_CODE)
val DarkColors = darkColors(
    primary = orange,
    surface = secondaryDarkOrange
)
val LightColors = lightColors(
    primary = orange,
    secondary = secondaryOrange
)