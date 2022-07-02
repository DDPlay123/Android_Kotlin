package com.tutorial.taiwanhighspeedrailsystem.Activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.gson.Gson
import com.tutorial.taiwanhighspeedrailsystem.API.THSR
import com.tutorial.taiwanhighspeedrailsystem.Data.ObjectStation
import com.tutorial.taiwanhighspeedrailsystem.Data.StationData
import com.tutorial.taiwanhighspeedrailsystem.Dialog.DialogLoading
import com.tutorial.taiwanhighspeedrailsystem.Dialog.DialogStation
import com.tutorial.taiwanhighspeedrailsystem.Dialog.DialogStationSearch
import com.tutorial.taiwanhighspeedrailsystem.Dialog.DialogWarn2
import com.tutorial.taiwanhighspeedrailsystem.Function.MyLocation
import com.tutorial.taiwanhighspeedrailsystem.R
import okhttp3.*
import java.io.IOException
import java.util.*

class HomeActivity : AppCompatActivity(), OnMapReadyCallback {
    var dialogLoading = DialogLoading(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        // 要求權限
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
        // 取得元件
        findView()
        // 載入地圖
        dialogLoading.startLoading()
        loadMap()
        // 起點
        edStart.setOnClickListener {
            val dialog = DialogStation(this, data, edStart)
            dialog.show()
            // open keyboard
            dialog.window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM)
            dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
        }
        // 終點
        edEnd.setOnClickListener {
            val dialog = DialogStation(this, data, edEnd)
            dialog.show()
            // open keyboard
            dialog.window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM)
            dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
        }
        // 起點 <--> 終點
        btnSwap.setOnClickListener {
            val temp = edStart.text.toString()
            edStart.text = edEnd.text
            edEnd.text = temp
        }
        // 站點搜尋
        btnStationSearch.setOnClickListener {
            val dialog = DialogStationSearch(this, data, myMap)
            dialog.show()
            // open keyboard
            dialog.window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM)
            dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
        }
        // 路線規劃
        btnRouteSearch.setOnClickListener {
            if (edStart.text == "" || edEnd.text == "") {
                val dialog = DialogWarn2(this)
                dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialog.setMessage("請選擇站點!!!")
                    .positive(object : DialogWarn2.IOnConfirmListener{
                        override fun positive(dialog: DialogWarn2?) {
                            dialog?.dismiss()
                        }
                    })
                    .close(object : DialogWarn2.IOnCloseListener{
                        override fun close(dialog: DialogWarn2?) {
                            dialog?.dismiss()
                        }
                    })
                    .show()
            } else if (edStart.text == edEnd.text) {
                val dialog = DialogWarn2(this)
                dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialog.setMessage("站點重複!!!")
                    .positive(object : DialogWarn2.IOnConfirmListener{
                        override fun positive(dialog: DialogWarn2?) {
                            dialog?.dismiss()
                        }
                    })
                    .close(object : DialogWarn2.IOnCloseListener{
                        override fun close(dialog: DialogWarn2?) {
                            dialog?.dismiss()
                        }
                    })
                    .show()
            } else {
                val bundle = Bundle()
                bundle.putString("start", edStart.text.toString())
                bundle.putString("end", edEnd.text.toString())
                val intent = Intent(this, StationActivity::class.java)
                intent.putExtras(bundle)
                startActivity(intent)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onMapReady(p0: GoogleMap) = // 檢查是否授權定位權限
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // 精確定位包含粗略定位，因此只要求精確定位權限
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
        } else {
            // 取得資料
            getData()
            myMap = p0
            // 顯示目前位置與目前位置的按鈕
            myMap.isMyLocationEnabled = true
            // 關閉原生定位鈕
            myMap.uiSettings.isMyLocationButtonEnabled = false
            // 關閉原生按鈕
            myMap.uiSettings.isMapToolbarEnabled = false
            // 當前位置
            btnMyLocation.setOnClickListener {
                val myLat: Double = MyLocation(this).findLocation().latitude
                val myLng: Double = MyLocation(this).findLocation().longitude
                myMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(myLat, myLng), 15f))
            }
            // 初始化地圖中心點及size
            myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                LatLng(23.805857418790836, 120.9195094280048), 7f))
            // Menu
            myMap.setOnMarkerClickListener { marker ->
                if (!marker.isInfoWindowShown) {
                    val popupMenu = PopupMenu(this, btnMyLocation)
                    popupMenu.menuInflater.inflate(R.menu.menu, popupMenu.menu)
                    popupMenu.menu.getItem(0).title = marker.title
                    popupMenu.setOnMenuItemClickListener { item: MenuItem ->
                        when (item.itemId) {
                            R.id.StationName ->
                                myMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                                    LatLng(marker.position.latitude, marker.position.longitude), 20f))
                            R.id.SetOriginSite -> edStart.text = marker.title
                            R.id.SetEndSite -> edEnd.text = marker.title
                            R.id.NearRestaurant -> startActivity(Intent(this, RestaurantActivity::class.java))
                            R.id.Cancel -> popupMenu.dismiss()
                        }
                        true
                    }
                    popupMenu.show()
                }
                true
            }
        }
    private lateinit var edStart: TextView
    private lateinit var edEnd: TextView
    private lateinit var btnSwap: ImageButton
    private lateinit var btnMyLocation: ImageButton
    private lateinit var btnStationSearch: Button
    private lateinit var btnRouteSearch: Button
    private fun findView() {
        edStart = findViewById(R.id.ed_start_station)
        edEnd = findViewById(R.id.ed_end_station)
        btnSwap = findViewById(R.id.btn_swap)
        btnMyLocation = findViewById(R.id.btn_my_location)
        btnStationSearch = findViewById(R.id.btn_station_search)
        btnRouteSearch = findViewById(R.id.btn_route_search)
    }
    // 取得車站資料
    private val data: ArrayList<StationData> = ArrayList()
    @RequiresApi(api = Build.VERSION_CODES.O)
    private fun getData() {
        val request: Request = THSR().API("Station", null, null, null, null)
        // GET Method
        OkHttpClient().newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }
            override fun onResponse(call: Call, response: Response) {
                val json = response.body?.string()
                val details: Array<ObjectStation> = Gson().fromJson(json, Array<ObjectStation>::class.java)
                val polylineOptions = PolylineOptions()
                for (detail in details) {
                    data.add(
                        StationData(detail.StationName.Zh_tw + "高鐵站",
                                    detail.StationAddress,
                                    detail.StationPosition.PositionLat.toFloat(),
                                    detail.StationPosition.PositionLon.toFloat()))
                    runOnUiThread {
                        // adder marker
                        val markerOptions = MarkerOptions()
                            .position(LatLng(detail.StationPosition.PositionLat, detail.StationPosition.PositionLon))
                            .title(detail.StationName.Zh_tw + "高鐵站")
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_thsr))
                        myMap.addMarker(markerOptions)
                        // adder polyline
                        polylineOptions
                            .add(LatLng(detail.StationPosition.PositionLat, detail.StationPosition.PositionLon))
                            .color(Color.parseColor("#FF9E42"))
                        myMap.addPolyline(polylineOptions).width = 50f
                        dialogLoading.endLoading()
                    }
                }
            }
        })
    }
    // 載入地圖
    private lateinit var myMap: GoogleMap
    private fun loadMap() {
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
    }
    // 要求權限
    @RequiresApi(api = Build.VERSION_CODES.O)
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 0) {
            if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                finish()
            } else {
                loadMap()
            }
        }
    }
}