package dev.fobo66.composemd

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
        fail("Not implemented")
    }

    @Test
    fun markdownIndentedCodeBlock() {
        fail("Not implemented")
    }

    @Test
    fun markdownFencedCodeBlock() {
        fail("Not implemented")
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
            MarkdownDocument(
                input = """
                First block of text
                
                ---

                Second block of text
            """.trimIndent()
            )
        }

        composeTestRule.onNode(hasTestTag("Thematic break")).assertIsDisplayed()
    }

    @Test
    fun markdownBlockQuote() {
        fail("Not implemented")
    }
}