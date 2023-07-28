package dev.fobo66.factcheckerassistant.ui.list

import android.text.format.DateUtils
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import dev.fobo66.factcheckerassistant.R
import dev.fobo66.factcheckerassistant.api.models.Claim
import dev.fobo66.factcheckerassistant.ui.theme.FactCheckerAssistantTheme
import java.time.ZoneOffset

@Composable
fun ClaimsSearch(
    query: String,
    claims: LazyPagingItems<Claim>,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    modifier: Modifier = Modifier,
    onSearchResultClick: (Claim?) -> Unit = {}
) {

    Box(modifier = modifier.fillMaxSize()) {
        ClaimsSearchBar(
            query,
            onQueryChange,
            onSearch,
            modifier = Modifier.align(Alignment.TopCenter)
        )
        Crossfade(claims, label = "searchItems") {
            if (it.itemCount == 0) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Text(
                        modifier = Modifier
                            .align(Alignment.Center),
                        text = stringResource(id = R.string.claims_empty_result)
                    )
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(
                        start = 16.dp,
                        top = 72.dp,
                        end = 16.dp,
                        bottom = 16.dp
                    ),
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
                                claim?.claimDate?.toInstant(ZoneOffset.UTC)?.toEpochMilli()
                                    ?: System.currentTimeMillis()
                            ).toString()
                        }
                        ClaimItem(
                            claimant = claim?.claimant,
                            claimDate = claimDate,
                            claimTitle = claim?.text,
                            onItemClick = {
                                onSearchResultClick(claim)
                            })
                    }

                    if (it.loadState.append == LoadState.Loading) {
                        item {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentWidth(Alignment.CenterHorizontally)
                            )
                        }
                    }

                    if (it.loadState.append is LoadState.Error) {
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
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun ClaimsSearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current
    var isActive by rememberSaveable {
        mutableStateOf(false)
    }
    DockedSearchBar(
        query = query,
        onQueryChange = onQueryChange,
        onSearch = {
            onSearch(it)
            focusManager.clearFocus()
            isActive = false
        },
        active = isActive,
        onActiveChange = {
            isActive = it
            if (!isActive) {
                focusManager.clearFocus()
            }
        },
        leadingIcon = {
            Icon(
                Icons.Default.Search,
                contentDescription = stringResource(id = R.string.search_icon_description)
            )
        },
        placeholder = {
            Text(text = stringResource(id = R.string.search_placeholder))
        },
        modifier = modifier
    ) {
        Text(
            text = stringResource(id = R.string.search_suggestion),
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
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
            headlineContent = {
                Text(
                    style = MaterialTheme.typography.titleMedium,
                    text = claimant ?: stringResource(id = R.string.claim_unknown_claimant)
                )
            },
            supportingContent = {
                Text(
                    text = claimDate
                )
            }
        )
        Text(
            text = claimTitle.orEmpty(),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview
@Composable
private fun ClaimItemPreview() {
    FactCheckerAssistantTheme {
        ClaimItem(
            claimant = "test",
            claimDate = "just now",
            claimTitle = "test",
            onItemClick = {}
        )
    }
}
