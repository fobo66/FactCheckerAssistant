package io.github.fobo66.factcheckerassistant.ui.main

import android.app.SearchManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import io.github.fobo66.factcheckerassistant.R
import io.github.fobo66.factcheckerassistant.databinding.MainFragmentBinding
import io.github.fobo66.factcheckerassistant.ui.list.ClaimsAdapter
import io.github.fobo66.factcheckerassistant.ui.list.ClaimsProgressAdapter
import io.github.fobo66.factcheckerassistant.util.viewBinding
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

@ExperimentalCoroutinesApi
class MainFragment : Fragment(R.layout.main_fragment) {

    private val mainViewModel: MainViewModel by sharedViewModel()

    private val binding: MainFragmentBinding by viewBinding()

    private lateinit var adapter: ClaimsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ClaimsAdapter()
        binding.factCheckResults.adapter = adapter
            .withLoadStateHeaderAndFooter(
                header = ClaimsProgressAdapter(),
                footer = ClaimsProgressAdapter()
            )

        lifecycleScope.launch {
            mainViewModel.claims.collectLatest { claims ->
                adapter.submitData(claims)
            }
        }

        val searchManager = getSystemService(requireContext(), SearchManager::class.java)
        (binding.toolbar.menu.findItem(R.id.app_bar_search).actionView as SearchView).apply {
            setSearchableInfo(searchManager?.getSearchableInfo(requireActivity().componentName))
            isSubmitButtonEnabled = true
            isIconified = true
        }
    }

    override fun onDestroyView() {
        binding.factCheckResults.adapter = null
        super.onDestroyView()
    }
}
