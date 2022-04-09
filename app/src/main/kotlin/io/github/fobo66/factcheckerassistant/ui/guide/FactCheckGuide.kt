package io.github.fobo66.factcheckerassistant.ui.guide

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import dev.fobo66.composemd.MarkdownDocument

private val guideTextPadding = 16.dp

@Composable
fun FactCheckGuide(modifier: Modifier = Modifier) {
    MarkdownDocument(
        LocalContext.current.assets.open("guide.md"),
        modifier = modifier
            .padding(guideTextPadding)
            .verticalScroll(rememberScrollState())
    )
}
