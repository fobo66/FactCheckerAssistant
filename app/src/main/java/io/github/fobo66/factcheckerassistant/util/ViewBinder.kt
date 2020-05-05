package io.github.fobo66.factcheckerassistant.util

import android.view.View
import androidx.viewbinding.ViewBinding

/**
 * Create instance of [ViewBinding] from a [View]
 */
interface ViewBinder<T : ViewBinding> {

    fun bind(view: View): T
}

inline fun <T : ViewBinding> viewBinder(crossinline bindView: (View) -> T): ViewBinder<T> {
    return object : ViewBinder<T> {

        override fun bind(view: View) = bindView(view)
    }
}
