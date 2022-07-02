package com.tutorial.youtubemediaplayer.Data

import java.io.Serializable

data class Sentence(val content: String, val time: Int, val position: Int,
                    val videourl: String, val mainEditor: String): Serializable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Sentence

        if (content != other.content) return false
        if (time != other.time) return false
        if (position != other.position) return false
        if (videourl != other.videourl) return false
        if (mainEditor != other.mainEditor) return false

        return true
    }

    override fun hashCode(): Int {
        var result = content.hashCode()
        result = 31 * result + time
        result = 31 * result + position
        result = 31 * result + videourl.hashCode()
        result = 31 * result + mainEditor.hashCode()
        return result
    }
}