package io.github.fobo66.factcheckerassistant.ui.list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.fobo66.factcheckerassistant.R
import io.github.fobo66.factcheckerassistant.api.models.Claim
import io.github.fobo66.factcheckerassistant.api.models.ClaimReview
import io.github.fobo66.factcheckerassistant.api.models.Publisher
import io.github.fobo66.factcheckerassistant.ui.theme.FactCheckerAssistantTheme
import java.time.LocalDateTime

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ClaimDetails(claim: Claim?, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier
            .padding(horizontal = 16.dp)
    ) {
        stickyHeader {
            Text(
                text = claim?.text.orEmpty(),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(top = 8.dp)
            )
            Text(
                text = stringResource(R.string.claim_reviews_title),
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
        val claimReviews = claim?.claimReview
        if (claimReviews.isNullOrEmpty()) {
            item {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    textAlign = TextAlign.Center,
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClaimReviewItem(claimReview: ClaimReview, modifier: Modifier = Modifier) {
    ListItem(
        modifier = modifier,
        headlineText = {
            Text(text = claimReview.title.orEmpty())
        },
        supportingText = {
            Text(
                text = stringResource(
                    id = R.string.claim_rating,
                    claimReview.textualRating
                )
            )
        }
    )
}

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

@Preview(showBackground = true)
@Composable
fun ClaimDetailsPreview() {
    FactCheckerAssistantTheme {
        val claim by remember {
            mutableStateOf(
                Claim(
                    "test",
                    "tester",
                    LocalDateTime.now().toString(),
                    listOf(
                        ClaimReview(
                            Publisher("test", "test.com"),
                            "url",
                            "test",
                            "test",
                            "test",
                            Locale.current.language
                        ),
                        ClaimReview(
                            Publisher("test", "test.com"),
                            "url",
                            "test",
                            "test",
                            "test",
                            Locale.current.language
                        )
                    )
                )
            )
        }

        ClaimDetails(claim)
    }
}
