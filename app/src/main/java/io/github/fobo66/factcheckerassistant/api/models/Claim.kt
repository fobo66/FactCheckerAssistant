package io.github.fobo66.factcheckerassistant.api.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Claim(
    val text: String,
    val claimant: String?,
    val claimDate: String?,
    val claimReview: List<ClaimReview>
)
