package io.github.fobo66.factcheckerassistant.ui.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import io.github.fobo66.factcheckerassistant.R
import io.github.fobo66.factcheckerassistant.api.models.Claim
import io.github.fobo66.factcheckerassistant.api.models.ClaimReview
import io.github.fobo66.factcheckerassistant.api.models.Publisher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.util.*

@ExperimentalCoroutinesApi
@Composable
fun ClaimsList(mainViewModel: MainViewModel) {
    val claims = mainViewModel.claims.collectAsLazyPagingItems()

    LazyColumn {
        items(claims) { claim ->
            ClaimItem(claim)
        }

        if (claims.loadState.append == LoadState.Loading) {
            item {
                CircularProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.CenterHorizontally)
                )
            }
        }
    }
}

@Composable
fun ClaimItem(claim: Claim?, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.padding(8.dp),
        shape = MaterialTheme.shapes.medium.copy(CornerSize(16.dp))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .wrapContentWidth(Alignment.End)
        ) {
            Text(
                text = claim?.text.orEmpty(),
                style = MaterialTheme.typography.h5,
            )
            Text(text = claim?.claimant.orEmpty(), modifier = Modifier.align(Alignment.End))
            Text(
                text = stringResource(
                    id = R.string.claim_rating,
                    claim?.claimReview?.get(0)?.textualRating.orEmpty()
                )
            )
            Button(onClick = { /*TODO*/ }, modifier = Modifier.align(Alignment.End)) {
                Text(text = stringResource(id = R.string.claim_learn_more))
            }
        }
    }
}

@Preview(widthDp = 300)
@Composable
fun ClaimItemPreview() {
    ClaimItem(
        claim = Claim(
            "Imran Khan read COVID curve upside down to claim that the curve is flattening in Pakistan",
            "by Joe Smith",
            Date().toString(),
            listOf(
                ClaimReview(
                    Publisher("", ""),
                    "",
                    "",
                    Date().toString(),
                    "True",
                    Locale.US.toLanguageTag()
                )
            )
        )
    )
}
