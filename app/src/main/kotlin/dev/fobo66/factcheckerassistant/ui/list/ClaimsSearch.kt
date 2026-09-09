package dev.fobo66.factcheckerassistant.ui.list

import android.text.format.DateUtils
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSearchBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewWrapper
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import dev.fobo66.factcheckerassistant.R
import dev.fobo66.factcheckerassistant.api.models.Claim
import dev.fobo66.factcheckerassistant.ui.theme.ThemeWrapper
import kotlin.time.ExperimentalTime
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun ClaimsSearch(
    claims: LazyPagingItems<Claim>,
    onSearch: (String) -> Unit,
    onSearchResultClick: (Claim?) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        ClaimsSearchBar(
            onSearch = onSearch,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Crossfade(claims, label = "searchItems", modifier = Modifier.weight(1f)) {
            if (it.itemCount == 0) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = stringResource(id = R.string.claims_empty_result)
                    )
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)

                ) {
                    items(
                        count = it.itemCount,
                        key = it.itemKey(),
                        contentType = it.itemContentType()
                    ) { index ->
                        val claim = it[index]
                        val context = LocalContext.current

                        val claimDate = remember {
                            DateUtils.getRelativeTimeSpanString(
                                context,
                                claim?.claimDate?.toEpochMilliseconds()
                                    ?: System.currentTimeMillis()
                            ).toString()
                        }
                        ClaimItem(
                            claimant = claim?.claimant,
                            claimDate = claimDate,
                            claimTitle = claim?.text,
                            onItemClick = {
                                onSearchResultClick(claim)
                            }
                        )
                    }

                    claimsListFooter(it)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
private fun LazyListScope.claimsListFooter(items: LazyPagingItems<Claim>) {
    if (items.loadState.append == LoadState.Loading) {
        item {
            LoadingIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.CenterHorizontally)
            )
        }
    }

    if (items.loadState.append is LoadState.Error) {
        item {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.CenterHorizontally),
                text = stringResource(id = R.string.claims_loading_error),
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}

@Composable
private fun ClaimsSearchBar(onSearch: (String) -> Unit, modifier: Modifier = Modifier) {
    val queryState = rememberTextFieldState()
    val searchBarState = rememberSearchBarState()
    SearchBar(
        state = searchBarState,
        inputField = {
            SearchBarDefaults.InputField(
                textFieldState = queryState,
                searchBarState = searchBarState,
                onSearch = {
                    onSearch(queryState.text.toString())
                },
                placeholder = {
                    Text(text = stringResource(id = R.string.search_placeholder))
                },
                leadingIcon = {
                    Icon(
                        painterResource(R.drawable.ic_search),
                        contentDescription = stringResource(id = R.string.search_icon_description)
                    )
                }
            )
        },
        modifier = modifier
    )
}

@Composable
fun ClaimItem(
    claimDate: String,
    onItemClick: () -> Unit,
    modifier: Modifier = Modifier,
    claimant: String? = null,
    claimTitle: String? = null
) {
    ElevatedCard(
        modifier = modifier
            .fillMaxWidth(),
        onClick = onItemClick
    ) {
        ListItem(
            supportingContent = {
                Text(
                    text = claimDate
                )
            }
        ) {
            Text(
                style = MaterialTheme.typography.titleMedium,
                text = claimant ?: stringResource(id = R.string.claim_unknown_claimant)
            )
        }

        Text(
            text = claimTitle.orEmpty(),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview
@PreviewWrapper(wrapper = ThemeWrapper::class)
@Composable
private fun ClaimItemPreview() {
    ClaimItem(
        claimant = "test",
        claimDate = "just now",
        claimTitle = "test",
        onItemClick = {}
    )
}

@OptIn(ExperimentalTime::class)
@Preview
@PreviewWrapper(wrapper = ThemeWrapper::class)
@Composable
private fun ClaimSearchPreview() {
    val pagingData = remember {
        PagingData.from(
            listOf(
                Claim(text = "test", claimant = "test")
            )
        )
    }
    val claims = MutableStateFlow(pagingData).collectAsLazyPagingItems()

    ClaimsSearch(
        claims = claims,
        onSearch = {},
        onSearchResultClick = {}
    )
}
