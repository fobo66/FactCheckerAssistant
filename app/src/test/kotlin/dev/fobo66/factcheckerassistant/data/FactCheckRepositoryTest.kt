package dev.fobo66.factcheckerassistant.data

import dev.fobo66.factcheckerassistant.api.FactCheckApi
import dev.fobo66.factcheckerassistant.api.models.Claim
import dev.fobo66.factcheckerassistant.api.models.FactCheckResponse
import dev.fobo66.factcheckerassistant.util.LocaleProvider
import dev.fobo66.factcheckerassistant.util.TestLocaleProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import retrofit2.Retrofit
import retrofit2.mock.MockRetrofit
import retrofit2.mock.NetworkBehavior
import java.util.concurrent.TimeUnit

@ExperimentalCoroutinesApi
class FactCheckRepositoryTest {
    private lateinit var factCheckRepository: FactCheckRepository

    private val localeProvider: LocaleProvider = TestLocaleProvider()

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://example.com")
        .build()

    private val networkBehavior = NetworkBehavior
        .create().apply {
            setDelay(0, TimeUnit.MILLISECONDS)
        }

    private val mockApi = MockRetrofit.Builder(retrofit)
        .networkBehavior(networkBehavior)
        .build().create(FactCheckApi::class.java)

    @Test
    fun `Search for query with empty results`() = runTest {
        factCheckRepository = FactCheckRepository(
            mockApi.returningResponse(
                FactCheckResponse(
                    listOf(), null
                )
            ),
            localeProvider
        )

        val result = factCheckRepository.search("test", 10).flow.firstOrNull()
        testScheduler.advanceTimeBy(DEFAULT_API_DELAY)
        assertNotNull(result)
    }

    @Test
    fun `Search for query with one result`() = runTest {
        val claim = Claim("test", null, null, listOf())

        factCheckRepository = FactCheckRepository(
            mockApi.returningResponse(
                FactCheckResponse(
                    listOf(claim), null
                )
            ),
            localeProvider
        )
        val result = factCheckRepository.search("test", 10).flow.firstOrNull()
        testScheduler.advanceTimeBy(DEFAULT_API_DELAY)
        assertNotNull(result)
    }

    companion object {
        // delay is needed to filter out initial empty list that is emitted by Paging
        private const val DEFAULT_API_DELAY = 100L
    }
}
