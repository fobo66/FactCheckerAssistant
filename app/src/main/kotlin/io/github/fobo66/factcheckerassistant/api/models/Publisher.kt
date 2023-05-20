package io.github.fobo66.factcheckerassistant.api.models

import androidx.compose.runtime.Immutable
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Immutable
data class Publisher(
    val name: String?,
    val site: String?
)
