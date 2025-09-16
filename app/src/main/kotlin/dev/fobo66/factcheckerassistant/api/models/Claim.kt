package dev.fobo66.factcheckerassistant.api.models

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import dev.fobo66.factcheckerassistant.util.InstantParceler
import dev.fobo66.factcheckerassistant.util.InstantSerializer
import kotlin.time.ExperimentalTime
import kotlin.time.Instant
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.TypeParceler
import kotlinx.serialization.Serializable

@Serializable
@Immutable
@Parcelize
@OptIn(ExperimentalTime::class)
data class Claim(
    val text: String? = null,
    val claimant: String? = null,
    @Serializable(with = InstantSerializer::class)
    @TypeParceler<Instant?, InstantParceler>() val claimDate: Instant? = null,
    val claimReview: List<ClaimReview> = emptyList()
) : Parcelable
