package io.github.fobo66.factcheckerassistant.util

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class LocalDateTimeAdapterTest {
    private val adapter = LocalDateTimeAdapter()
    private val now = LocalDateTime.parse(DEFAULT_TIMESTAMP)

    @Test
    fun `parse date`() {
        val date = adapter.toJson(now)
        assertEquals(EXPECTED_TIMESTAMP, date)
    }

    @Test
    fun `serialize date`() {
        val date = adapter.fromJson(EXPECTED_TIMESTAMP)
        assertEquals(now, date)
    }

    companion object {
        const val DEFAULT_TIMESTAMP = "2023-03-21T12:34:56.789"
        const val EXPECTED_TIMESTAMP = "2023-03-21T12:34:56.789Z"
    }
}
