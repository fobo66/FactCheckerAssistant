package io.github.fobo66.factcheckerassistant.data

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import androidx.paging.toLiveData
import io.github.fobo66.factcheckerassistant.api.FactCheckApi
import io.github.fobo66.factcheckerassistant.api.models.Claim
import io.github.fobo66.factcheckerassistant.util.LocaleProvider
import kotlinx.coroutines.CoroutineScope

class FactCheckRepository(
    private val factCheckApi: FactCheckApi,
    private val localeProvider: LocaleProvider
) {
    fun search(query: String, pageSize: Int, scope: CoroutineScope): LiveData<PagedList<Claim>> {
        val dataSourceFactory = FactCheckDataSourceFactory(
            query,
            factCheckApi,
            localeProvider.currentLocale.toLanguageTag(),
            scope
        )
        return dataSourceFactory.toLiveData(pageSize = pageSize)
    }
}
