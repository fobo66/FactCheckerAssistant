package io.github.fobo66.factcheckerassistant.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.github.fobo66.factcheckerassistant.api.FactCheckApi
import io.github.fobo66.factcheckerassistant.api.models.Claim
import io.github.fobo66.factcheckerassistant.api.models.FactCheckResponse
import io.github.fobo66.factcheckerassistant.util.LocaleProvider
import io.github.fobo66.factcheckerassistant.util.TestLocaleProvider
import io.github.fobo66.factcheckerassistant.util.TestTree
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.mock.BehaviorDelegate
import retrofit2.mock.MockRetrofit
import retrofit2.mock.NetworkBehavior
import timber.log.Timber
import java.util.concurrent.TimeUnit

@ExperimentalCoroutinesApi
class FactCheckRepositoryTest {
    private lateinit var factCheckRepository: FactCheckRepository
    private lateinit var mockApi: BehaviorDelegate<FactCheckApi>

    private val localeProvider: LocaleProvider = TestLocaleProvider()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {

        Timber.plant(TestTree())

        val retrofit = Retrofit.Builder()
            .baseUrl("http://example.com")
            .build()

        val networkBehavior = NetworkBehavior.create()
        networkBehavior.setDelay(0, TimeUnit.MILLISECONDS)

        mockApi = MockRetrofit.Builder(retrofit)
            .networkBehavior(networkBehavior)
            .build().create(FactCheckApi::class.java)
    }


    @After
    fun tearDown() {
        Timber.uprootAll()
    }

    @Test
    fun `Search for query with empty results`() {
        factCheckRepository = FactCheckRepository(
            mockApi.returningResponse(
                FactCheckResponse(
                    listOf(), null
                )
            ), localeProvider
        )

        runBlocking {
            val result = factCheckRepository.search("test", 10).flow.firstOrNull()
            delay(DEFAULT_API_DELAY)
            assertNotNull(result)
        }
    }

    @Test
    fun `Search for query with one result`() {
        val claim: Claim = mockk()

        factCheckRepository = FactCheckRepository(
            mockApi.returningResponse(
                FactCheckResponse(
                    listOf(claim), null
                )
            ),
            localeProvider
        )

        runBlocking {
            val result = factCheckRepository.search("test", 10).flow.firstOrNull()
            delay(DEFAULT_API_DELAY)
            assertNotNull(result)
        }
    }

    companion object {
        // delay is needed to filter out initial empty list that is emitted by Paging
        private const val DEFAULT_API_DELAY = 300L
    }
}
