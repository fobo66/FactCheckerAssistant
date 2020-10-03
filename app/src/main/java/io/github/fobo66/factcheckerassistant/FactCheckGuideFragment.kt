package io.github.fobo66.factcheckerassistant

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.fragment.app.Fragment
import androidx.ui.tooling.preview.Preview
import io.github.fobo66.factcheckerassistant.ui.theme.DarkColors
import io.github.fobo66.factcheckerassistant.ui.theme.LightColors
import io.github.fobo66.factcheckerassistant.util.isNightModeAvailable

class FactCheckGuideFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_fact_check_guide, container, false)
            .apply {
                findViewById<ComposeView>(R.id.factCheckGuideRoot).setContent {
                    FactCheckGuideContent(requireContext().isNightModeAvailable())
                }
            }
    }

    @Composable
    fun FactCheckGuideContent(isNightMode: Boolean) = MaterialTheme(
        colors = if (isNightMode) DarkColors else LightColors
    ) {
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
        FactCheckGuideContent(true)
    }
}
