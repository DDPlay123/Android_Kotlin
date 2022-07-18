package com.tutorial.youtubemediaplayer.API

import okhttp3.HttpUrl
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

class Video {
    // https://api.italkutalk.com/api/video/detail
    // headers => Content-Type: application/json
    // body => {guestKey:"*****", videoID:"*****", mode:0 or 1}
    // mode: 0->不增加總瀏覽數(於詳細頁面重刷時), 1->增加總瀏覽數(從列表進入時) 使用兩個都可
    private val guestKey = "44f6cfed-b251-4952-b6ab-34de1a599ae4"
    private val videoID = "5edfb3b04486bc1b20c2851a"

    fun postData(): Request {
        val url: String = HttpUrl.Builder()
            .scheme("https")
            .host("api.italkutalk.com")
            .addPathSegment("api")
            .addPathSegment("video")
            .addPathSegment("detail").toString()

        val mediaType = "application/json; charset=utf-8".toMediaTypeOrNull()

        val param = "{\"guestKey\":\"$guestKey\"," +
                    "\"videoID\":\"$videoID\"," +
                    "\"mode\":1}"

        val body = param.toRequestBody(mediaType)

        return Request.Builder()
            .url(url)
            .addHeader("Content-Type", "application/json")
            .post(body)
            .build()
    }
}