package io.github.fobo66.factcheckerassistant.api.models

import androidx.compose.runtime.Immutable
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Immutable
data class ClaimReview(
    val publisher: Publisher,
    val url: String,
    val title: String?,
    val reviewDate: String?,
    val textualRating: String,
    val languageCode: String
)
