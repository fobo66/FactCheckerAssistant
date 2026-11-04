package dev.fobo66.composemd

import androidx.compose.foundation.layout.Column
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.v2.runComposeUiTest
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class MarkdownTest {

    @Test
    fun markdownDocument() = runComposeUiTest {
        setContent {
            MarkdownDocument(input = "Hello World!")
        }

        onNodeWithText("Hello World!").assertIsDisplayed()
    }

    @Test
    fun markdownOrderedList() = runComposeUiTest {
        setContent {
            MarkdownDocument(
                input = """
                1. First item
                2. Second item
                """.trimIndent()
            )
        }

        onNode(hasText("First item", substring = true)).assertIsDisplayed()
    }

    @Test
    fun markdownBulletList() = runComposeUiTest {
        setContent {
            MarkdownDocument(
                input = """
                * First item
                * Second item
                """.trimIndent()
            )
        }

        onNode(hasText("First item", substring = true)).assertIsDisplayed()
    }

    @Test
    fun markdownImage() = runComposeUiTest {
        setContent {
            MarkdownDocument(
                input = """
                    ![Test image](https://picsum.photos/200 "Test image")
                """.trimIndent()
            )
        }

        awaitIdle()

        onNode(hasContentDescription("Test image", substring = true))
            .assertIsDisplayed()
    }

    @Test
    fun markdownIndentedCodeBlock() = runComposeUiTest {
        setContent {
            MarkdownDocument(
                input = """
                Block of text
                
                    Block of code
                
                """
            )
        }

        onNode(hasTestTag("Indented code block")).assertIsDisplayed()
    }

    @Test
    fun markdownFencedCodeBlock() = runComposeUiTest {
        setContent {
            MarkdownDocument(
                input = """
                    Block of text
                    
                    ```
                        Block of code
                    ```
                """.trimIndent()
            )
        }

        onNode(hasTestTag("Fenced code block")).assertIsDisplayed()
    }

    @Test
    fun markdownParagraph() = runComposeUiTest {
        setContent {
            MarkdownDocument(
                input = """
                First block of text

                Second block of text
                """.trimIndent()
            )
        }

        onNodeWithText("First block of text").assertIsDisplayed()
    }

    @Test
    fun markdownHeading() = runComposeUiTest {
        setContent {
            MarkdownDocument(input = "# Hello World!")
        }

        onNodeWithText("Hello World!").assertIsDisplayed()
    }

    @Test
    fun markdownText() = runComposeUiTest {
        setContent {
            MarkdownDocument(input = "Hello World!")
        }

        onNodeWithText("Hello World!").assertIsDisplayed()
    }

    @Test
    fun markdownThematicBreak() = runComposeUiTest {
        setContent {
            Column {
                MarkdownDocument(
                    input = """
                First block of text
                
                ---

                Second block of text
                    """.trimIndent()
                )
            }
        }

        onNode(hasTestTag("Thematic break")).assertIsDisplayed()
    }

    @Test
    fun markdownBlockQuote() = runComposeUiTest {
        setContent {
            MarkdownDocument(
                input = """
                > Quoted text
                """.trimIndent()
            )
        }

        onNodeWithText("Quoted text").assertIsDisplayed()
    }
}
