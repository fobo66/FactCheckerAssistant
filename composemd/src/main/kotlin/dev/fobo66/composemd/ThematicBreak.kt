package dev.fobo66.composemd

import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag

@Composable
fun MarkdownThematicBreak(modifier: Modifier = Modifier) {
    HorizontalDivider(
        modifier = modifier.testTag("Thematic break")
    )
}
