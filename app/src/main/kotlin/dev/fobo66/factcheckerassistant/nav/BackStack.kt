package dev.fobo66.factcheckerassistant.nav

import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSerializable
import androidx.navigation3.runtime.NavBackStack
import kotlinx.serialization.serializer

@Composable
fun rememberBackStack(vararg elements: Screen): NavBackStack<Screen> =
    rememberSerializable(serializer = serializer()) {
        NavBackStack(*elements)
    }
