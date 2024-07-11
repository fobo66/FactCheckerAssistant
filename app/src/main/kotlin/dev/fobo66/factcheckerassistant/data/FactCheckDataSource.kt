package dev.fobo66.factcheckerassistant.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import dev.fobo66.factcheckerassistant.BuildConfig
import dev.fobo66.factcheckerassistant.api.FactCheckApi
import dev.fobo66.factcheckerassistant.api.models.Claim
import io.ktor.client.plugins.ResponseException
import java.io.IOException
import timber.log.Timber

class FactCheckDataSource(
    private val query: String,
    private val factCheckApi: FactCheckApi,
    private val languageTag: String
) : PagingSource<String, Claim>() {
    private var nextPageToken: String? = null

    override suspend fun load(params: LoadParams<String>): LoadResult<String, Claim> = try {
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
        LoadResult.Page(result.claims ?: listOf(), null, nextPageToken)
    } catch (e: ResponseException) {
        Timber.e(e, "Error occurred during loading claims for query %s", query)
        LoadResult.Error(e)
    } catch (e: IOException) {
        Timber.e(e, "Error occurred during loading claims for query %s", query)
        LoadResult.Error(e)
    }

    override fun getRefreshKey(state: PagingState<String, Claim>): String? = nextPageToken
}
