package dev.fobo66.composemd

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material.Colors
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.google.accompanist.coil.rememberCoilPainter
import org.commonmark.node.BlockQuote
import org.commonmark.node.BulletList
import org.commonmark.node.Code
import org.commonmark.node.Document
import org.commonmark.node.Emphasis
import org.commonmark.node.FencedCodeBlock
import org.commonmark.node.HardLineBreak
import org.commonmark.node.Heading
import org.commonmark.node.Image
import org.commonmark.node.IndentedCodeBlock
import org.commonmark.node.Link
import org.commonmark.node.ListBlock
import org.commonmark.node.Node
import org.commonmark.node.OrderedList
import org.commonmark.node.Paragraph
import org.commonmark.node.StrongEmphasis
import org.commonmark.node.Text
import org.commonmark.node.ThematicBreak

private const val TAG_URL = "TAG_URL"
private const val TAG_IMAGE_URL = "TAG_IMAGE_URL"

/**
 * Render Markdown document with Jetpack Compose.
 * Based on [Markdown Composer](https://github.com/ErikHellman/MarkdownComposer)
 */

@Composable
internal fun MarkdownDocument(document: Document) {
    MarkdownBlockChildren(document)
}

@Composable
fun MarkdownBlockChildren(parent: Node) {
    var child = parent.firstChild
    while (child != null) {
        when (child) {
            is BlockQuote -> MarkdownBlockQuote(child)
            is ThematicBreak -> MarkdownThematicBreak()
            is Heading -> MarkdownHeading(child)
            is Paragraph -> MarkdownParagraph(child)
            is FencedCodeBlock -> MarkdownFencedCodeBlock(child)
            is IndentedCodeBlock -> MarkdownIndentedCodeBlock(child)
            is Image -> MarkdownImage(child)
            is BulletList -> MarkdownBulletList(child)
            is OrderedList -> MarkdownOrderedList(child)
        }
        child = child.next
    }
}

@Composable
fun MarkdownOrderedList(orderedList: OrderedList) {
    var number = orderedList.startNumber
    val delimiter = orderedList.delimiter
    MarkdownListItems(orderedList) {
        val text = buildAnnotatedString {
            pushStyle(MaterialTheme.typography.body1.toSpanStyle())
            append("${number++}$delimiter ")
            appendMarkdownChildren(it, MaterialTheme.colors)
            pop()
        }
        MarkdownText(text, MaterialTheme.typography.body1)
    }
}

@Composable
fun MarkdownBulletList(bulletList: BulletList) {
    val marker = bulletList.bulletMarker
    MarkdownListItems(bulletList) {
        val text = buildAnnotatedString {
            pushStyle(MaterialTheme.typography.body1.toSpanStyle())
            append("$marker ")
            appendMarkdownChildren(it, MaterialTheme.colors)
            pop()
        }
        MarkdownText(text, MaterialTheme.typography.body1)
    }
}

@Composable
fun MarkdownListItems(
    listBlock: ListBlock,
    item: @Composable (node: Node) -> Unit
) {
    val bottom = if (listBlock.parent is Document) 8.dp else 0.dp
    val start = if (listBlock.parent is Document) 0.dp else 8.dp
    Box(modifier = Modifier.padding(start = start, bottom = bottom)) {
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


@Composable
fun MarkdownImage(image: Image) {
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        Image(
            painter = rememberCoilPainter(
                request = image.destination,
                shouldRefetchOnSizeChange = { _, _ -> false },
            ),
            contentDescription = image.title,
        )
    }
}

@Composable
fun MarkdownIndentedCodeBlock(indentedCodeBlock: IndentedCodeBlock) {
    val padding = if (indentedCodeBlock.parent is Document) 8.dp else 0.dp
    Box(modifier = Modifier.padding(padding)) {
        androidx.compose.material.Text(
            text = indentedCodeBlock.literal,
            style = TextStyle(fontFamily = FontFamily.Monospace)
        )
    }
}

@Composable
fun MarkdownFencedCodeBlock(fencedCodeBlock: FencedCodeBlock) {
    val padding = if (fencedCodeBlock.parent is Document) 8.dp else 0.dp
    Box(modifier = Modifier.padding(padding)) {
        androidx.compose.material.Text(
            text = fencedCodeBlock.literal,
            style = TextStyle(fontFamily = FontFamily.Monospace)
        )
    }
}

@Composable
fun MarkdownParagraph(paragraph: Paragraph) {
    if (paragraph.firstChild is Image && paragraph.firstChild == paragraph.lastChild) {
        // Paragraph with single image
        MarkdownImage(paragraph.firstChild as Image)
    } else {
        val padding = if (paragraph.parent is Document) 8.dp else 0.dp
        Box(modifier = Modifier.padding(bottom = padding)) {
            val styledText = buildAnnotatedString {
                pushStyle(MaterialTheme.typography.body1.toSpanStyle())
                appendMarkdownChildren(paragraph, MaterialTheme.colors)
                pop()
            }
            MarkdownText(styledText, MaterialTheme.typography.body1)
        }
    }
}

@Composable
fun MarkdownHeading(heading: Heading) {
    val style = when (heading.level) {
        1 -> MaterialTheme.typography.h1
        2 -> MaterialTheme.typography.h2
        3 -> MaterialTheme.typography.h3
        4 -> MaterialTheme.typography.h4
        5 -> MaterialTheme.typography.h5
        6 -> MaterialTheme.typography.h6
        else -> {
            // Not a header...
            MarkdownBlockChildren(heading)
            return
        }
    }

    val padding = if (heading.parent is Document) 8.dp else 0.dp
    Box(modifier = Modifier.padding(bottom = padding)) {
        val text = buildAnnotatedString {
            appendMarkdownChildren(heading, MaterialTheme.colors)
        }
        MarkdownText(text, style)
    }
}

@Composable
fun MarkdownText(text: AnnotatedString, style: TextStyle) {
    val uriHandler = LocalUriHandler.current
    val layoutResult = remember {
        mutableStateOf<TextLayoutResult?>(null)
    }

    androidx.compose.material.Text(
        text = text,
        modifier = Modifier.pointerInput(Unit) {
            detectTapGestures(onTap = { pos ->
                layoutResult.value?.let {
                    val position = it.getOffsetForPosition(pos)
                    text.getStringAnnotations(position, position)
                        .firstOrNull()
                        ?.let { sa ->
                            if (sa.tag == TAG_URL) {
                                uriHandler.openUri(sa.item)
                            }
                        }
                }
            })
        },
        style = style,
        inlineContent = mapOf(
            TAG_IMAGE_URL to InlineTextContent(
                Placeholder(
                    style.fontSize, style.fontSize,
                    PlaceholderVerticalAlign.Bottom
                )
            ) {
                Image(
                    painter = rememberCoilPainter(
                        request = it,
                        shouldRefetchOnSizeChange = { _, _ -> false },
                    ),
                    contentDescription = null,
                )
            }
        ),
        onTextLayout = { layoutResult.value = it }
    )
}

@Composable
fun MarkdownThematicBreak() {
    Divider(modifier = Modifier.semantics {
        testTag = "Thematic break"
    })
}

@Composable
fun MarkdownBlockQuote(blockQuote: BlockQuote) {
    Row {
        Box(
            modifier = Modifier
                .width(4.dp)
                .background(color = MaterialTheme.colors.onSurface.copy(alpha = 0.12f))
        )
        Column {
            MarkdownBlockChildren(parent = blockQuote)
        }
    }
}

private fun AnnotatedString.Builder.appendMarkdownChildren(
    parent: Node, colors: Colors
) {
    var child = parent.firstChild
    while (child != null) {
        when (child) {
            is Paragraph -> appendMarkdownChildren(child, colors)
            is Text -> append(child.literal)
            is Image -> appendInlineContent(
                TAG_IMAGE_URL,
                child.destination
            )
            is Emphasis -> {
                pushStyle(SpanStyle(fontStyle = FontStyle.Italic))
                appendMarkdownChildren(child, colors)
                pop()
            }
            is StrongEmphasis -> {
                pushStyle(SpanStyle(fontWeight = FontWeight.Bold))
                appendMarkdownChildren(child, colors)
                pop()
            }
            is Code -> {
                pushStyle(
                    TextStyle(
                        fontFamily =
                        FontFamily.Monospace
                    ).toSpanStyle()
                )
                append(child.literal)
                pop()
            }
            is HardLineBreak -> {
                append("\n")
            }
            is Link -> {
                val underline = SpanStyle(
                    colors.primary,
                    textDecoration = TextDecoration.Underline
                )
                pushStyle(underline)
                pushStringAnnotation(TAG_URL, child.destination)
                appendMarkdownChildren(child, colors)
                pop()
                pop()
            }
        }
        child = child.next
    }
}