package io.github.fobo66.factcheckerassistant.data

import io.github.fobo66.factcheckerassistant.api.FactCheckerApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@ExperimentalCoroutinesApi
class FactCheckRepositoryTest {
    private lateinit var factCheckRepository: FactCheckRepository
    private lateinit var mockWebServer: MockWebServer

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()

        val factCheckerApi = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(FactCheckerApi::class.java)

        factCheckRepository = FactCheckRepository(factCheckerApi)
    }

    @Test
    fun `Search for claim and get no results`() {
        mockWebServer.enqueue(
            MockResponse().setBody(
                """
            {
                "claims": []
            }
            """.trimIndent()
            )
        )

        runBlocking {
            factCheckRepository.search("test")
        }
    }
}