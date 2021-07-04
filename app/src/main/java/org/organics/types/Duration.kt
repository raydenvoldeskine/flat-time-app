package org.organics.types

class Duration private constructor(value: Long) {
    protected var value: Long
        private set

    fun milliseconds(): Long {
        return value
    }

    fun seconds(): Long {
        return value / 1000
    }

    fun add(other: Duration) : Duration {
        return Duration.ofMilliseconds(milliseconds() + other.milliseconds())
    }

    fun copy() : Duration {
        return Duration.ofMilliseconds(milliseconds())
    }

    fun multipleBy(factor: Int) : Duration {
        return Duration.ofMilliseconds(milliseconds() * factor)
    }

    fun divideBy(factor: Int) : Duration {
        return Duration.ofMilliseconds(milliseconds() / factor)
    }

    fun plus(other: Duration) : Duration {
        return Duration.ofMilliseconds(milliseconds() + other.milliseconds())
    }

    fun minus(other: Duration) : Duration {
        return Duration.ofMilliseconds(milliseconds() - other.milliseconds())
    }

    fun isBefore(other: Duration) : Boolean {
        return milliseconds() < other.milliseconds()
    }

    companion object {
        @JvmStatic
        fun ofMilliseconds(value: Long): Duration {
            return Duration(value)
        }

        @JvmStatic
        fun ofSeconds(value: Long): Duration {
            return Duration(value * 1000)
        }

        @JvmStatic
        fun zero(): Duration {
            return Duration(0)
        }
    }

    init {
        this.value = value.toLong()
    }
}