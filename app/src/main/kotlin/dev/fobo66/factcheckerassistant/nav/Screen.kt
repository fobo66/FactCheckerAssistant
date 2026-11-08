package dev.fobo66.factcheckerassistant.nav

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.navigation3.runtime.NavKey
import dev.fobo66.factcheckerassistant.api.models.Claim
import kotlinx.serialization.Serializable

@Stable
@Serializable
sealed interface Screen : NavKey {
    @Immutable
    @Serializable
    data object Search : Screen

    @Immutable
    @Serializable
    data class SearchResult(val claim: Claim?) : Screen

    @Immutable
    @Serializable
    data object Guide : Screen
}
