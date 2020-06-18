package io.github.fobo66.factcheckerassistant.ui.main

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import io.github.fobo66.factcheckerassistant.data.FactCheckRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flatMapLatest

@ExperimentalCoroutinesApi
class MainViewModel(
    private val factCheckRepository: FactCheckRepository,
    private val handle: SavedStateHandle
) : ViewModel() {

    val claims = handle.getLiveData<String>(KEY_QUERY).asFlow()
        .flatMapLatest { query ->
            factCheckRepository.search(query, DEFAULT_PAGE_SIZE, viewModelScope)
        }

    fun search(query: String?) {
        handle.set(KEY_QUERY, query)
    }

    companion object {
        private const val KEY_QUERY = "query"
        private const val DEFAULT_PAGE_SIZE = 10
    }
}
