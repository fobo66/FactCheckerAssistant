package io.github.fobo66.factcheckerassistant.api.models

import com.squareup.moshi.JsonClass
import java.time.LocalDateTime

@JsonClass(generateAdapter = true)
data class Claim(
    val text: String,
    val claimant: String?,
    val claimDate: LocalDateTime?,
    val claimReview: List<ClaimReview>
)
