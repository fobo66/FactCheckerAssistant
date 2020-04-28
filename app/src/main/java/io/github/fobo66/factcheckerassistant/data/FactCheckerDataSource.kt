package io.github.fobo66.factcheckerassistant.data

import androidx.paging.PageKeyedDataSource
import io.github.fobo66.factcheckerassistant.BuildConfig
import io.github.fobo66.factcheckerassistant.api.FactCheckerApi
import io.github.fobo66.factcheckerassistant.api.models.Claim
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class FactCheckerDataSource(
    private val query: String,
    private val factCheckerApi: FactCheckerApi,
    private val scope: CoroutineScope
) : PageKeyedDataSource<String, Claim>() {

    private var nextPageToken: String? = null

    override fun loadInitial(
        params: LoadInitialParams<String>,
        callback: LoadInitialCallback<String, Claim>
    ) {
        scope.launch {
            val result = factCheckerApi.search(
                query,
                pageSize = params.requestedLoadSize,
                key = BuildConfig.API_KEY
            )
            nextPageToken = result.nextPageToken
            callback.onResult(result.claims, null, nextPageToken)
        }
    }

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<String, Claim>) {
        scope.launch {
            val result = factCheckerApi.search(
                query,
                pageSize = params.requestedLoadSize,
                pageToken = nextPageToken,
                key = BuildConfig.API_KEY
            )
            nextPageToken = result.nextPageToken
            callback.onResult(result.claims, nextPageToken)
        }
    }

    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<String, Claim>) {
        // do nothing
    }
}