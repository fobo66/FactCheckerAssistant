package dev.fobo66.composemd

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import org.commonmark.node.Document
import org.commonmark.parser.Parser
import java.io.InputStream

@Composable
fun MarkdownDocument(inputStream: InputStream, modifier: Modifier = Modifier) {
    val parser = remember {
        Parser.builder().build()
    }

    val document = remember(inputStream) {
        inputStream.bufferedReader().use {
            parser.parseReader(it) as Document
        }
    }

    MarkdownDocument(document, modifier)
}

@Composable
fun MarkdownDocument(input: String, modifier: Modifier = Modifier) {
    val parser = remember {
        Parser.builder().build()
    }
    val document = remember(input) {
        parser.parse(input) as Document
    }

    MarkdownDocument(document, modifier)
}
