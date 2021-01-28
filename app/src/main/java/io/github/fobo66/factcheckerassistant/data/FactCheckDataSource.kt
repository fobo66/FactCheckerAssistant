package io.github.fobo66.factcheckerassistant.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import io.github.fobo66.factcheckerassistant.BuildConfig
import io.github.fobo66.factcheckerassistant.api.FactCheckApi
import io.github.fobo66.factcheckerassistant.api.models.Claim
import retrofit2.HttpException
import timber.log.Timber

class FactCheckDataSource(
        private val query: String,
        private val factCheckApi: FactCheckApi,
        private val languageTag: String
) : PagingSource<String, Claim>() {
    private var nextPageToken: String? = null

    override suspend fun load(params: LoadParams<String>): LoadResult<String, Claim> {
        try {
            Timber.d("Loading claims started")
            val result = factCheckApi.search(
                query,
                languageCode = languageTag,
                pageSize = params.loadSize,
                pageToken = params.key,
                key = BuildConfig.API_KEY
            )
            Timber.d("Loading claims finished")
            nextPageToken = result.nextPageToken
            return LoadResult.Page(result.claims, null, nextPageToken)
        } catch (e: HttpException) {
            Timber.e(e, "Error occurred during loading claims for query %s", query)
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<String, Claim>): String? {
        return nextPageToken
    }
}
