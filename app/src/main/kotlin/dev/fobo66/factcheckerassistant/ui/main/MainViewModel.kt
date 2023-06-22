package dev.fobo66.factcheckerassistant.ui.main

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.fobo66.factcheckerassistant.api.models.Claim
import dev.fobo66.factcheckerassistant.data.FactCheckRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

@HiltViewModel
class MainViewModel @Inject constructor(
    private val factCheckRepository: FactCheckRepository,
    private val handle: SavedStateHandle
) : ViewModel() {

    private val query = handle.getLiveData<String>(KEY_QUERY)
        .asFlow()
        .stateIn(viewModelScope, SharingStarted.Lazily, "")

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val claims = query
        .filterNot { it.isNullOrBlank() }
        .debounce(SEARCH_DEBOUNCE.milliseconds)
        .flatMapLatest { query ->
            factCheckRepository.search(query, DEFAULT_PAGE_SIZE).flow
        }
        .cachedIn(viewModelScope)

    val selectedClaim = handle.getLiveData<Claim>(KEY_SELECTED_CLAIM)
        .asFlow()
        .stateIn(viewModelScope, SharingStarted.Lazily, null)

    fun search(query: String?) {
        handle.getLiveData<String>(KEY_QUERY).postValue(query)
    }

    fun selectClaim(claim: Claim?) {
        handle.getLiveData<Claim>(KEY_SELECTED_CLAIM).postValue(claim)
    }

    companion object {
        private const val KEY_QUERY = "query"
        private const val KEY_SELECTED_CLAIM = "claim"
        private const val DEFAULT_PAGE_SIZE = 10
        private const val SEARCH_DEBOUNCE = 300
    }
}
