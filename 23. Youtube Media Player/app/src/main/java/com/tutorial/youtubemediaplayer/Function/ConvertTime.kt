package com.tutorial.youtubemediaplayer.Function

import com.tutorial.youtubemediaplayer.Data.ConvertDuration

class ConvertTime {
    // Converter duration to Time ex: 00:00
    fun convertTime(duration: Int): ConvertDuration {
        val minutes = duration / 60
        val seconds = duration % 60
        val min: String = if (minutes == 0) "00" else minutes.toString()
        val sec: String = if (seconds == 0) "00" else seconds.toString()
        return ConvertDuration(min, sec)
    }
}