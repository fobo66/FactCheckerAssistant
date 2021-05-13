package dev.fobo66.composemd

import androidx.compose.foundation.layout.Column
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import org.junit.Assert.fail
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
            Column {
                MarkdownDocument(
                    input = """
                1. First item
                2. Second item
            """.trimIndent()
                )
            }
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
        fail("Not implemented")
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

        composeTestRule.onNode(hasText("Block of code", substring = true)).assertIsDisplayed()
    }

    @Test
    fun markdownFencedCodeBlock() {
        composeTestRule.setContent {
            Column {
                MarkdownDocument(
                    input = """
                    Block of text
                    
                    ```
                        Block of code
                    ```
                    """.trimIndent()
                )
            }

        }

        composeTestRule.onNode(hasText("Block of code", substring = true)).assertIsDisplayed()
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