package io.github.fobo66.factcheckerassistant.api.models

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
@Immutable
@Parcelize
data class ClaimReview(
    val publisher: Publisher,
    val url: String,
    val title: String?,
    val reviewDate: String?,
    val textualRating: String,
    val languageCode: String
) : Parcelable
