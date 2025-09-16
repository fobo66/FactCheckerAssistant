package dev.fobo66.factcheckerassistant.data

import com.google.common.truth.Truth.assertThat
import dev.fobo66.factcheckerassistant.api.models.Claim
import dev.fobo66.factcheckerassistant.api.models.FactCheckResponse
import dev.fobo66.factcheckerassistant.util.LocaleProvider
import dev.fobo66.factcheckerassistant.util.TestLocaleProvider
import java.io.IOException
import kotlin.time.ExperimentalTime
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class FactCheckRepositoryTest {
    private lateinit var factCheckRepository: FactCheckRepository

    private val localeProvider: LocaleProvider = TestLocaleProvider()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Search for query with empty results`() = runTest {
        factCheckRepository = FactCheckRepository(
            FakeFactCheckApi(
                FactCheckResponse(
                    listOf(),
                    null
                )
            ),
            localeProvider
        )

        val result = factCheckRepository.search("test", 10).flow.firstOrNull()
        testScheduler.advanceTimeBy(DEFAULT_API_DELAY)
        assertThat(result).isNotNull()
    }

    @OptIn(ExperimentalCoroutinesApi::class, ExperimentalTime::class)
    @Test
    fun `Search for query with one result`() = runTest {
        val claim = Claim("test", null, null, listOf())

        factCheckRepository = FactCheckRepository(
            FakeFactCheckApi(
                FactCheckResponse(
                    listOf(claim),
                    null
                )
            ),
            localeProvider
        )
        val result = factCheckRepository.search("test", 10).flow.firstOrNull()
        testScheduler.advanceTimeBy(DEFAULT_API_DELAY)
        assertThat(result).isNotNull()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Search for query with error`() = runTest {
        factCheckRepository = FactCheckRepository(
            FakeFactCheckApi(
                error = IOException(),
                response = FactCheckResponse(emptyList(), null)
            ),
            localeProvider
        )
        val result = factCheckRepository.search("test", 10).flow.firstOrNull()
        testScheduler.advanceTimeBy(DEFAULT_API_DELAY)
        assertThat(result).isNotNull()
    }

    companion object {
        // delay is needed to filter out initial empty list that is emitted by Paging
        private const val DEFAULT_API_DELAY = 100L
    }
}
