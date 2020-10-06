package io.github.fobo66.factcheckerassistant.util

import android.content.Context
import android.content.res.Configuration
import android.os.Looper
import androidx.annotation.RestrictTo

@get:RestrictTo(RestrictTo.Scope.LIBRARY)
internal inline val isMainThread: Boolean
    get() = Looper.myLooper() == Looper.getMainLooper()

@RestrictTo(RestrictTo.Scope.LIBRARY)
internal fun checkIsMainThread() = check(isMainThread)


fun Context.isNightModeAvailable(): Boolean {
    val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK

    return currentNightMode == Configuration.UI_MODE_NIGHT_YES
}
