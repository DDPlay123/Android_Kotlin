package com.tutorial.youtubemediaplayer.Data

import java.io.Serializable

data class VideoDetail(val thumbnails: String, val duration: ConvertDuration,
                       val title: String, val viewer: Int): Serializable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as VideoDetail

        if (thumbnails != other.thumbnails) return false
        if (duration != other.duration) return false
        if (title != other.title) return false
        if (viewer != other.viewer) return false

        return true
    }

    override fun hashCode(): Int {
        var result = thumbnails.hashCode()
        result = 31 * result + duration.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + viewer
        return result
    }
}