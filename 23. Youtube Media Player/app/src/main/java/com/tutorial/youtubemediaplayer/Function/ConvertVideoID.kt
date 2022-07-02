package com.tutorial.youtubemediaplayer.Function

class ConvertVideoID {
    fun convertVideoId(videoURL: String): String {
        val scheme = "https://"
        val host = "youtu.be/"
        var newUrl = videoURL.replace(scheme, "")
        newUrl = newUrl.replace(host, "")
        return newUrl
    }
}