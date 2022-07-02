package com.tutorial.attractionssearch.Dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.tutorial.attractionssearch.R

class DialogInfo(context: Context): Dialog(context){
    private var message: String?= null
    private var btnInfoListener: IOBtnInfo? = null
    private var btnGpsListener: IOBtnGps? = null
    // 設定標題功能
    fun setMessage(message: String?): DialogInfo {
        this.message = message
        return this
    }
    // 設定旅店資訊按鈕功能
    fun setInfo(Listener: IOBtnInfo): DialogInfo {
        this.btnInfoListener = Listener
        return this
    }
    // 設定旅店導航按鈕功能
    fun setGps(Listener: IOBtnGps): DialogInfo {
        this.btnGpsListener = Listener
        return this
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_info)

        val btnInfo: Button = findViewById(R.id.btn_info)
        val btnNavigation: Button = findViewById(R.id.btn_gps)
        val tvMsg: TextView = findViewById(R.id.tv_msg)
        // setMessage使用時，更改標題
        message?.let {
            tvMsg.text= it
        }
        // 監聽按鈕，觸發功能
        btnInfo.setOnClickListener {
            btnInfoListener?.btnInfo(this)
        }
        // 監聽按鈕，觸發功能
        btnNavigation.setOnClickListener {
            btnGpsListener?.btnGps(this)
        }
    }
    // 監聽按鈕
    interface IOBtnInfo {
        fun btnInfo(dialog: DialogInfo?)
    }
    interface IOBtnGps {
        fun btnGps(dialog: DialogInfo?)
    }
}