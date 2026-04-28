package dev.fobo66.factcheckerassistant.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import dev.fobo66.factcheckerassistant.api.FactCheckApi
import dev.fobo66.factcheckerassistant.api.models.Claim
import dev.fobo66.factcheckerassistant.util.LocaleProvider
import dev.zacsweers.metro.Inject

class FactCheckRepository @Inject constructor(
    private val factCheckApi: FactCheckApi,
    private val localeProvider: LocaleProvider
) {
    fun search(query: String, pageSize: Int): Pager<String, Claim> = Pager(
        config = PagingConfig(pageSize),
        pagingSourceFactory = {
            FactCheckDataSource(
                query,
                factCheckApi,
                localeProvider.currentLocale.toLanguageTag()
            )
        }
    )
}
