package dev.fobo66.composemd

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
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

@Preview
@Composable
fun MarkdownSimpleText() {
    MarkdownDocument(input = "Hello World!")
}

@Preview
@Composable
fun MarkdownBlockquote() {
    Column {
        MarkdownDocument(input = "> Hello World! To anyone whom may concern: get over here!")
    }
}

@Preview
@Composable
fun MarkdownSimpleTextWithHeading() {
    Column {
        MarkdownDocument(input = "# Hello World!\n\nThis is a test")
    }
}