package io.github.fobo66.factcheckerassistant.ui.guide

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.contentColorFor
import androidx.compose.material.primarySurface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import com.google.android.material.composethemeadapter.MdcTheme
import dev.chrisbanes.accompanist.insets.ProvideWindowInsets
import dev.chrisbanes.accompanist.insets.statusBarsPadding
import dev.fobo66.composemd.MarkdownDocument
import io.github.fobo66.factcheckerassistant.R
import io.github.fobo66.factcheckerassistant.databinding.FragmentFactCheckGuideBinding
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
        ProvideWindowInsets {
            Scaffold(
                topBar = {
                    Surface(
                        color = MaterialTheme.colors.primarySurface,
                        elevation = topbarElevation
                    ) {
                        TopAppBar(
                            title = {
                                Text(
                                    stringResource(R.string.fact_check_guide_title)
                                )
                            },
                            backgroundColor = Color.Transparent,
                            contentColor = contentColorFor(MaterialTheme.colors.primarySurface),
                            elevation = 0.dp,
                            modifier = Modifier.statusBarsPadding()
                        )
                    }
                }
            ) {
                Column(
                    modifier = Modifier
                        .padding(guideTextPadding)
                        .verticalScroll(rememberScrollState())
                ) {
                    MarkdownDocument(LocalContext.current.assets.open("guide.md"))
                }
            }
        }
    }

    @Preview
    @Composable
    fun FactCheckGuideContentPreview() {
        FactCheckGuideContent()
    }

    companion object {
        private val topbarElevation = 4.dp
        private val guideTextPadding = 16.dp
    }
}
