package io.github.fobo66.factcheckerassistant.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import io.github.fobo66.factcheckerassistant.api.FactCheckApi
import io.github.fobo66.factcheckerassistant.api.models.Claim
import io.github.fobo66.factcheckerassistant.util.LocaleProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

class FactCheckRepository(
    private val factCheckApi: FactCheckApi,
    private val localeProvider: LocaleProvider
) {
    fun search(query: String, pageSize: Int, scope: CoroutineScope): Flow<PagingData<Claim>> {
        return Pager(
            config = PagingConfig(pageSize),
            pagingSourceFactory = {
                FactCheckDataSource(
                    query,
                    factCheckApi,
                    localeProvider.currentLocale.toLanguageTag()
                )
            }
        ).flow
    }
}
