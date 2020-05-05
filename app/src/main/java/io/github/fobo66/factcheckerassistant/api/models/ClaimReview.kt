package io.github.fobo66.factcheckerassistant.api.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ClaimReview(
    val publisher: Publisher,
    val url: String,
    val title: String?,
    val reviewDate: String?,
    val textualRating: String,
    val languageCode: String
)