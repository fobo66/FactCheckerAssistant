package dev.fobo66.composemd

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import org.commonmark.node.Document
import org.commonmark.node.Image
import org.commonmark.node.Paragraph

@Composable
fun MarkdownParagraph(paragraph: Paragraph, modifier: Modifier = Modifier) {
    if (paragraph.firstChild is Image && paragraph.firstChild == paragraph.lastChild) {
        // Paragraph with single image
        MarkdownImage(
            imageDestination = (paragraph.firstChild as Image).destination,
            imageTitle = (paragraph.firstChild as Image).title,
            modifier = modifier
        )
    } else {
        val padding = if (paragraph.parent is Document) 8.dp else 0.dp
        Box(modifier = modifier.padding(bottom = padding)) {
            val styledText =
                buildAnnotatedString {
                    pushStyle(MaterialTheme.typography.bodyMedium.toSpanStyle())
                    appendMarkdownChildren(paragraph, MaterialTheme.colorScheme)
                    pop()
                }
            MarkdownText(styledText, MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
fun MarkdownText(text: AnnotatedString, style: TextStyle, modifier: Modifier = Modifier) {
    val uriHandler = LocalUriHandler.current
    val layoutResult =
        remember {
            mutableStateOf<TextLayoutResult?>(null)
        }

    Text(
        text = text,
        modifier =
            modifier.pointerInput(Unit) {
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
        inlineContent =
            mapOf(
                TAG_IMAGE_URL to
                    InlineTextContent(
                        Placeholder(
                            style.fontSize,
                            style.fontSize,
                            PlaceholderVerticalAlign.Bottom
                        )
                    ) {
                        AsyncImage(
                            model =
                                ImageRequest.Builder(LocalContext.current)
                                    .data(it)
                                    .build(),
                            contentDescription = null
                        )
                    }
            ),
        onTextLayout = { layoutResult.value = it }
    )
}
