package io.github.fobo66.factcheckerassistant.api

import io.github.fobo66.factcheckerassistant.api.models.FactCheckResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface FactCheckerApi {

    @GET("/claims:search")
    suspend fun search(
        @Query("query") query: String
    ): FactCheckResponse
}