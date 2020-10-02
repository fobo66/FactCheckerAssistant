package io.github.fobo66.factcheckerassistant.ui.theme

import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color

private val orange = Color(0xFFFF5722L)
private val secondaryOrange = Color(0xFFF44336L)
private val darkOrange = Color(0xFFD84315L)
private val secondaryDarkOrange = Color(0xFFC62828L)
private val yellow = Color(0xFFFFEB3BL)
private val darkYellow = Color(0xFFF9A825L)

val DarkColors = darkColors(
    primary = darkOrange,
    primaryVariant = secondaryDarkOrange,
    surface = darkYellow
)
val LightColors = lightColors(
    primary = orange,
    primaryVariant = secondaryOrange,
    surface = yellow
)