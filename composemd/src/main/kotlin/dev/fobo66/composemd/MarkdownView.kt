package dev.fobo66.composemd

import androidx.annotation.RawRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.retain.retain
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalResources
import org.commonmark.node.Document
import org.commonmark.parser.Parser

@Composable
fun MarkdownDocument(@RawRes resourceId: Int, modifier: Modifier = Modifier) {
    val resources = LocalResources.current

    val parser = retain {
        Parser.builder().build()
    }

    val document = retain {
        resources.openRawResource(resourceId).bufferedReader().use {
            parser.parseReader(it) as Document
        }
    }

    MarkdownDocument(document, modifier)
}

@Composable
fun MarkdownDocument(input: String, modifier: Modifier = Modifier) {
    val parser = retain {
        Parser.builder().build()
    }
    val document = remember(input) {
        parser.parse(input) as Document
    }

    MarkdownDocument(document, modifier)
}
