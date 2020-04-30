package io.github.fobo66.factcheckerassistant.data

import io.github.fobo66.factcheckerassistant.api.FactCheckerApi
import io.github.fobo66.factcheckerassistant.api.models.FactCheckResponse
import io.github.fobo66.factcheckerassistant.extractValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.mock.MockRetrofit

@ExperimentalCoroutinesApi
class FactCheckRepositoryTest {
    private lateinit var factCheckRepository: FactCheckRepository

    @Before
    fun setUp() {

        val retrofit = Retrofit.Builder()
            .baseUrl("http://example.com")
            .build()

        val delegate = MockRetrofit.Builder(retrofit)
            .build().create(FactCheckerApi::class.java)

        factCheckRepository = FactCheckRepository(
            delegate.returningResponse(
                FactCheckResponse(
                    listOf(), null
                )
            )
        )
    }

    @Test
    fun no_results() {

        runBlocking(Dispatchers.Main) {
            val result = factCheckRepository.search("test", this).extractValue()
            assertTrue(result.isNullOrEmpty())
        }
    }
}