package com.tutorial.taiwanhighspeedrailsystem.API

import android.util.Base64
import android.util.Log
import com.google.gson.Gson
import com.tutorial.taiwanhighspeedrailsystem.Data.ObjectToken
import okhttp3.*
import java.io.IOException
import java.security.SignatureException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.CountDownLatch
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

class THSR {
    private var accessToken: String = ""

    private var beforeDate: String = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault()).format(Date())
    private lateinit var afterDate: String
    private var isNextDay = true

    private lateinit var httpUrl: String

    fun API(msg: String,
            OriginStationID: String ?= null,
            DestinationStationID: String ?= null,
            TrainDate: String ?= null,
            TrainNo: String ?= null): Request {

        httpUrl = urlBuild(msg, OriginStationID, DestinationStationID, TrainDate, TrainNo)

        checkNextDay()

        if (accessToken == "" || isNextDay) accessToken = postToken()

        Log.d("Token Test", accessToken)

        return Request.Builder()
            .url(httpUrl)
            .addHeader("authorization", "Bearer $accessToken")
            .build()
    }

    private fun urlBuild(msg: String,
                         OriginStationID: String ?= null,
                         DestinationStationID: String ?= null,
                         TrainDate: String ?= null,
                         TrainNo: String ?= null): String{
        val url = HttpUrl.Builder().apply {
            scheme("https")
            host("tdx.transportdata.tw")
            addPathSegment("api")
            addPathSegment("basic")
            addPathSegment("v2")
            addPathSegment("Rail")
            addPathSegment("THSR")
        }
        val httpUrl: HttpUrl = when(msg) {
            // ex: https://tdx.transportdata.tw/api/basic/v2/Rail/THSR/Station
            "Station" -> {
                url.addPathSegment("Station")
                url.addQueryParameter("format","JSON").build()
            }
            // ex: https://tdx.transportdata.tw/api/basic/v2/Rail/THSR/DailyTimetable/OD/0990/to/1000/2022-03-25
            "DailyTimetable" -> {
                url.addPathSegment("DailyTimetable")
                url.addPathSegment("OD")
                url.addPathSegment("$OriginStationID")
                url.addPathSegment("to")
                url.addPathSegment("$DestinationStationID")
                url.addPathSegment("$TrainDate")
                url.addQueryParameter("format","JSON").build()
            }
            // ex: https://tdx.transportdata.tw/api/basic/v2/Rail/THSR/DailyTimetable/Today/TrainNo/0333
            "TrainNumber" -> {
                url.addPathSegment("DailyTimetable")
                url.addPathSegment("Today")
                url.addPathSegment("TrainNo")
                url.addPathSegment("$TrainNo")
                url.addQueryParameter("format","JSON").build()
            }
            else -> {url.build()}
        }
        return httpUrl.toString()
    }

    // 取得TOKEN
    private val grantType = "client_credentials" // 固定使用"client_credentials"
    private val clientId = "-----------------5c-4198" // Client Id
    private val clientSecret = "----------------e3-6f6bc643bcf0" // Client Secret

    // POST Token
    private fun postToken(): String {
        lateinit var token: String
        // URL
        val url: String = HttpUrl.Builder()
            .scheme("https")
            .host("tdx.transportdata.tw")
            .addPathSegment("auth")
            .addPathSegment("realms")
            .addPathSegment("TDXConnect")
            .addPathSegment("protocol")
            .addPathSegment("openid-connect")
            .addPathSegment("token").toString()
        // Body
        val formBody = FormBody.Builder()
            .add("grant_type", grantType)
            .add("client_id", clientId)
            .add("client_secret", clientSecret)
            .build()
        // Request
        val request = Request.Builder()
            .url(url)
            .addHeader("Content-Type", "application/x-www-form-urlencoded")
            .post(formBody)
            .build()
        val countDownLatch = CountDownLatch(1)
        // POST
        OkHttpClient().newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                countDownLatch.countDown()
            }
            override fun onResponse(call: Call, response: Response) {
                val json = response.body?.string()
                val result = Gson().fromJson(json, ObjectToken::class.java)
                token = result.access_token
                countDownLatch.countDown()
            }
        })
        countDownLatch.await()
        return token
    }

    private fun checkNextDay() {
        afterDate = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault()).format(Date())
        if (afterDate > beforeDate) {
            isNextDay = true
            beforeDate = afterDate
        } else isNextDay = false
    }
}