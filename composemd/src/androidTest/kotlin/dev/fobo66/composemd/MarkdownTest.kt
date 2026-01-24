package dev.fobo66.composemd

import androidx.compose.foundation.layout.Column
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import org.junit.Rule
import org.junit.Test

class MarkdownTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun markdownDocument() {
        composeTestRule.setContent {
            MarkdownDocument(input = "Hello World!")
        }

        composeTestRule.onNodeWithText("Hello World!").assertIsDisplayed()
    }

    @Test
    fun markdownOrderedList() {
        composeTestRule.setContent {
            MarkdownDocument(
                input = """
                1. First item
                2. Second item
                """.trimIndent()
            )
        }

        composeTestRule.onNode(hasText("First item", substring = true)).assertIsDisplayed()
    }

    @Test
    fun markdownBulletList() {
        composeTestRule.setContent {
            MarkdownDocument(
                input = """
                * First item
                * Second item
                """.trimIndent()
            )
        }

        composeTestRule.onNode(hasText("First item", substring = true)).assertIsDisplayed()
    }

    @Test
    fun markdownImage() {
        composeTestRule.setContent {
            MarkdownDocument(
                input = "![Test image](https://source.unsplash.com/random/800x600 \"Test image\")"
            )
        }

        composeTestRule.onNode(hasContentDescription("Test image", substring = true))
            .assertIsDisplayed()
    }

    @Test
    fun markdownIndentedCodeBlock() {
        composeTestRule.setContent {
            MarkdownDocument(
                input = """
Block of text

    Block of code

"""
            )
        }

        composeTestRule.onNode(hasTestTag("Indented code block")).assertIsDisplayed()
    }

    @Test
    fun markdownFencedCodeBlock() {
        composeTestRule.setContent {
            MarkdownDocument(
                input = """
                    Block of text
                    
                    ```
                        Block of code
                    ```
                """.trimIndent()
            )
        }

        composeTestRule.onNode(hasTestTag("Fenced code block")).assertIsDisplayed()
    }

    @Test
    fun markdownParagraph() {
        composeTestRule.setContent {
            MarkdownDocument(
                input = """
                First block of text

                Second block of text
                """.trimIndent()
            )
        }

        composeTestRule.onNodeWithText("First block of text").assertIsDisplayed()
    }

    @Test
    fun markdownHeading() {
        composeTestRule.setContent {
            MarkdownDocument(input = "# Hello World!")
        }

        composeTestRule.onNodeWithText("Hello World!").assertIsDisplayed()
    }

    @Test
    fun markdownText() {
        composeTestRule.setContent {
            MarkdownDocument(input = "Hello World!")
        }

        composeTestRule.onNodeWithText("Hello World!").assertIsDisplayed()
    }

    @Test
    fun markdownThematicBreak() {
        composeTestRule.setContent {
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

        composeTestRule.onNode(hasTestTag("Thematic break")).assertIsDisplayed()
    }

    @Test
    fun markdownBlockQuote() {
        composeTestRule.setContent {
            MarkdownDocument(
                input = """
                > Quoted text
                """.trimIndent()
            )
        }

        composeTestRule.onNodeWithText("Quoted text").assertIsDisplayed()
    }
}
