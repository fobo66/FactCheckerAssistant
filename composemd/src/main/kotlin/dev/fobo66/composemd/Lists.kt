package dev.fobo66.composemd

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import org.commonmark.node.BulletList
import org.commonmark.node.Document
import org.commonmark.node.ListBlock
import org.commonmark.node.Node
import org.commonmark.node.OrderedList

@Composable
fun MarkdownOrderedList(orderedList: OrderedList, modifier: Modifier = Modifier) {
    var number = orderedList.markerStartNumber
    val delimiter = orderedList.markerDelimiter
    MarkdownListItems(orderedList, modifier) {
        val text =
            buildAnnotatedString {
                pushStyle(MaterialTheme.typography.bodyMedium.toSpanStyle())
                append("${number++}$delimiter ")
                appendMarkdownChildren(it, MaterialTheme.colorScheme)
                pop()
            }
        MarkdownText(text, MaterialTheme.typography.bodyMedium)
    }
}

@Composable
fun MarkdownBulletList(bulletList: BulletList, modifier: Modifier = Modifier) {
    val marker = bulletList.marker
    MarkdownListItems(bulletList, modifier) {
        val text =
            buildAnnotatedString {
                pushStyle(MaterialTheme.typography.bodyMedium.toSpanStyle())
                append("$marker ")
                appendMarkdownChildren(it, MaterialTheme.colorScheme)
                pop()
            }
        MarkdownText(text, MaterialTheme.typography.bodyMedium)
    }
}

@Composable
fun MarkdownListItems(
    listBlock: ListBlock,
    modifier: Modifier = Modifier,
    item: @Composable (node: Node) -> Unit
) {
    val bottom = if (listBlock.parent is Document) 8.dp else 0.dp
    val start = if (listBlock.parent is Document) 0.dp else 8.dp
    Box(modifier = modifier.padding(start = start, bottom = bottom)) {
        var listItem = listBlock.firstChild
        while (listItem != null) {
            var child = listItem.firstChild
            while (child != null) {
                when (child) {
                    is BulletList -> MarkdownBulletList(child)
                    is OrderedList -> MarkdownOrderedList(child)
                    else -> item(child)
                }
                child = child.next
            }
            listItem = listItem.next
        }
    }
}
