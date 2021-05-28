package io.github.fobo66.factcheckerassistant.ui.main

import android.app.SearchManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.content.getSystemService
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import dagger.hilt.android.AndroidEntryPoint
import dev.chrisbanes.insetter.applyInsetter
import io.github.fobo66.factcheckerassistant.R
import io.github.fobo66.factcheckerassistant.databinding.MainFragmentBinding
import io.github.fobo66.factcheckerassistant.ui.list.ClaimsAdapter
import io.github.fobo66.factcheckerassistant.ui.list.ClaimsProgressAdapter
import io.github.fobo66.factcheckerassistant.util.viewBinding
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.main_fragment) {

    private val mainViewModel: MainViewModel by viewModels()

    private val binding: MainFragmentBinding by viewBinding()

    private lateinit var adapter: ClaimsAdapter

    private val searchView: SearchView
        get() = binding.toolbar.menu.findItem(R.id.app_bar_search).actionView as SearchView

    @ExperimentalPagingApi
    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ClaimsAdapter()
        binding.factCheckResults.adapter = adapter
            .withLoadStateFooter(ClaimsProgressAdapter())

        lifecycleScope.launch {
            mainViewModel.claims.collectLatest { claims ->
                binding.factCheckResults.isVisible = true
                binding.claimsIndicator.isVisible = false
                adapter.submitData(claims)
            }
        }

        val searchManager = requireContext().getSystemService<SearchManager>()
        searchView.apply {
            setSearchableInfo(searchManager?.getSearchableInfo(requireActivity().componentName))
            isSubmitButtonEnabled = true
            isIconified = true
        }

        binding.appbar.applyInsetter {
            type(statusBars = true) {
                padding(top = true)
            }
        }
    }

    override fun onDestroyView() {
        binding.factCheckResults.adapter = null
        super.onDestroyView()
    }
}
