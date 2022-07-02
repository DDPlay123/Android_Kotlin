package com.tutorial.taiwanhighspeedrailsystem.Function

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@SuppressLint("SimpleDateFormat")
class GetTime {
    // 當前時間
    fun getToday(): String? {
        val date = Date()
        val format = SimpleDateFormat("yyyy-MM-dd")
        return format.format(date)
    }

    // 比較時間
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Throws(ParseException::class)
    fun compareTime(time: String?): Boolean {
        // 當前時間
        val localDateTime = LocalDateTime.now()
        val localTime = localDateTime.format(DateTimeFormatter.ofPattern("HH:mm"))
        // 轉換格式
        val format = SimpleDateFormat("HH:mm")
        val startTime = format.parse(localTime)
        val endTime = format.parse(time.toString())!!
        assert(startTime != null)
        val diff = endTime.time - startTime!!.time
        return diff > 0
    }

    // 取時間差
    @Throws(ParseException::class)
    fun diffTime(start: String?, end: String?): String {
        val format = SimpleDateFormat("HH:mm")
        val startTime = format.parse(start.toString())
        val endTime = format.parse(end.toString())
        assert(startTime != null)
        val diff = endTime!!.time - startTime!!.time
        val days = diff / (1000 * 60 * 60 * 24)
        val hours = (diff - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60)
        val minutes = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60)
        var time = ""
        if (hours > 0 && minutes > 0) time =
            hours.toString() + "小時" + minutes + "分鐘" else if (hours > 0) time =
            hours.toString() + "小時" else if (minutes > 0) time = minutes.toString() + "分鐘"
        return time
    }
}