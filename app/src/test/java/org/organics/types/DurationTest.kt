package org.organics.types

import org.organics.types.Duration.Companion.ofMilliseconds
import org.organics.types.Duration.Companion.ofSeconds
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class DurationTest {
    @Test
    fun testThatObjectCreatedProperlyWhileUsingMillisecondUnits() {
        val value = 1234567
        val duration =
            ofMilliseconds(value.toLong())
        Assert.assertEquals(value.toLong(), duration.milliseconds())
    }

    @Test
    fun testThatObjectCreatedProperlyWhileUsingSecondUnits() {
        val value = 1234
        val duration =
            ofSeconds(value.toLong())
        Assert.assertEquals(value * 1000.toLong(), duration.milliseconds())
    }
}