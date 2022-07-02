package com.tutorial.taiwanhighspeedrailsystem.API

import android.util.Base64
import okhttp3.HttpUrl
import okhttp3.Request
import java.security.SignatureException
import java.text.SimpleDateFormat
import java.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

class THSR {
    private val ptx_app_id = "---------------------17bbafbcf16"
    private val ptx_app_key = "---------------------HX1SZb4"

    fun API(msg: String, OriginStationID: String ?= null,
                DestinationStationID: String ?= null,
                TrainDate: String ?= null,
                TrainNo: String ?= null): Request {
        val httpUrl: String = UrlBuild(msg, OriginStationID, DestinationStationID, TrainDate, TrainNo)
        return Request.Builder()
            .url(httpUrl)
            .addHeader("Authorization", authString())
            .addHeader("x-date", serverTime())
            .build()
    }
    // https://stackoverflow.com/questions/30142626/how-to-add-query-parameters-to-a-http-get-request-by-okhttp
    private fun UrlBuild(msg: String, OriginStationID: String ?= null,
                         DestinationStationID: String ?= null,
                         TrainDate: String ?= null,
                         TrainNo: String ?= null): String {
        val url = HttpUrl.Builder().apply {
            scheme("https")
            host("ptx.transportdata.tw")
            addPathSegment("MOTC")
            addPathSegment("v2")
            addPathSegment("Rail")
            addPathSegment("THSR")
        }
        val httpurl: HttpUrl = when(msg) {
            // ex: https://ptx.transportdata.tw/MOTC/v2/Rail/THSR/Station
            "Station" -> {
                url.addPathSegment("Station")
                url.addQueryParameter("format","JSON").build()
            }
            // ex: https://ptx.transportdata.tw/MOTC/v2/Rail/THSR/DailyTimetable/OD/0990/to/1000/2022-03-25
            "DailyTimetable" -> {
                url.addPathSegment("DailyTimetable")
                url.addPathSegment("OD")
                url.addPathSegment("$OriginStationID")
                url.addPathSegment("to")
                url.addPathSegment("$DestinationStationID")
                url.addPathSegment("$TrainDate")
                url.addQueryParameter("format","JSON").build()
            }
            // ex: https://ptx.transportdata.tw/MOTC/v2/Rail/THSR/DailyTimetable/Today/TrainNo/0333
            "TrainNumber" -> {
                url.addPathSegment("DailyTimetable")
                url.addPathSegment("Today")
                url.addPathSegment("TrainNo")
                url.addPathSegment("$TrainNo")
                url.addQueryParameter("format","JSON").build()
            }
            else -> {url.build()}
        }
        return httpurl.toString()
    }
    /*------------------------------------------------------------*/
    // 參考: https://ithelp.ithome.com.tw/articles/10206666
    // ptx api 有 HMAC機制
    // 以HMAC簽章驗證使用者的身份，用戶在請求API服務時，將APP Key
    // 與當下時間（格式請使用GMT時間）做HMAC-SHA1 運算後轉成Base64 格式，
    // 帶入signature屬性欄位，服務器端將驗證用戶請求時的header欄位(詳如第四點)，
    // 驗證使用者的身份及請求服務的時效性。
    // 需要加入HTTP Header設定
    /*------------------------------------------------------------*/
    // 金鑰
    private fun authString(): String{
        var signature = ""
        try {
            // get signature
            signature = signature("x-date: ${serverTime()}", ptx_app_key)!!
        } catch (e: SignatureException) {
            e.printStackTrace()
        }
        return  "hmac username=\"${ptx_app_id}\", " +
                "algorithm=\"hmac-sha1\", " +
                "headers=\"x-date\", " +
                "signature=\"$signature\""
    }
    // 當前時間
    private fun serverTime(): String{
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US)
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"))
        return dateFormat.format(calendar.time)
    }
    // 參考: https://ithelp.ithome.com.tw/articles/10208177
    // HMAC-SHA1 簽證機制
    private fun signature(xData: String, AppKey: String): String? {
        return try {
            // get an hmac_sha1 key from the raw key bytes
            val signingKey = SecretKeySpec(AppKey.toByteArray(charset("UTF-8")), "HmacSHA1")
            // get an hmac_sha1 Mac instance and initialize with the signing key
            val mac: Mac = Mac.getInstance("HmacSHA1")
            mac.init(signingKey)
            // compute the hmac on input data bytes
            val rawHmac: ByteArray = mac.doFinal(xData.toByteArray(charset("UTF-8")))
            Base64.encodeToString(rawHmac, Base64.NO_WRAP)
        } catch (e: Exception) {
            throw SignatureException("Failed to generate HMAC : " + e.message)
        }
    }
}