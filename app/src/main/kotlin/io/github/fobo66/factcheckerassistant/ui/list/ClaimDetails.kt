package io.github.fobo66.factcheckerassistant.ui.list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ListItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.fobo66.factcheckerassistant.R
import io.github.fobo66.factcheckerassistant.api.models.Claim
import io.github.fobo66.factcheckerassistant.api.models.ClaimReview
import io.github.fobo66.factcheckerassistant.api.models.Publisher
import io.github.fobo66.factcheckerassistant.ui.theme.FactCheckerAssistantTheme
import java.util.Date

@ExperimentalMaterialApi
@Composable
fun ClaimDetails(claimState: State<Claim?>, modifier: Modifier = Modifier) {
    val claim by claimState
    Column(modifier = modifier) {
        Text(
            text = claim?.text.orEmpty(),
            style = MaterialTheme.typography.h5
        )
        Text(text = stringResource(R.string.claim_reviews_title), fontWeight = FontWeight.Bold)
        LazyColumn {
            val claimReviews = claim?.claimReview
            if (claimReviews.isNullOrEmpty()) {
                item {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth(Alignment.CenterHorizontally)
                            .padding(8.dp),
                        text = stringResource(id = R.string.claim_reviews_empty)
                    )
                }
            } else {
                items(claimReviews) {
                    ClaimReviewItem(it)
                }
            }
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

@OptIn(ExperimentalMaterialApi::class)
@Preview(showBackground = true)
@Composable
fun ClaimDetailsPreview() {
    FactCheckerAssistantTheme {
        val claimState = remember {
            mutableStateOf(Claim("test", "tester", Date().toString(), listOf()))
        }

        ClaimDetails(claimState)
    }
}
