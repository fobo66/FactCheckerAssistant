package io.github.fobo66.factcheckerassistant.data

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import io.github.fobo66.factcheckerassistant.api.FactCheckerApi
import io.github.fobo66.factcheckerassistant.api.models.Claim
import kotlinx.coroutines.CoroutineScope

class FactCheckerDataSourceFactory(
    private val query: String,
    private val factCheckerApi: FactCheckerApi,
    private val scope: CoroutineScope
) : DataSource.Factory<String, Claim>() {
    val sourceLiveData = MutableLiveData<FactCheckerDataSource>()
    override fun create(): DataSource<String, Claim> {
        val latestSource = FactCheckerDataSource(query, factCheckerApi, scope)
        sourceLiveData.postValue(latestSource)
        return latestSource
    }

}