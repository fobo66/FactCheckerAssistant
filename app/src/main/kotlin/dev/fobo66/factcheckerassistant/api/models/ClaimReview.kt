package dev.fobo66.factcheckerassistant.api.models

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Immutable
@Parcelize
data class ClaimReview(
    val publisher: Publisher,
    val url: String,
    val title: String?,
    val reviewDate: String? = null,
    val textualRating: String,
    val languageCode: String
) : Parcelable
