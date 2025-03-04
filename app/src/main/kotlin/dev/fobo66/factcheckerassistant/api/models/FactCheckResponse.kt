package dev.fobo66.factcheckerassistant.api.models

import kotlinx.serialization.Serializable

@Serializable
data class FactCheckResponse(val claims: List<Claim>? = null, val nextPageToken: String? = null)
