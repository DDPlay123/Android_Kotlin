package com.tutorial.mapapp

import android.content.pm.PackageManager
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions

class MainActivity : AppCompatActivity(), OnMapReadyCallback {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // 連接 SupportMapFragment 元件並載入地圖
        val map = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        map.getMapAsync(this)
    }

    override fun onMapReady(p0: GoogleMap) {
        // 檢查是否授權定位權限
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_DENIED &&
            ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_DENIED
        ){
            // 精確定位包含粗略定位，因此只要求精確定位權限
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 1)
        }else {
            // 顯示目前位置與目前位置的按鈕
            p0.isMyLocationEnabled = true
            // 加入標記
            val marker = MarkerOptions()
            marker.position(LatLng(25.033611, 121.565000))
            marker.title("台北 101")
            marker.draggable(true)
            p0.addMarker(marker)
            marker.position(LatLng(25.047924, 121.517081))
            marker.title("台北車站")
            marker.draggable(true)
            p0.addMarker(marker)
            // 繪製線段
            val polylineOpt = PolylineOptions()
            polylineOpt.add(LatLng(25.033611, 121.565000))
            polylineOpt.add(LatLng(25.032435, 121.534905))
            polylineOpt.add(LatLng(25.047924, 121.517081))
            polylineOpt.color(Color.RED)
            val polyline = p0.addPolyline(polylineOpt)
            polyline.width = 20f
            // 初始化地圖中心點及size
            p0.moveCamera(
                CameraUpdateFactory.newLatLngZoom(
                    LatLng(25.035, 121.54), 13f
                )
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.isNotEmpty() && requestCode == 1){
            if (grantResults[0] == PackageManager.PERMISSION_DENIED) finish()
            else{
                val map = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
                map.getMapAsync(this)
            }
        }
    }
}