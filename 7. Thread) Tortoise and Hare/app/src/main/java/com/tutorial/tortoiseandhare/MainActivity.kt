package com.tutorial.tortoiseandhare

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.widget.Button
import android.widget.SeekBar
import android.widget.Toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private var disRabbit = 0
    private var disTurtle = 0
    private lateinit var btnStart: Button
    private lateinit var sbRabbit: SeekBar
    private lateinit var sbTurtle: SeekBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnStart = findViewById(R.id.start)
        sbRabbit = findViewById(R.id.sb_rabbit)
        sbTurtle = findViewById(R.id.sb_turtle)

        btnStart.setOnClickListener {
            disRabbit = 0
            disTurtle = 0
            sbRabbit.progress = 0
            sbTurtle.progress = 0
            rabbit()
            turtle()
        }
    }

    private fun turtle() {
        GlobalScope.launch(Dispatchers.Main) {
            while (disRabbit < 100 && disTurtle < 100) {
                delay(100)
                disTurtle += 1
                sbTurtle.progress = disTurtle
                if (disRabbit < 100 && disTurtle >= 100) {
                    toast("烏龜勝")
                }
            }
        }
    }

    private fun rabbit() {
        Thread {
            val sleep = arrayOf(true, true, false)
            while (disRabbit < 100 && disTurtle < 100) {
                Thread.sleep(100)
                if (sleep.random()) Thread.sleep(300)
                disRabbit += 3
                val msg = Message()
                msg.what = 1
                handler.sendMessage(msg)
            }
        }.start()
    }

    private val handler = Handler(Looper.getMainLooper()) { msg ->
        if (msg.what == 1) sbRabbit.progress = disRabbit
        if (disRabbit >= 100 && disTurtle < 100) toast("兔子勝")
        return@Handler true
    }

    private fun toast(msg: String) {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}