package org.organics.types.time

import org.organics.types.Duration


interface TimeProvider{
    fun invokeOnceAfter(duration: Duration, callback: () -> Unit) : TimeToken
    fun invokePeriodically(duration: Duration, callback: () -> Unit) : TimeToken
    fun discard(token: TimeToken)
}

typealias TimeToken = Long