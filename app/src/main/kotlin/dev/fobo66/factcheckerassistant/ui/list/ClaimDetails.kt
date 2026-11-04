package dev.fobo66.factcheckerassistant.ui.list

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLocale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewWrapper
import androidx.compose.ui.unit.dp
import dev.fobo66.factcheckerassistant.R
import dev.fobo66.factcheckerassistant.api.models.Claim
import dev.fobo66.factcheckerassistant.api.models.ClaimReview
import dev.fobo66.factcheckerassistant.api.models.Publisher
import dev.fobo66.factcheckerassistant.ui.theme.ThemeWrapper
import kotlin.time.Clock
import kotlinx.collections.immutable.toImmutableList

@Composable
fun ClaimDetails(claim: Claim?, modifier: Modifier = Modifier) {
    Crossfade(
        targetState = claim?.claimReview?.toImmutableList(),
        label = "ClaimDetails",
        modifier = modifier
    ) {
        if (it.isNullOrEmpty()) {
            ClaimDetailsPlaceholder(
                message = stringResource(R.string.claim_reviews_empty),
                modifier = Modifier.fillMaxSize()
            )
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                stickyHeader {
                    TopAppBar(
                        title = {
                            Text(
                                text = claim?.text.orEmpty(),
                                style = MaterialTheme.typography.titleLarge
                            )
                        },
                        subtitle = {
                            Text(
                                text = stringResource(R.string.claim_reviews_title),
                                style = MaterialTheme.typography.titleMedium
                            )
                        },
                        modifier = Modifier.statusBarsPadding()
                    )
                }
                items(it, key = { item -> item.url }) { review ->
                    ClaimReviewItem(
                        review,
                        modifier = Modifier.animateItem()
                            .padding(horizontal = 16.dp)
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
            supportingContent = {
                Text(
                    text = claimReview.publisher.name.orEmpty()
                )
            }
        ) {
            Text(
                text = claimReview.title.orEmpty()
            )
        }
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

@Composable
fun ClaimDetailsPlaceholder(
    modifier: Modifier = Modifier,
    message: String = stringResource(R.string.select_claim)
) {
    Box(modifier = modifier) {
        Text(message, modifier = Modifier.align(Alignment.Center))
    }
}

@Preview(showBackground = true)
@PreviewWrapper(wrapper = ThemeWrapper::class)
@Composable
private fun ClaimReviewItemPreview() {
    val languageCode = LocalLocale.current.platformLocale.language
    ClaimReviewItem(
        claimReview = ClaimReview(
            Publisher("test", "test.com"),
            "url",
            "test",
            "test",
            "test",
            languageCode
        )
    )
}

@Preview(showBackground = true)
@PreviewWrapper(wrapper = ThemeWrapper::class)
@Composable
private fun ClaimDetailsPreview() {
    val languageCode = LocalLocale.current.platformLocale.language
    val claim by remember {
        mutableStateOf(
            Claim(
                "test",
                "tester",
                Clock.System.now(),
                listOf(
                    ClaimReview(
                        Publisher("test", "test.com"),
                        "url1",
                        "test",
                        "test",
                        "test",
                        languageCode
                    ),
                    ClaimReview(
                        Publisher("test", "test.com"),
                        "url2",
                        "test",
                        "test",
                        "test",
                        languageCode
                    )
                )
            )
        )
    }

    ClaimDetails(claim)
}

@Preview(showBackground = true)
@PreviewWrapper(wrapper = ThemeWrapper::class)
@Composable
private fun ClaimDetailsPlaceholderPreview() {
    ClaimDetailsPlaceholder()
}
