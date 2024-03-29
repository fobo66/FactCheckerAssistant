package dev.fobo66.composemd

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
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.testTag
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
import coil.compose.AsyncImage
import coil.request.ImageRequest
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
internal fun MarkdownDocument(document: Document, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        MarkdownBlockChildren(document)
    }
}

@Composable
fun MarkdownBlockChildren(parent: Node, modifier: Modifier = Modifier) {
    var child = parent.firstChild
    while (child != null) {
        when (child) {
            is BlockQuote -> MarkdownBlockQuote(child, modifier)
            is ThematicBreak -> MarkdownThematicBreak(modifier)
            is Heading -> MarkdownHeading(child, modifier)
            is Paragraph -> MarkdownParagraph(child, modifier)
            is FencedCodeBlock -> MarkdownFencedCodeBlock(child, modifier)
            is IndentedCodeBlock -> MarkdownIndentedCodeBlock(child, modifier)
            is Image -> MarkdownImage(child, modifier)
            is BulletList -> MarkdownBulletList(child, modifier)
            is OrderedList -> MarkdownOrderedList(child, modifier)
        }
        child = child.next
    }
}

@Composable
fun MarkdownOrderedList(orderedList: OrderedList, modifier: Modifier = Modifier) {
    var number = orderedList.markerStartNumber
    val delimiter = orderedList.markerDelimiter
    MarkdownListItems(orderedList, modifier) {
        val text = buildAnnotatedString {
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
        val text = buildAnnotatedString {
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

@Composable
fun MarkdownImage(image: Image, modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(image.destination)
                .build(),
            contentDescription = image.title
        )
    }
}

@Composable
fun MarkdownIndentedCodeBlock(indentedCodeBlock: IndentedCodeBlock, modifier: Modifier = Modifier) {
    val padding = if (indentedCodeBlock.parent is Document) 8.dp else 0.dp
    Box(
        modifier = modifier
            .padding(padding)
            .semantics {
                testTag = "Indented code block"
            }
    ) {
        androidx.compose.material3.Text(
            text = indentedCodeBlock.literal,
            style = TextStyle(fontFamily = FontFamily.Monospace)
        )
    }
}

@Composable
fun MarkdownFencedCodeBlock(fencedCodeBlock: FencedCodeBlock, modifier: Modifier = Modifier) {
    val padding = if (fencedCodeBlock.parent is Document) 8.dp else 0.dp
    Box(
        modifier = modifier
            .padding(padding)
            .semantics {
                testTag = "Fenced code block"
            }
    ) {
        androidx.compose.material3.Text(
            text = fencedCodeBlock.literal,
            style = TextStyle(fontFamily = FontFamily.Monospace)
        )
    }
}

@Composable
fun MarkdownParagraph(paragraph: Paragraph, modifier: Modifier = Modifier) {
    if (paragraph.firstChild is Image && paragraph.firstChild == paragraph.lastChild) {
        // Paragraph with single image
        MarkdownImage(paragraph.firstChild as Image, modifier)
    } else {
        val padding = if (paragraph.parent is Document) 8.dp else 0.dp
        Box(modifier = modifier.padding(bottom = padding)) {
            val styledText = buildAnnotatedString {
                pushStyle(MaterialTheme.typography.bodyMedium.toSpanStyle())
                appendMarkdownChildren(paragraph, MaterialTheme.colorScheme)
                pop()
            }
            MarkdownText(styledText, MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
@Suppress("ModifierReused") // modifier passed to the children is fine
fun MarkdownHeading(heading: Heading, modifier: Modifier = Modifier) {
    val style = when (heading.level) {
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
        val text = buildAnnotatedString {
            appendMarkdownChildren(heading, MaterialTheme.colorScheme)
        }
        MarkdownText(text, style)
    }
}

@Composable
fun MarkdownText(text: AnnotatedString, style: TextStyle, modifier: Modifier = Modifier) {
    val uriHandler = LocalUriHandler.current
    val layoutResult = remember {
        mutableStateOf<TextLayoutResult?>(null)
    }

    androidx.compose.material3.Text(
        text = text,
        modifier = modifier.pointerInput(Unit) {
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
                    style.fontSize,
                    style.fontSize,
                    PlaceholderVerticalAlign.Bottom
                )
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(it)
                        .build(),
                    contentDescription = null
                )
            }
        ),
        onTextLayout = { layoutResult.value = it }
    )
}

@Composable
fun MarkdownThematicBreak(modifier: Modifier = Modifier) {
    HorizontalDivider(
        modifier = modifier.testTag("Thematic break")
    )
}

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

private fun AnnotatedString.Builder.appendMarkdownChildren(
    parent: Node,
    colors: ColorScheme
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
