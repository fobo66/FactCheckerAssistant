package dev.fobo66.composemd

import androidx.compose.runtime.Composable
import org.commonmark.node.Document
import org.commonmark.parser.Parser
import java.io.InputStream

@Composable
fun MarkdownDocument(inputStream: InputStream) {
    val parser = Parser.builder().build()
    val document = parser.parseReader(inputStream.reader()) as Document
    MarkdownDocument(document)
}

@Composable
fun MarkdownDocument(input: String) {
    val parser = Parser.builder().build()
    val document = parser.parse(input) as Document

    MarkdownDocument(document)
}
