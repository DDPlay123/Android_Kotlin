package com.tutorial.taiwanhighspeedrailsystem.Dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import com.tutorial.taiwanhighspeedrailsystem.R

class DialogWarn(context: Context): Dialog(context) {
    private var message: String?= null
    private var negativeListener: IOnCancelListener? = null
    private var positiveListener: IOnConfirmListener? = null
    private var closeListener: IOnCloseListener? = null
    // 設定功能
    fun setMessage(message: String?): DialogWarn {
        this.message = message
        return this
    }
    fun negative(Listener: IOnCancelListener): DialogWarn {
        this.negativeListener = Listener
        return this
    }
    fun positive(Listener: IOnConfirmListener): DialogWarn {
        this.positiveListener = Listener
        return this
    }
    fun close(Listener: IOnCloseListener): DialogWarn {
        this.closeListener = Listener
        return this
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_warn)

        val btnNegative: Button = findViewById(R.id.btn_negative)
        val btnPositive: Button = findViewById(R.id.btn_positive)
        val btnClose: ImageButton = findViewById(R.id.btn_close)
        val tvMsg: TextView = findViewById(R.id.tv_msg)
        // setMessage使用
        message?.let {
            tvMsg.text= it
        }
        // 監聽按鈕，觸發功能
        btnNegative.setOnClickListener {
            negativeListener?.negative(this)
        }
        // 監聽按鈕，觸發功能
        btnPositive.setOnClickListener {
            positiveListener?.positive(this)
        }
        // 監聽按鈕，觸發功能
        btnClose.setOnClickListener {
            closeListener?.close(this)
        }

    }
    // 監聽按鈕
    interface IOnCancelListener {
        fun negative(dialog: DialogWarn?)
    }
    interface IOnConfirmListener {
        fun positive(dialog: DialogWarn?)
    }
    interface IOnCloseListener {
        fun close(dialog: DialogWarn?)
    }
}