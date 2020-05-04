package io.github.fobo66.factcheckerassistant.ui.main

import androidx.recyclerview.widget.RecyclerView
import io.github.fobo66.factcheckerassistant.R
import io.github.fobo66.factcheckerassistant.api.models.Claim
import io.github.fobo66.factcheckerassistant.databinding.FactCheckResultBinding

class ClaimsViewHolder(private val binding: FactCheckResultBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Claim?) {
        binding.claimText.text = item?.text
        binding.claimant.text =
            binding.root.context.getString(R.string.fact_check_result_claimant, item?.claimant)
        binding.claimReviewResult.text =
            binding.root.context.getString(
                R.string.fact_check_result_rating,
                item?.claimReview?.get(0)?.textualRating
            )
    }
}
