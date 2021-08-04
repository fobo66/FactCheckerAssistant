package io.github.fobo66.factcheckerassistant.util

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Plagiarism
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import io.github.fobo66.factcheckerassistant.R

sealed class Screen(
    val route: String,
    @StringRes val resourceId: Int,
    val icon: ImageVector = Icons.Filled.Favorite
) {
    object Search : Screen("search", R.string.main_fragment_title, Icons.Filled.Search)
    object Details : Screen("claim", R.string.main_fragment_title)
    object Guide : Screen("guide", R.string.guide_fragment_title, Icons.Filled.Plagiarism)
}
