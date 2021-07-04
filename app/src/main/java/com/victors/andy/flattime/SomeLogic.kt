package com.victors.andy.flattime

import org.organics.types.Duration
import org.organics.types.time.TimeProvider

class SomeLogic(time: TimeProvider, interval: Duration) {

    var onEventTriggered: (() -> Unit)? = null

    private val timer = CompactOnceTimer(time, interval) {
        onEventTriggered?.invoke()
    }

    fun start() {
        timer.activate()
    }
}