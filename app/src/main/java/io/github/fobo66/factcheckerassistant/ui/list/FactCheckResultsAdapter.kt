package io.github.fobo66.factcheckerassistant.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import io.github.fobo66.factcheckerassistant.api.models.Claim
import io.github.fobo66.factcheckerassistant.databinding.FactCheckResultBinding
import io.github.fobo66.factcheckerassistant.ui.main.ClaimsViewHolder

class FactCheckResultsAdapter : PagedListAdapter<Claim, ClaimsViewHolder>(diffUtilCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClaimsViewHolder {
        val binding = FactCheckResultBinding.inflate(LayoutInflater.from(parent.context))
        return ClaimsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ClaimsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val diffUtilCallback = object : DiffUtil.ItemCallback<Claim>() {
            override fun areItemsTheSame(oldItem: Claim, newItem: Claim): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Claim, newItem: Claim): Boolean {
                return oldItem.text == newItem.text
            }

        }
    }
}