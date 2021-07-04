package org.organics.types.time

import org.organics.types.Duration


class NeverTimeProvider : TimeProvider {
    override fun invokeOnceAfter(duration: Duration, callback: () -> Unit): TimeToken {
        return 0;
    }

    override fun invokePeriodically(duration: Duration, callback: () -> Unit): TimeToken {
        return 0;
    }

    override fun discard(token: TimeToken) {
    }

}