package io.github.fobo66.factcheckerassistant.util

import timber.log.Timber

class TestTree : Timber.Tree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        println("[${tag ?: "test"}] $message")

        if (t != null) {
            System.err.println(t.stackTrace)
        }
    }
}