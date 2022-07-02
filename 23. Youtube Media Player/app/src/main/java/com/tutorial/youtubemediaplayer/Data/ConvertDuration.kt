package com.tutorial.youtubemediaplayer.Data

data class ConvertDuration(val min: String, val sec: String) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ConvertDuration

        if (min != other.min) return false
        if (sec != other.sec) return false

        return true
    }

    override fun hashCode(): Int {
        var result = min.hashCode()
        result = 31 * result + sec.hashCode()
        return result
    }
}