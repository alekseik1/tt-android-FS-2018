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

    val BASIC_TIME = 10L
    var currTime = BASIC_TIME
    val CURR_TIME_TAG = "currTime"
    val TAG_TIMER_RUNNING = "timerRunning"
    var isRunning = false

    val numbersConverter = NumbersConverter()

    var timer: CountDownTimer = initTimer(currTime)

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

    fun startTimer() {
        timer = initTimer(currTime)
        timer.start()
        isRunning = true
    }

    fun stopTimer() {
        timer.cancel()
        isRunning = false
    }

    fun getNaturalTime(timeMillis: Long): String {
        val timeSeconds = TimeUnit.MILLISECONDS.toSeconds(timeMillis)
        return numbersConverter.convert(timeSeconds.toInt())
    }

    lateinit var tvHello: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvHello = findViewById(R.id.tv_hello)

        val btnStart = findViewById<Button>(R.id.btn_start)
        btnStart.setOnClickListener {
            if(!isRunning)
                startTimer()
        }

    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        savedInstanceState?.run {
            currTime = getLong(CURR_TIME_TAG, BASIC_TIME)
            isRunning = getBoolean(TAG_TIMER_RUNNING, false)
        }
        if (isRunning) {
            startTimer()
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.run {
            putLong(CURR_TIME_TAG, currTime)
            putBoolean(TAG_TIMER_RUNNING, isRunning)
        }
        super.onSaveInstanceState(outState)
    }

    override fun onStop() {
        if(isRunning)
            stopTimer()
        super.onStop()
    }
}
