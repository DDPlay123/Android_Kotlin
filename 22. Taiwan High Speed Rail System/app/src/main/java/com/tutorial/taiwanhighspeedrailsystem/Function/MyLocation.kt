package com.tutorial.taiwanhighspeedrailsystem.Function

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.model.LatLng

class MyLocation(private val context: Context) {
    private var latitude = 0.0f
    private var longitude = 0.0f

    // 尋找經緯度
    @SuppressLint("MissingPermission")
    fun findLocation(): LatLng {
        // defGPS = LocationManager.GPS_PROVIDER; //GPS定位
        val defGPS: String = LocationManager.NETWORK_PROVIDER //網路定位
        // LocationManager可以用來獲取當前的位置，追蹤設備的移動路線
        val locationManager = context.getSystemService(AppCompatActivity.LOCATION_SERVICE) as LocationManager
        locationManager.requestLocationUpdates(defGPS, 1000, 0f, locationListener)
        val location = locationManager.getLastKnownLocation(defGPS)
        if (location != null) {
            latitude = location.latitude.toFloat()
            longitude = location.longitude.toFloat()
        }
        return LatLng(latitude.toDouble(), longitude.toDouble())
    }

    // location監聽
    private var locationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            latitude = location.latitude.toFloat()
            longitude = location.longitude.toFloat()
        }

        // Provider的轉態在可用、暫時不可用和無服務三個狀態直接切換時觸發此函數
        @Deprecated("Deprecated in Java")
        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}

        // Provider被enable時觸發此函數，比如GPS被打開
        override fun onProviderEnabled(provider: String) {}

        // Provider被disable時觸發此函數，比如GPS被關閉
        override fun onProviderDisabled(provider: String) {}
    }
}