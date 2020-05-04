package io.github.fobo66.factcheckerassistant.data

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import androidx.paging.toLiveData
import io.github.fobo66.factcheckerassistant.api.FactCheckApi
import io.github.fobo66.factcheckerassistant.api.models.Claim
import kotlinx.coroutines.CoroutineScope

class FactCheckRepository(
    private val factCheckApi: FactCheckApi
) {
    fun search(query: String, scope: CoroutineScope): LiveData<PagedList<Claim>> {
        val dataSourceFactory = FactCheckDataSourceFactory(query, factCheckApi, scope)
        return dataSourceFactory.toLiveData(pageSize = 10)
    }
}