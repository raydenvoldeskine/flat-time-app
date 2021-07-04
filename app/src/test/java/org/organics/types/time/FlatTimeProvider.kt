package org.organics.types.time

import org.organics.types.Duration


class FlatTimeProvider : TimeProvider {

    private var tokenCounter = 0L
    private var frozen = false
    private var records: HashMap<TimeToken, MutableList<TimeRecord>> = hashMapOf()

    fun freeze(){
        frozen = true
        records.clear()
    }

    override fun invokeOnceAfter(duration: Duration, callback: () -> Unit): TimeToken {
        if (!frozen) {
            callback()
            return 0
        } else {
            val token = generateToken()
            records[token] = arrayListOf<TimeRecord>(CallRecord(duration, false, callback))
            return token
        }
    }

    override fun invokePeriodically(duration: Duration, callback: () -> Unit): TimeToken {
        if (!frozen) {
            callback()
            return 0
        } else {
            val token = generateToken()
            records[token] = arrayListOf<TimeRecord>(CallRecord(duration, true, callback))
            return token
        }
    }

    override fun discard(token: TimeToken) {
        if (!frozen) {
            // do nothing
        } else {
            records[token]?.add(StopRecord())
        }
    }

    fun wait(duration: Duration){
        if (frozen){
            for ((_, list) in records){
                list.add(DurationRecord(duration))
            }
        }
    }

    fun unfreeze(){
        // Note that timers are processed sequentionally and
        // independently. So if one class has two or more timers
        // and they are interdependent, this will not work properly
        // This is a current limitation. It can be withdrawn
        // But it requires efforts to write a "timeline processor"
        // which will dispatch calls with the given precision
        for ((token, list) in records){
            var currentTime = Duration.zero()
            var pendingCall: CallRecord? = null
            var pendingCallTime: Duration = Duration.zero()
            for (record in list) {
                when (record) {
                    is DurationRecord -> {
                        currentTime = currentTime.plus(record.duration)
                        if (pendingCall != null){
                            if (pendingCallTime.plus(pendingCall.duration).isBefore(currentTime)){
                                // for repeated calls, multiple of invokations could have happened during wait
                                val numberOfInvokations = currentTime.minus(pendingCallTime).milliseconds() / pendingCall.duration.milliseconds()
                                for (i in 1..numberOfInvokations) {
                                    pendingCall.callback.invoke()
                                }

                                if (pendingCall.repeat) {
                                    pendingCallTime = pendingCallTime.plus(pendingCall.duration).copy()
                                } else {
                                    pendingCall = null
                                }

                            }
                        }
                    }
                    is StopRecord -> pendingCall = null
                    is CallRecord -> {
                        pendingCall = record
                        pendingCallTime = currentTime.copy()
                    }
                    else -> {}
                }
            }
        }
    }

    private fun generateToken() : TimeToken {
        tokenCounter++
        return tokenCounter
    }

    private open class TimeRecord() {}

    private class CallRecord(val duration: Duration, val repeat: Boolean, val callback: () -> Unit) : TimeRecord() {}

    private class StopRecord() : TimeRecord() {}

    private class DurationRecord(val duration: Duration) : TimeRecord()
}

fun TimeProviders.flat() : FlatTimeProvider {
    return FlatTimeProvider()
}