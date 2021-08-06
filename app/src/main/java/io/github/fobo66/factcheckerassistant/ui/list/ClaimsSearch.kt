package io.github.fobo66.factcheckerassistant.ui.list

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import io.github.fobo66.factcheckerassistant.R
import io.github.fobo66.factcheckerassistant.api.models.Claim
import io.github.fobo66.factcheckerassistant.ui.main.MainViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlin.time.ExperimentalTime

@ExperimentalFoundationApi
@ExperimentalAnimationApi
@ExperimentalMaterialApi
@ExperimentalCoroutinesApi
@FlowPreview
@ExperimentalTime
@Composable
fun ClaimsSearch(
    mainViewModel: MainViewModel = hiltViewModel(),
    onSearchResultClick: (Claim?) -> Unit
) {
    val claims = mainViewModel.claims.collectAsLazyPagingItems()
    val query by mainViewModel.query.collectAsState(initial = "")

    LazyColumn {
        stickyHeader {
            TextField(
                value = query,
                onValueChange = {
                    mainViewModel.search(it)
                },
                modifier = Modifier
                    .fillMaxWidth(),
                placeholder = {
                    Text(stringResource(R.string.search_hint))
                },
                leadingIcon = {
                    Icon(imageVector = Icons.Filled.Search, contentDescription = null)
                }
            )
        }

        items(claims) { claim ->
            ClaimItem(claim, onSearchResultClick = onSearchResultClick)
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

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Composable
fun ClaimItem(
    claim: Claim?,
    modifier: Modifier = Modifier,
    onSearchResultClick: (Claim?) -> Unit = {},
    mainViewModel: MainViewModel = hiltViewModel()
) {
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
            Text(
                text = claim?.claimant.orEmpty(),
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(vertical = 4.dp)
            )
            Button(modifier = Modifier.align(Alignment.End), onClick = {
                mainViewModel.selectClaim(claim)
                onSearchResultClick(claim)
            }) {
                Text(stringResource(R.string.claim_learn_more))
            }
        }
    }
}