package dev.fobo66.composemd

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
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
import org.commonmark.node.Node
import org.commonmark.node.OrderedList
import org.commonmark.node.Paragraph
import org.commonmark.node.StrongEmphasis
import org.commonmark.node.Text
import org.commonmark.node.ThematicBreak

internal const val TAG_URL = "TAG_URL"
internal const val TAG_IMAGE_URL = "TAG_IMAGE_URL"

/**
 * Render Markdown document with Jetpack Compose.
 * Based on [Markdown Composer](https://github.com/ErikHellman/MarkdownComposer)
 */

@Composable
internal fun MarkdownDocument(document: Document, modifier: Modifier = Modifier) {
    Column(modifier = modifier.verticalScroll(rememberScrollState())) {
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

internal fun AnnotatedString.Builder.appendMarkdownChildren(parent: Node, colors: ColorScheme) {
    var child = parent.firstChild
    while (child != null) {
        when (child) {
            is Paragraph -> appendMarkdownChildren(child, colors)

            is Text -> append(child.literal)

            is Image ->
                appendInlineContent(
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
                val underline =
                    SpanStyle(
                        colors.primary,
                        textDecoration = TextDecoration.Underline
                    )
                pushLink(
                    LinkAnnotation.Url(
                        url = child.destination,
                        styles = TextLinkStyles(style = underline)
                    )
                )
                appendMarkdownChildren(child, colors)
                pop()
            }
        }
        child = child.next
    }
}
