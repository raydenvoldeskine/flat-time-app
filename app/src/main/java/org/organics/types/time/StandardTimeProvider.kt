package org.organics.types.time
import android.os.Handler
import android.os.Looper
import org.organics.types.Duration


class StandardTimeProvider : TimeProvider{

    private var handler = Handler(Looper.getMainLooper())
    private var tokenCounter: Long = 0L
    private val stoppedTokens: MutableList<TimeToken> = arrayListOf()

    override fun invokeOnceAfter(duration: Duration, callback: () -> Unit): TimeToken {
        val token: Long = tokenCounter++
        handler.postDelayed(object : Runnable {
            override fun run() {
                if (!stoppedTokens.contains(token)) {
                    callback()
                }
            }
        }, duration.milliseconds())
        return token
    }

    override fun invokePeriodically(duration: Duration, callback: () -> Unit): TimeToken {
        val token: Long = tokenCounter++
        callAnotherTime(token, duration, callback)
        return token
    }

    private fun callAnotherTime(token: TimeToken, duration: Duration, callback: () -> Unit) {
        handler.postDelayed(object : Runnable {
            override fun run() {
                if (!stoppedTokens.contains(token)) {
                    callback()
                    callAnotherTime(token, duration, callback)
                }
            }
        }, duration.milliseconds())
    }

    override fun discard(token: TimeToken) {
        stoppedTokens.add(token)
    }


}