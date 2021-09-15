package io.github.fobo66.factcheckerassistant.api.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FactCheckResponse(
    val claims: List<Claim>?,
    val nextPageToken: String?
)
