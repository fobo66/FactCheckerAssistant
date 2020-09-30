package io.github.fobo66.factcheckerassistant

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Text
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment

class FactCheckGuideFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_fact_check_guide, container, false)
            .apply {
                findViewById<ComposeView>(R.id.factCheckGuideRoot).setContent {
                    // In Compose world
                    MaterialTheme {
                        Text("Hello Compose!")
                    }
                }
            }
    }
}
