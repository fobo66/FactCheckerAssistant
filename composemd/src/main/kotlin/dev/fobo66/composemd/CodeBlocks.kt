package dev.fobo66.composemd

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import org.commonmark.node.Document
import org.commonmark.node.FencedCodeBlock
import org.commonmark.node.IndentedCodeBlock

@Composable
fun MarkdownIndentedCodeBlock(indentedCodeBlock: IndentedCodeBlock, modifier: Modifier = Modifier) {
    val padding = if (indentedCodeBlock.parent is Document) 8.dp else 0.dp
    Box(
        modifier =
            modifier
                .padding(padding)
                .semantics {
                    testTag = "Indented code block"
                }
    ) {
        Text(
            text = indentedCodeBlock.literal,
            style = TextStyle(fontFamily = FontFamily.Monospace)
        )
    }
}

@Composable
fun MarkdownFencedCodeBlock(fencedCodeBlock: FencedCodeBlock, modifier: Modifier = Modifier) {
    val padding = if (fencedCodeBlock.parent is Document) 8.dp else 0.dp
    Box(
        modifier =
            modifier
                .padding(padding)
                .semantics {
                    testTag = "Fenced code block"
                }
    ) {
        Text(
            text = fencedCodeBlock.literal,
            style = TextStyle(fontFamily = FontFamily.Monospace)
        )
    }
}
