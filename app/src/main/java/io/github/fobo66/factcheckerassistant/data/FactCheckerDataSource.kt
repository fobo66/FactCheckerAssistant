package io.github.fobo66.factcheckerassistant.data

import androidx.paging.PageKeyedDataSource
import io.github.fobo66.factcheckerassistant.api.models.Claim

class FactCheckerDataSource(
        val query: String
) : PageKeyedDataSource<String, Claim>() {
    override fun loadInitial(
            params: LoadInitialParams<String>,
            callback: LoadInitialCallback<String, Claim>
    ) {
        TODO("Not yet implemented")
    }

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<String, Claim>) {
        TODO("Not yet implemented")
    }

    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<String, Claim>) {
        // do nothing
    }
}