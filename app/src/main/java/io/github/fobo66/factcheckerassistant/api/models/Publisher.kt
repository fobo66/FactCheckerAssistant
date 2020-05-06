package io.github.fobo66.factcheckerassistant.api.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Publisher(
    val name: String?,
    val site: String?
)
