package org.organics.types.time

import org.organics.types.Duration


class OnceTriggerLogic(val time: TimeProvider, val duration: Duration) {
    var triggered: Boolean = false
    var token: TimeToken? = null
    fun trigger() {
        token = time.invokeOnceAfter(duration) {
            triggered = true
        }
    }

    fun stop() {
        token?.let { time.discard(it) }
    }
}

