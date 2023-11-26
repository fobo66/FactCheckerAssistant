package dev.fobo66.factcheckerassistant.api.models

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import dev.fobo66.factcheckerassistant.util.InstantParceler
import kotlinx.datetime.Instant
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.TypeParceler
import kotlinx.serialization.Serializable

@Serializable
@Immutable
@Parcelize
data class Claim(
    val text: String,
    val claimant: String? = null,
    @TypeParceler<Instant?, InstantParceler>() val claimDate: Instant? = null,
    val claimReview: List<ClaimReview> = emptyList()
) : Parcelable
