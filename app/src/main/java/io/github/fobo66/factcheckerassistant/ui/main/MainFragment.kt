package io.github.fobo66.factcheckerassistant.ui.main

import android.app.SearchManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import io.github.fobo66.factcheckerassistant.R
import io.github.fobo66.factcheckerassistant.databinding.MainFragmentBinding
import io.github.fobo66.factcheckerassistant.util.viewBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class MainFragment : Fragment(R.layout.main_fragment) {

    private val mainViewModel: MainViewModel by sharedViewModel()

    private val binding: MainFragmentBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val searchManager = getSystemService(requireContext(), SearchManager::class.java)
        (binding.toolbar.menu.findItem(R.id.app_bar_search).actionView as SearchView).apply {
            setSearchableInfo(searchManager?.getSearchableInfo(requireActivity().componentName))
            isSubmitButtonEnabled = true
            isIconified = true
        }
    }
}