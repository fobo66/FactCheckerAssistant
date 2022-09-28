package io.github.fobo66.factcheckerassistant.ui.list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import io.github.fobo66.factcheckerassistant.R
import io.github.fobo66.factcheckerassistant.api.models.Claim
import io.github.fobo66.factcheckerassistant.ui.icons.Search

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ClaimsSearch(
    query: String,
    claims: LazyPagingItems<Claim>,
    onSearch: (String) -> Unit,
    modifier: Modifier = Modifier,
    onSearchResultClick: (Claim?) -> Unit = {}
) {
    LazyColumn(modifier = modifier) {
        stickyHeader {
            TextField(
                value = query,
                onValueChange = onSearch,
                modifier = Modifier
                    .fillMaxWidth(),
                placeholder = {
                    Text(stringResource(R.string.search_hint))
                },
                leadingIcon = {
                    Icon(imageVector = Search, contentDescription = null)
                },
                maxLines = 1
            )
        }

        if (claims.itemCount == 0) {
            item {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.CenterHorizontally)
                        .padding(8.dp),
                    text = stringResource(id = R.string.claims_empty_result)
                )
            }
        } else {
            items(claims) { claim ->
                ClaimItem(claim, onSearchResultClick = onSearchResultClick)
            }
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

        if (claims.loadState.append is LoadState.Error) {
            item {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.CenterHorizontally),
                    text = stringResource(id = R.string.claims_loading_error)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClaimItem(
    claim: Claim?,
    modifier: Modifier = Modifier,
    onSearchResultClick: (Claim?) -> Unit
) {
    Card(
        modifier = modifier
            .padding(8.dp),
        onClick = {
            onSearchResultClick(claim)
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .wrapContentWidth(Alignment.End)
        ) {
            Text(
                text = claim?.text.orEmpty(),
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = claim?.claimant.orEmpty(),
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(vertical = 4.dp)
            )
        }
    }
}
