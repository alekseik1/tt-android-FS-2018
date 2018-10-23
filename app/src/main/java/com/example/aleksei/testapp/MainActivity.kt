package com.example.aleksei.testapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.PersistableBundle
import android.widget.Button
import android.widget.TextView
import java.sql.Time
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    var currTime = 0L
    val BASIC_TIME = 10L

    lateinit var timer: CountDownTimer

    fun initTimer(totalTime: Long): CountDownTimer {
        val timer = object: CountDownTimer(TimeUnit.SECONDS.toMillis(totalTime), TimeUnit.SECONDS.toMillis(1)) {

            override fun onFinish() {
                tvHello.text = "Закончили считать"
            }

            override fun onTick(value: Long) {
                tvHello.text = getNaturalTime(value).toString()
                currTime = TimeUnit.MILLISECONDS.toSeconds(value)
            }
        }
        return timer
    }

    fun getNaturalTime(timeMillis: Long): Long {
        val timeSeconds = TimeUnit.MILLISECONDS.toSeconds(timeMillis)
        return timeSeconds
    }

    lateinit var tvHello: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvHello = findViewById(R.id.tv_hello)

        val btnStart = findViewById<Button>(R.id.btn_start)
        btnStart.setOnClickListener {
            var startTime = BASIC_TIME
            savedInstanceState?.run {
                startTime = getLong("currTime", BASIC_TIME)
            }
            timer = initTimer(startTime)
            timer.start()
        }

    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.putLong("currTime", currTime)
        timer.cancel()
        super.onSaveInstanceState(outState)
    }
}
