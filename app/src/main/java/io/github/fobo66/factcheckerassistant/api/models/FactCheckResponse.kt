package io.github.fobo66.factcheckerassistant.api.models

data class FactCheckResponse(
    val claims: List<Claim>,
    val nextPageToken: String
)