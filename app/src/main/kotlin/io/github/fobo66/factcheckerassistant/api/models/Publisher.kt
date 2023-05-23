package io.github.fobo66.factcheckerassistant.api.models

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
@Immutable
@Parcelize
data class Publisher(
    val name: String?,
    val site: String?
) : Parcelable
