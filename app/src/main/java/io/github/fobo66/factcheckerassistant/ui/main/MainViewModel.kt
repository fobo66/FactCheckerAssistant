package io.github.fobo66.factcheckerassistant.ui.main

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.fobo66.factcheckerassistant.data.FactCheckRepository

class MainViewModel(
    private val factCheckRepository: FactCheckRepository,
    private val handle: SavedStateHandle
) : ViewModel() {

    val claims = Transformations.map(handle.getLiveData<String>(KEY_QUERY)) { query ->
        factCheckRepository.search(query, viewModelScope)
    }

    fun search(query: String?) {
        handle.set(KEY_QUERY, query)
    }

    companion object {
        private const val KEY_QUERY = "query"
    }
}