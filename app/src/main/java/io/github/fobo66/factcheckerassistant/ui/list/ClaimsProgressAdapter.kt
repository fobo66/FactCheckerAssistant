package io.github.fobo66.factcheckerassistant.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import io.github.fobo66.factcheckerassistant.databinding.ClaimsLoadingProgressHeaderBinding

class ClaimsProgressAdapter : LoadStateAdapter<ClaimsProgressViewHolder>() {
    override fun onBindViewHolder(holder: ClaimsProgressViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): ClaimsProgressViewHolder {
        return ClaimsProgressViewHolder(
            ClaimsLoadingProgressHeaderBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }
}

class ClaimsProgressViewHolder(private val binding: ClaimsLoadingProgressHeaderBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(loadState: LoadState) {
        binding.claimsLoadingProgressBar.isVisible = loadState is LoadState.Loading

        if (loadState is LoadState.Error) {
            binding.claimsLoadingError.apply {
                isVisible = true
                text = loadState.error.localizedMessage
            }
        }
    }
}