package dev.fobo66.composemd

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
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
    fun markdownBlockChildren() {
    }

    @Test
    fun markdownOrderedList() {
    }

    @Test
    fun markdownBulletList() {
    }

    @Test
    fun markdownImage() {
    }

    @Test
    fun markdownIndentedCodeBlock() {
    }

    @Test
    fun markdownFencedCodeBlock() {
    }

    @Test
    fun markdownParagraph() {
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
    }

    @Test
    fun markdownThematicBreak() {
    }

    @Test
    fun markdownBlockQuote() {
    }
}