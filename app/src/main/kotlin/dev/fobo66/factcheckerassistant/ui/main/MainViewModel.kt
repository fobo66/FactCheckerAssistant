package dev.fobo66.factcheckerassistant.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import dev.fobo66.factcheckerassistant.api.models.Claim
import dev.fobo66.factcheckerassistant.data.FactCheckRepository
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.Inject
import dev.zacsweers.metrox.viewmodel.ViewModelKey
import kotlin.time.Duration.Companion.milliseconds
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

private const val STATE_FLOW_TIMEOUT = 5000L
private const val DEFAULT_PAGE_SIZE = 10
private const val SEARCH_DEBOUNCE = 300

@ViewModelKey
@ContributesIntoMap(AppScope::class)
class MainViewModel @Inject constructor(private val factCheckRepository: FactCheckRepository) :
    ViewModel() {

    private val query = MutableStateFlow("")
    private val _selectedClaim: MutableStateFlow<Claim?> = MutableStateFlow(null)

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val claims = query
        .filterNot { it.isBlank() }
        .debounce(SEARCH_DEBOUNCE.milliseconds)
        .flatMapLatest { query ->
            factCheckRepository.search(query, DEFAULT_PAGE_SIZE).flow
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(STATE_FLOW_TIMEOUT),
            initialValue = PagingData.empty()
        )

    val selectedClaim = _selectedClaim.asStateFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(STATE_FLOW_TIMEOUT), null)

    fun search(query: String) {
        this.query.update { query }
    }

    fun selectClaim(claim: Claim?) {
        _selectedClaim.update { claim }
    }
}
