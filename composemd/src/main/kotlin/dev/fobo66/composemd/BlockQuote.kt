package dev.fobo66.composemd

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.commonmark.node.BlockQuote

@Composable
fun MarkdownBlockQuote(blockQuote: BlockQuote, modifier: Modifier = Modifier) {
    Row(modifier) {
        Box(
            modifier = Modifier
                .width(4.dp)
                .background(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f))
        )
        Column {
            MarkdownBlockChildren(parent = blockQuote)
        }
    }
}
