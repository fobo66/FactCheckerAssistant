package io.github.fobo66.factcheckerassistant

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.fragment.app.Fragment
import androidx.ui.tooling.preview.Preview
import com.google.android.material.composethemeadapter.MdcTheme
import io.github.fobo66.factcheckerassistant.databinding.FragmentFactCheckGuideBinding
import io.github.fobo66.factcheckerassistant.databinding.MainFragmentBinding
import io.github.fobo66.factcheckerassistant.util.viewBinding

class FactCheckGuideFragment : Fragment(R.layout.fragment_fact_check_guide) {

    private val binding: FragmentFactCheckGuideBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.factCheckGuideRoot.setContent {
            FactCheckGuideContent()
        }
    }

    @Composable
    fun FactCheckGuideContent() = MdcTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(stringResource(R.string.fact_check_guide_title))
                    }
                )
            }
        ) {
            Column {
                Text(text = "Check the source")
                Text(text = "Check the source")
                Text(text = "Check the source")
                Text(text = "Check the source")
            }
        }
    }

    @Preview
    @Composable
    fun FactCheckGuideContentPreview() {
        FactCheckGuideContent()
    }
}
