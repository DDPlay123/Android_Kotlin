package com.tutorial.radiostation

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private lateinit var tvMsg: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvMsg = findViewById(R.id.tv_msg)
        val btnMusic = findViewById<Button>(R.id.btn_music)
        val btnNews = findViewById<Button>(R.id.btn_news)
        val btnSport = findViewById<Button>(R.id.btn_sport)

        btnMusic.setOnClickListener {
            register("music")
        }
        btnNews.setOnClickListener {
            register("news")
        }
        btnSport.setOnClickListener {
            register("sport")
        }
    }

    private val receiver: BroadcastReceiver = object : BroadcastReceiver(){
        override fun onReceive(p0: Context?, p1: Intent?) {
            p1?.extras?.let {
                tvMsg.text = "${it.getString("msg")}"
            }
        }
    }

    private fun register(channel: String){
        // 建立 IntentFilter 物件來指定接收的頻道，並註冊 Receiver
        val intentFilter = IntentFilter(channel)
        registerReceiver(receiver, intentFilter)
        // 啟動MyReceiver，並且傳送頻道Channel
        val intent = Intent(this, MyService::class.java)
        startService(intent.putExtra("channel", channel))
    }

    override fun onDestroy() {
        // 離開Activity時，註銷receiver
        unregisterReceiver(receiver)
        super.onDestroy()
    }
}