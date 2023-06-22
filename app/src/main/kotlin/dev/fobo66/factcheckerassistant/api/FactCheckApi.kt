package dev.fobo66.factcheckerassistant.api

import dev.fobo66.factcheckerassistant.api.models.FactCheckResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface FactCheckApi {

    @GET("/v1alpha1/claims:search")
    suspend fun search(
        @Query("query") query: String,
        @Query("languageCode") languageCode: String,
        @Query("pageSize") pageSize: Int = 10,
        @Query("pageToken") pageToken: String? = null,
        @Query("key") key: String
    ): FactCheckResponse
}
