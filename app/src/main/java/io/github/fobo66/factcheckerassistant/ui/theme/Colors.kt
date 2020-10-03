package io.github.fobo66.factcheckerassistant.ui.theme

import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color

private const val ORANGE_500_COLOR_CODE = 0xFFFF5622L
private const val ORANGE_900_COLOR_CODE = 0xFFc41b00L
private const val BLACK_COLOR_CODE = 0xFF000000L

private val orange500 = Color(ORANGE_500_COLOR_CODE)
private val orange900 = Color(ORANGE_900_COLOR_CODE)
private val black = Color(BLACK_COLOR_CODE)

val DarkColors = darkColors(
    primary = orange500,
    surface = orange900
)
val LightColors = lightColors(
    primary = orange500,
    onPrimary = black
)