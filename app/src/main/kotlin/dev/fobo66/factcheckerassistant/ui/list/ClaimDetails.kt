package dev.fobo66.factcheckerassistant.ui.list

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.fobo66.factcheckerassistant.R
import dev.fobo66.factcheckerassistant.api.models.Claim
import dev.fobo66.factcheckerassistant.api.models.ClaimReview
import dev.fobo66.factcheckerassistant.api.models.Publisher
import dev.fobo66.factcheckerassistant.ui.theme.FactCheckerAssistantTheme
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlinx.collections.immutable.toImmutableList

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ClaimDetails(claim: Claim?, modifier: Modifier = Modifier) {
    Crossfade(
        targetState = claim?.claimReview?.toImmutableList(),
        label = "ClaimDetails",
        modifier = modifier
    ) {
        if (it.isNullOrEmpty()) {
            Text(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                textAlign = TextAlign.Center,
                text = stringResource(id = R.string.claim_reviews_empty)
            )
        } else {
            LazyColumn(
                contentPadding = PaddingValues(vertical = 8.dp, horizontal = 16.dp)
            ) {
                stickyHeader {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.padding(vertical = 16.dp)
                    ) {
                        Text(
                            text = claim?.text.orEmpty(),
                            style = MaterialTheme.typography.titleLarge
                        )
                        Text(
                            text = stringResource(R.string.claim_reviews_title),
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
                items(it) { review ->
                    ClaimReviewItem(
                        review,
                        modifier = Modifier.animateItem()
                    )
                }
            }
        }
    }
}

@Composable
fun ClaimReviewItem(claimReview: ClaimReview, modifier: Modifier = Modifier) {
    OutlinedCard(
        modifier = modifier
    ) {
        val uriHandler = LocalUriHandler.current
        ListItem(
            leadingContent = {
                if (claimReview.publisher.name != null) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .background(MaterialTheme.colorScheme.primaryContainer, CircleShape)
                    ) {
                        Text(
                            text = claimReview.publisher.name[0].titlecase(),
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            modifier = Modifier
                                .align(Alignment.Center)
                        )
                    }
                }
            },
            headlineContent = {
                Text(
                    text = claimReview.title.orEmpty()
                )
            },
            supportingContent = {
                Text(
                    text = claimReview.publisher.name.orEmpty()
                )
            }
        )
        Text(
            text = stringResource(
                id = R.string.claim_rating,
                claimReview.textualRating
            ),
            modifier = Modifier.padding(16.dp)
        )
        Button(
            onClick = {
                uriHandler.openUri(claimReview.url)
            },
            modifier = Modifier
                .align(Alignment.End)
                .padding(16.dp)
        ) {
            Text(text = stringResource(R.string.claim_details_more))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ClaimReviewItemPreview() {
    FactCheckerAssistantTheme {
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
}

@OptIn(ExperimentalTime::class)
@Preview(showBackground = true)
@Composable
private fun ClaimDetailsPreview() {
    FactCheckerAssistantTheme {
        val claim by remember {
            mutableStateOf(
                Claim(
                    "test",
                    "tester",
                    Clock.System.now(),
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
