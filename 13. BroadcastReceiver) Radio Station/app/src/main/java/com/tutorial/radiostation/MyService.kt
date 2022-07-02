package com.tutorial.radiostation

import android.app.Service
import android.content.Intent
import android.os.IBinder

class MyService : Service() {
    private var channel = ""

    private fun broadcast(msg: String){
        sendBroadcast(Intent(channel).putExtra("msg", msg))
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        intent?.extras?.let {
            channel = it.getString("channel").toString()
        }
        broadcast(
            when(channel){
                "music" -> "音樂頻道"
                "news" -> "新聞頻道"
                "sport" -> "運動頻道"
                else -> "頻道錯誤"
            }
        )
        Thread{
            try {
                Thread.sleep(3000)
                broadcast(
                    when(channel){
                        "music" -> "播放音樂廣播"
                        "news" -> "播放新聞廣播"
                        "sport" -> "播放運動廣播"
                        else -> "頻道錯誤"
                    }
                )
            }catch (e: InterruptedException){
                e.printStackTrace()
            }
        }.start()
        // START_STICKY : Service 被殺掉，系統會重啟，但是 Intent 會是 null。
        // START_NOT_STICKY : Service 被系統殺掉，不會重啟。
        // START_REDELIVER_INTENT : Service 被系統殺掉，重啟且 Intent 會重傳。
        return START_STICKY
    }

    override fun onBind(intent: Intent): IBinder? {
        TODO("Return the communication channel to the service.")
        // 由於是啟動 Service，因此 onBind 會是 return null，
        // 由於 Service 是運作在 UI Thread 上面，
        // 因此，你必須將長任務開個 Thread 並且執行在 onStartCommand 方法內。
        return null
    }
}