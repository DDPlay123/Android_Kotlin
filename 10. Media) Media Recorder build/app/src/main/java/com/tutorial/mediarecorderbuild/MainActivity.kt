package com.tutorial.mediarecorderbuild

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var tvText: TextView
    private lateinit var startRecord: Button
    private lateinit var stopRecord: Button
    private lateinit var playRecord: Button
    private lateinit var stopPlay: Button

    private val mediaRecorder = MediaRecorder()
    private val mediaPlayer = MediaPlayer()
    private lateinit var folder: File
    private lateinit var fileName: String

    override fun onDestroy() {
        mediaRecorder.release()
        mediaPlayer.release()
        super.onDestroy()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvText = findViewById(R.id.text)
        startRecord = findViewById(R.id.start_record)
        stopRecord = findViewById(R.id.stop_record)
        playRecord = findViewById(R.id.play_record)
        stopPlay = findViewById(R.id.stop_play_record)

        // mkdir()創一個資料夾，mkdirs()創多個。
        folder = File(filesDir.absolutePath + "/record")
        if (!folder.exists()) folder.mkdir()
        // 錄音權限
        val permission = Manifest.permission.RECORD_AUDIO
        if (ActivityCompat.checkSelfPermission(
                this,
                permission
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this, arrayOf(permission), 0)
        } else {
            button()
        }
    }

    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    private fun button() {
        startRecord.setOnClickListener {
            fileName = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date()) //目前時間
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC) //聲音來源為麥克風
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4) //設定輸出格式
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB) //設定編碼器
            mediaRecorder.setOutputFile(File(folder, fileName).absolutePath) //設定輸出路徑
            try {
                mediaRecorder.prepare() //準備錄音
                mediaRecorder.start() //開始錄音
                tvText.text = "錄音中..."
                startRecord.isEnabled = false
                stopRecord.isEnabled = true
                playRecord.isEnabled = false
                stopPlay.isEnabled = false
            } catch (e: IOException) {
                e.printStackTrace()
                tvText.text = "發生錯誤!!!"
                startRecord.isEnabled = true
                stopRecord.isEnabled = false
                playRecord.isEnabled = false
                stopPlay.isEnabled = false
            }
        }
        stopRecord.setOnClickListener {
            try {
                val file = File(folder, fileName)
                mediaRecorder.stop()
                tvText.text = "已儲存至" + file.absolutePath //定義錄音檔案
                startRecord.isEnabled = true
                stopRecord.isEnabled = false
                playRecord.isEnabled = true
                stopPlay.isEnabled = false
            } catch (e: Exception) {
                e.printStackTrace()
                mediaRecorder.reset() //重置錄音器
                tvText.text = "錄音失敗!!!"
                startRecord.isEnabled = true
                stopRecord.isEnabled = false
                playRecord.isEnabled = false
                stopPlay.isEnabled = false
            }
        }
        playRecord.setOnClickListener {
            val file = File(folder, fileName)
            try {
                mediaPlayer.setDataSource(
                    applicationContext,
                    Uri.fromFile(file)
                ) //設定音訊來源
                mediaPlayer.setVolume(1f, 1f) //設定左右聲道音量
                mediaPlayer.prepare() //準備播放
                mediaPlayer.start() //開始播放
                tvText.text = "播放中..."
                startRecord.isEnabled = false
                stopRecord.isEnabled = false
                playRecord.isEnabled = false
                stopPlay.isEnabled = true
            } catch (e: IOException) {
                e.printStackTrace()
                tvText.text = "發生錯誤!!!"
                startRecord.isEnabled = true
                stopRecord.isEnabled = false
                playRecord.isEnabled = true
                stopPlay.isEnabled = false
            }
        }
        //設定播放器播放完畢的監聽器
        mediaPlayer.setOnCompletionListener {
            it.reset() //重置播放器
            tvText.text = "播放結束!!!"
            startRecord.isEnabled = true
            stopRecord.isEnabled = false
            playRecord.isEnabled = true
            stopPlay.isEnabled = false
        }
        stopPlay.setOnClickListener {
            mediaPlayer.stop()
            mediaPlayer.reset()
            tvText.text = "播放結束!!!"
            startRecord.isEnabled = true
            stopRecord.isEnabled = false
            playRecord.isEnabled = true
            stopPlay.isEnabled = false
        }
    }

    // 要求權限的方法
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // 判斷是否有結果且識別標籤相同
        if (requestCode == 0) {
            // 取出結果並判斷是否允許權限
            if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                finish() //若拒絕給予錄音權限，則關閉應用程式
            } else {
                button()
            }
        }
    }
}