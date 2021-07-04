package com.victors.andy.flattime

import org.organics.types.Duration
import org.organics.types.time.TimeProvider
import org.organics.types.time.TimeToken

class CompactOnceTimer(private val time: TimeProvider, private val duration: Duration, private val executeOnFire: () -> Unit) {

    private var token: TimeToken? = null

    fun activate() {
        token = time.invokeOnceAfter(duration) {
            executeOnFire.invoke()
        }
    }

    fun deactivate() {
        token?.let {
            time.discard(it)
        }
    }

    fun reactivate() {
        deactivate()
        activate()
    }

}