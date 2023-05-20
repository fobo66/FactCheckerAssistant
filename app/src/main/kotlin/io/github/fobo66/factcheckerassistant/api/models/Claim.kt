package io.github.fobo66.factcheckerassistant.api.models

import androidx.compose.runtime.Immutable
import com.squareup.moshi.JsonClass
import java.time.LocalDateTime

@JsonClass(generateAdapter = true)
@Immutable
data class Claim(
    val text: String,
    val claimant: String?,
    val claimDate: LocalDateTime?,
    val claimReview: List<ClaimReview>
)
