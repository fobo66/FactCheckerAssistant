package dev.fobo66.factcheckerassistant.api.models

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Immutable
@Parcelize
data class Publisher(val name: String? = null, val site: String? = null) : Parcelable
