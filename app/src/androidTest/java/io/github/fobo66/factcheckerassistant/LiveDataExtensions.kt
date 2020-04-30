package io.github.fobo66.factcheckerassistant

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

// Adapted from https://github.com/android/architecture-components-samples/blob/master/BasicSample/app/src/androidTest/java/com/example/android/persistence/LiveDataTestUtil.java
inline fun <reified T> LiveData<T>.extractValue(): T? {
    val liveDataValue = arrayOfNulls<T?>(1)
    val latch = CountDownLatch(1)
    val observer: Observer<T> = object : Observer<T> {
        override fun onChanged(t: T) {
            liveDataValue[0] = t
            latch.countDown()
            removeObserver(this)
        }

    }
    observeForever(observer)
    latch.await(1, TimeUnit.SECONDS)
    return liveDataValue[0]
}