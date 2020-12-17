package io.github.fobo66.factcheckerassistant

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import com.google.android.material.composethemeadapter.MdcTheme
import dev.chrisbanes.accompanist.insets.ProvideWindowInsets
import dev.chrisbanes.accompanist.insets.statusBarsPadding
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
                    InsetAwareTopAppBar(
                        title = {
                            Text(
                                stringResource(R.string.fact_check_guide_title)
                            )
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
    }

    @Preview
    @Composable
    fun FactCheckGuideContentPreview() {
        FactCheckGuideContent()
    }

    /**
     * A wrapper around [TopAppBar] which uses [Modifier.statusBarsPadding] to shift the app bar's
     * contents down, but still draws the background behind the status bar too.
     */
    @Composable
    private fun InsetAwareTopAppBar(
        title: @Composable() () -> Unit,
        navigationIcon: @Composable() (() -> Unit)? = null,
        actions: @Composable() (RowScope.() -> Unit) = {},
        backgroundColor: Color = MaterialTheme.colors.primarySurface,
        contentColor: Color = contentColorFor(backgroundColor)
    ) {
        Surface(
            color = backgroundColor,
            elevation = topbarElevation
        ) {
            TopAppBar(
                title = title,
                navigationIcon = navigationIcon,
                actions = actions,
                backgroundColor = Color.Transparent,
                contentColor = contentColor,
                elevation = 0.dp,
                modifier = Modifier.statusBarsPadding()
            )
        }
    }

    companion object {
        private val topbarElevation = 4.dp
    }
}
