package com.tutorial.backgroundpopupapp

import android.app.Service
import android.content.Intent
import android.os.IBinder

class MyService : Service() {
    override fun onCreate() {
        super.onCreate()
        Thread {
            try {
                Thread.sleep(3000)
                val intent = Intent(this, SecActivity::class.java)
                // 加入 Flag 表示要產生一個新的 Activity
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }.start()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        // START_STICKY : Service 被殺掉，系統會重啟，但是 Intent 會是 null。
        // START_NOT_STICKY : Service 被系統殺掉，不會重啟。
        // START_REDELIVER_INTENT : Service 被系統殺掉，重啟且 Intent 會重傳。
        return START_NOT_STICKY //Service 終止後不再重啟
    }

    override fun onBind(intent: Intent): IBinder? {
        TODO("Return the communication channel to the service.")
        // 由於是啟動 Service，因此 onBind 會是 return null，
        // 由於 Service 是運作在 UI Thread 上面，
        // 因此，你必須將長任務開個 Thread 並且執行在 onStartCommand 方法內。
        return null
    }
}