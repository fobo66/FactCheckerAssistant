package io.github.fobo66.factcheckerassistant.data

import io.github.fobo66.factcheckerassistant.BuildConfig
import io.github.fobo66.factcheckerassistant.api.FactCheckerApi
import io.github.fobo66.factcheckerassistant.api.models.Claim

class FactCheckRepository(
    private val factCheckerApi: FactCheckerApi
) {
    suspend fun search(query: String): List<Claim> {
        return factCheckerApi.search(query, BuildConfig.API_KEY).claims
    }
}