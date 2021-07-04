package com.victors.andy.flattime

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import org.organics.types.Duration
import org.organics.types.time.TimeProviders

class MainActivity : AppCompatActivity() {

    private val timer = CompactOnceTimer(TimeProviders.standard(), Duration.ofSeconds(5)) {
        onTimerFired()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        timer.activate()
    }

    private fun onTimerFired() {
        findViewById<TextView>(R.id.label).text = "Timer fired"
    }
}