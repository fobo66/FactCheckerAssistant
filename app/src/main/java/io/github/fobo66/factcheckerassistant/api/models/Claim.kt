package io.github.fobo66.factcheckerassistant.api.models

data class Claim(
    val text: String,
    val claimant: String,
    val claimDate: String,
    val claimReview: List<ClaimReview>
)