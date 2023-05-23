package io.github.fobo66.factcheckerassistant.api.models

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@JsonClass(generateAdapter = true)
@Immutable
@Parcelize
data class Claim(
    val text: String,
    val claimant: String?,
    val claimDate: LocalDateTime?,
    val claimReview: List<ClaimReview>
) : Parcelable
