package org.organics.types.time

import org.organics.types.Duration


class ContiniousTriggerLogic(val time: TimeProvider, val duration: Duration) {
    var triggerCount: Int = 0
    var token: TimeToken? = null
    fun start() {
        token = time.invokePeriodically(duration) {
            triggerCount++
        }
    }

    fun stop() {
        token?.let { time.discard(it) }
    }
}