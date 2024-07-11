package dev.fobo66.factcheckerassistant.data

import dev.fobo66.factcheckerassistant.api.FactCheckApi
import dev.fobo66.factcheckerassistant.api.models.FactCheckResponse

class FakeFactCheckApi(val response: FactCheckResponse, val error: Exception? = null) :
    FactCheckApi {
    override suspend fun search(
        query: String,
        languageCode: String,
        pageSize: Int,
        pageToken: String?,
        key: String
    ): FactCheckResponse = if (error == null) {
        response
    } else {
        throw error
    }
}
