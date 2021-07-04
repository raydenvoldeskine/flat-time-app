package org.organics.types.time

object TimeProviders {
    fun standard() : TimeProvider {
        return StandardTimeProvider()
    }

    fun never() : TimeProvider {
        return NeverTimeProvider()
    }
}