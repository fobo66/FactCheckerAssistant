package io.github.fobo66.factcheckerassistant.ui.main

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.fobo66.factcheckerassistant.data.FactCheckRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val factCheckRepository: FactCheckRepository,
    private val handle: SavedStateHandle
) : ViewModel() {

    val query = handle.getLiveData<String>(KEY_QUERY)

    @ExperimentalCoroutinesApi
    val claims = query
        .asFlow()
        .flatMapLatest { query ->
            factCheckRepository.search(query, DEFAULT_PAGE_SIZE).flow
        }
        .cachedIn(viewModelScope)

    fun search(query: String?) {
        this.query.postValue(query)
    }

    companion object {
        private const val KEY_QUERY = "query"
        private const val DEFAULT_PAGE_SIZE = 10
    }
}
