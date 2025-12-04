package dev.fobo66.composemd

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import org.commonmark.node.Document
import org.commonmark.node.Heading

@Composable
fun MarkdownHeading(heading: Heading, modifier: Modifier = Modifier) {
    val style =
        when (heading.level) {
            HEADING_LEVEL_1 -> MaterialTheme.typography.headlineLarge

            HEADING_LEVEL_2 -> MaterialTheme.typography.headlineMedium

            HEADING_LEVEL_3 -> MaterialTheme.typography.headlineSmall

            HEADING_LEVEL_4 -> MaterialTheme.typography.titleLarge

            HEADING_LEVEL_5 -> MaterialTheme.typography.titleMedium

            HEADING_LEVEL_6 -> MaterialTheme.typography.titleSmall

            else -> {
                // Not a header...
                MarkdownBlockChildren(heading, modifier)
                return
            }
        }

    val padding = if (heading.parent is Document) 8.dp else 0.dp
    Box(modifier = modifier.padding(bottom = padding)) {
        val text =
            buildAnnotatedString {
                appendMarkdownChildren(heading, MaterialTheme.colorScheme)
            }
        MarkdownText(text, style)
    }
}
