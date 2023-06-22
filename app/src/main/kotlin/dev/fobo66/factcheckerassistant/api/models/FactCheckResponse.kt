package dev.fobo66.factcheckerassistant.api.models

import com.squareup.moshi.JsonClass
import dev.fobo66.factcheckerassistant.api.models.Claim

@JsonClass(generateAdapter = true)
data class FactCheckResponse(
    val claims: List<Claim>?,
    val nextPageToken: String?
)
