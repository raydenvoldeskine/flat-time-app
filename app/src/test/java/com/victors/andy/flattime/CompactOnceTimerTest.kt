package com.victors.andy.flattime

import junit.framework.Assert.assertFalse
import junit.framework.Assert.assertTrue
import org.junit.Assert
import org.junit.Test
import org.organics.types.Duration
import org.organics.types.time.TimeProviders
import org.organics.types.time.flat

class CompactOnceTimerTest {

    @Test
    fun testThatActivatedTimerWillFireAfterTimeIsPassed() {
        val time = TimeProviders.flat()
        val duration = Duration.ofSeconds(10)
        var fired = false
        val timer = CompactOnceTimer(time, duration) {
            fired = true
        }
        time.freeze()
        timer.activate()
        time.wait(duration.plus(Duration.ofSeconds(1)))
        time.unfreeze()
        assertTrue(fired)
    }

    @Test
    fun testThatActivatedTimerWillNotFireEarlierThanExpected() {
        val time = TimeProviders.flat()
        val duration = Duration.ofSeconds(10)
        var fired = false
        val timer = CompactOnceTimer(time, duration) {
            fired = true
        }
        time.freeze()
        timer.activate()
        time.wait(duration.minus(Duration.ofSeconds(1)))
        time.unfreeze()
        assertFalse(fired)
    }

    @Test
    fun testThatStoppingTimerWillNotFire() {
        val time = TimeProviders.flat()
        val duration = Duration.ofSeconds(10)
        var fired = false
        val timer = CompactOnceTimer(time, duration) {
            fired = true
        }
        time.freeze()
        timer.activate()
        time.wait(duration.minus(Duration.ofSeconds(1)))
        timer.deactivate()
        time.unfreeze()
        assertFalse(fired)
    }

}