package io.github.fobo66.factcheckerassistant.ui.list

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ListItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import io.github.fobo66.factcheckerassistant.R
import io.github.fobo66.factcheckerassistant.api.models.ClaimReview
import io.github.fobo66.factcheckerassistant.api.models.Publisher
import io.github.fobo66.factcheckerassistant.ui.main.MainViewModel

@ExperimentalMaterialApi
@Composable
fun ClaimDetails(viewModel: MainViewModel = hiltViewModel()) {
    val claim by viewModel.selectedClaim.collectAsState()

    LazyColumn {
        items(claim?.claimReview ?: listOf()) {
            ClaimReviewItem(it)
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun ClaimReviewItem(claimReview: ClaimReview, modifier: Modifier = Modifier) {
    ListItem(
        modifier = modifier,
        text = {
            Text(text = claimReview.title.orEmpty())
        },
        secondaryText = {
            Text(
                text = stringResource(
                    id = R.string.claim_rating,
                    claimReview.textualRating
                )
            )
        },
        singleLineSecondaryText = false
    )
}

@ExperimentalMaterialApi
@Preview(showBackground = true)
@Composable
fun ClaimReviewItemPreview() {
    ClaimReviewItem(
        claimReview = ClaimReview(
            Publisher("test", "test.com"),
            "url",
            "test",
            "test",
            "test",
            Locale.current.language
        )
    )
}
