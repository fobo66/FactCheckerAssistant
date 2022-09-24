package io.github.fobo66.factcheckerassistant.util

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector
import io.github.fobo66.factcheckerassistant.R
import io.github.fobo66.factcheckerassistant.ui.icons.GuideIcon

sealed class Screen(
    val route: String,
    @StringRes val resourceId: Int,
    val icon: ImageVector
) {
    object Search : Screen(
        "search",
        R.string.main_fragment_title,
        io.github.fobo66.factcheckerassistant.ui.icons.Search
    )

    object Guide : Screen("guide", R.string.guide_fragment_title, GuideIcon)
}
