package io.github.fobo66.factcheckerassistant.data

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import io.github.fobo66.factcheckerassistant.api.FactCheckApi
import io.github.fobo66.factcheckerassistant.api.models.Claim
import kotlinx.coroutines.CoroutineScope

class FactCheckDataSourceFactory(
    private val query: String,
    private val factCheckApi: FactCheckApi,
    private val scope: CoroutineScope
) : DataSource.Factory<String, Claim>() {
    val sourceLiveData = MutableLiveData<FactCheckDataSource>()
    override fun create(): DataSource<String, Claim> {
        val latestSource = FactCheckDataSource(query, factCheckApi, scope)
        sourceLiveData.postValue(latestSource)
        return latestSource
    }

}
