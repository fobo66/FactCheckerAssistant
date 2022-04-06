package io.github.fobo66.factcheckerassistant.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import dagger.hilt.android.scopes.ViewModelScoped
import io.github.fobo66.factcheckerassistant.api.FactCheckApi
import io.github.fobo66.factcheckerassistant.api.models.Claim
import io.github.fobo66.factcheckerassistant.util.LocaleProvider
import javax.inject.Inject

@ViewModelScoped
class FactCheckRepository @Inject constructor(
    private val factCheckApi: FactCheckApi,
    private val localeProvider: LocaleProvider
) {
    fun search(query: String, pageSize: Int): Pager<String, Claim> {
        return Pager(
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
}
