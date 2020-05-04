package io.github.fobo66.factcheckerassistant.util

import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class FragmentViewBindingProperty<T : ViewBinding>(
    private val viewBinder: ViewBinder<T>
) : ReadOnlyProperty<Fragment, T> {

    private var viewBinding: T? = null
    private val lifecycleObserver = BindingLifecycleObserver()

    @MainThread
    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        checkIsMainThread()
        this.viewBinding?.let { return it }

        val view = thisRef.requireView()
        thisRef.viewLifecycleOwner.lifecycle.addObserver(lifecycleObserver)
        return viewBinder.bind(view).also { vb -> this.viewBinding = vb }
    }


    private inner class BindingLifecycleObserver : LifecycleEventObserver {

        private val mainHandler = Handler(Looper.getMainLooper())

        override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
            if (event == Lifecycle.Event.ON_DESTROY) {
                source.lifecycle.removeObserver(this)
                // Fragment.viewLifecycleOwner call LifecycleObserver.onDestroy() before Fragment.onDestroyView().
                // That's why we need to postpone reset of the viewBinding
                mainHandler.post {
                    viewBinding = null
                }
            }
        }
    }
}

/**
 * Create new [ViewBinding] associated with the [Fragment][this]
 */
inline fun <reified T : ViewBinding> Fragment.viewBinding(): ReadOnlyProperty<Fragment, T> {
    return FragmentViewBindingProperty(DefaultViewBinder(T::class.java))
}

/**
 * Create new [ViewBinding] associated with the [Fragment][this] and allow customize how
 * a [View] will be bounded to the view binding.
 */
inline fun <T : ViewBinding> Fragment.viewBinding(
    crossinline bindView: (View) -> T
): ReadOnlyProperty<Fragment, T> {
    return FragmentViewBindingProperty(viewBinder(bindView))
}
