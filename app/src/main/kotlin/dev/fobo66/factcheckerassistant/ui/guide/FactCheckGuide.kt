package dev.fobo66.factcheckerassistant.ui.guide

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalResources
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.fobo66.composemd.MarkdownDocument
import dev.fobo66.factcheckerassistant.R
import dev.fobo66.factcheckerassistant.ui.theme.FactCheckerAssistantTheme

@Composable
fun FactCheckGuide(modifier: Modifier = Modifier) {
    MarkdownDocument(
        LocalResources.current.openRawResource(R.raw.guide),
        modifier = modifier
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
    )
}

@Preview
@Composable
private fun FactCheckGuidePreview() {
    FactCheckerAssistantTheme {
        FactCheckGuide()
    }
}
