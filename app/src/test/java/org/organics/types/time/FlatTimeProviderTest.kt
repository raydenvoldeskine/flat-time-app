package org.organics.types.time

import org.organics.types.Duration
import junit.framework.Assert.*
import org.junit.Test


class FlatTimeProviderTest {

    @Test
    fun testThatTriggerHappensImmediately() {
        val duration = Duration.ofSeconds(10)
        val logic = OnceTriggerLogic(TimeProviders.flat(), duration)
        logic.trigger()
        assertTrue(logic.triggered)
    }

    @Test
    fun testThatByPassedTimesBelowDurationLogicIsNotTriggered() {
        val duration = Duration.ofSeconds(10)
        val time = TimeProviders.flat()
        val logic = OnceTriggerLogic(time, duration)
        time.freeze()
        logic.trigger()
        time.wait(Duration.ofSeconds(5))
        time.unfreeze()
        assertFalse(logic.triggered)
    }

    @Test
    fun testThatByPassedTimesAboveDurationLogicIsNotTriggered() {
        val duration = Duration.ofSeconds(10)
        val time = TimeProviders.flat()
        val logic = OnceTriggerLogic(time, duration)
        time.freeze()
        logic.trigger()
        time.wait(duration.plus(Duration.ofSeconds(1)))
        time.unfreeze()
        assertTrue(logic.triggered)
    }

    @Test
    fun testThatStoppingBeforeExpirationLogicIsNotTriggered() {
        val duration = Duration.ofSeconds(10)
        val time = TimeProviders.flat()
        val logic = OnceTriggerLogic(time, duration)
        time.freeze()
        logic.trigger()
        time.wait(Duration.ofSeconds(5))
        logic.stop()
        time.unfreeze()
        assertFalse(logic.triggered)
    }

    @Test
    fun testThatContiniousLogicIsTriggeredSecondAndThirdTime() {
        val duration = Duration.ofSeconds(10)
        val time = TimeProviders.flat()
        val logic = ContiniousTriggerLogic(time, duration)
        time.freeze()
        logic.start()
        time.wait(duration.multipleBy(3).plus(Duration.ofSeconds(1)))
        time.unfreeze()
        assertEquals(3, logic.triggerCount)
    }


    @Test
    fun testThatContiniousLogicIsTriggeredButCanBeStopped() {
        val duration = Duration.ofSeconds(10)
        val time = TimeProviders.flat()
        val logic = ContiniousTriggerLogic(time, duration)
        time.freeze()
        logic.start()
        time.wait(duration.multipleBy(2).plus(Duration.ofSeconds(1)))
        logic.stop()
        time.unfreeze()
        assertEquals(2, logic.triggerCount)
    }

}

fun OnceTriggerLogic.equals(value: Int, to: Int): Unit {
    assertEquals(to, value)
}