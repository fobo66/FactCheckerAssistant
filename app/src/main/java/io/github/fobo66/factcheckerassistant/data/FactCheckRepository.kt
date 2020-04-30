package io.github.fobo66.factcheckerassistant.data

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import androidx.paging.toLiveData
import io.github.fobo66.factcheckerassistant.api.FactCheckerApi
import io.github.fobo66.factcheckerassistant.api.models.Claim
import kotlinx.coroutines.CoroutineScope

class FactCheckRepository(
    private val factCheckerApi: FactCheckerApi
) {
    fun search(query: String, scope: CoroutineScope): LiveData<PagedList<Claim>> {
        val dataSourceFactory = FactCheckerDataSourceFactory(query, factCheckerApi, scope)
        return dataSourceFactory.toLiveData(pageSize = 10)
    }
}