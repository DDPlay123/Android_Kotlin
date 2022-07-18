package com.tutorial.attractionssearch.Activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.gson.Gson
import com.tutorial.attractionssearch.Adapter.RecordAdapter
import com.tutorial.attractionssearch.Adapter.SearchAdapter
import com.tutorial.attractionssearch.Data.DataModel
import com.tutorial.attractionssearch.Data.DetailData
import com.tutorial.attractionssearch.Data.RecordData
import com.tutorial.attractionssearch.Dialog.DialogInfo
import com.tutorial.attractionssearch.R
import com.tutorial.attractionssearch.SQLite.Database
import okhttp3.*
import java.io.IOException
import java.io.Serializable

class HomeActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var database: SQLiteDatabase
    private val httpURL: String = "https://android-quiz-29a4c.web.app/"
    private lateinit var edSearch: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        edSearch = findViewById(R.id.edit_search)
        val btnSearch = findViewById<Button>(R.id.btn_search)
        val btnRecord = findViewById<Button>(R.id.btn_record)
        val btnDelete = findViewById<ImageView>(R.id.btn_delete)

        // 取得資料庫
        database = Database(this).writableDatabase
        // 要求權限
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
        // API
        if (details.isEmpty()) getData()
        // 載入地圖
        loadMap()
        // 搜尋功能
        btnSearch.setOnClickListener {
            search()
            // 關閉鍵盤
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS)
        }
        edSearch.setOnEditorActionListener { _, id, _ ->
            if (id == EditorInfo.IME_ACTION_SEARCH) search()
            false
        }
        btnDelete.setOnClickListener {
            edSearch.setText("")
        }
        // 搜尋紀錄
        btnRecord.setOnClickListener {
            val data: ArrayList<RecordData> = ArrayList()
            @SuppressLint("Recycle") val cursor =
                database.rawQuery("SELECT name, vicinity FROM history", null)
            cursor.moveToFirst() // 從第一筆開始輸出
            for (i in cursor.count downTo 1) {
                data.add(RecordData(cursor.getString(0), cursor.getString(1)))
                cursor.moveToNext()
            }
            if (cursor.count == 0) {
                Toast.makeText(this, "無歷史紀錄", Toast.LENGTH_SHORT).show()
            } else {
                // 建立 AlertDialog
                val dialogRecord =
                    AlertDialog.Builder(this)
                val rowList = layoutInflater.inflate(R.layout.dialog_record, null)
                dialogRecord.setView(rowList)
                // 使用 dialog_record 的 ListView
                val listView = rowList.findViewById<ListView>(R.id.list_record)
                val adapter = RecordAdapter(this, data, R.layout.item_record)
                listView.adapter = adapter
                // 彈出 Dialog 畫面
                adapter.notifyDataSetChanged()
                val dialog = dialogRecord.create()
                dialog.window!!
                    .setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialog.show()
                // 如清空按鈕被點選，刪除所有資料
                rowList.findViewById<View>(R.id.btn_delete)
                    .setOnClickListener {
                        database.execSQL("DELETE FROM history")
                        Toast.makeText(this, "已清除紀錄", Toast.LENGTH_SHORT).show()
                        dialog.dismiss()
                    }
            }
        }
    }

    override fun onMapReady(p0: GoogleMap) {
        // 檢查是否授權定位權限
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
            PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) !=
            PackageManager.PERMISSION_GRANTED) {
            // 精確定位包含粗略定位，因此只要求精確定位權限
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                1
            )
        } else {
            myMap = p0
            // 顯示目前位置與目前位置的按鈕
            myMap.isMyLocationEnabled = true
            // 初始化地圖中心點及size
            myMap.moveCamera(
                CameraUpdateFactory.newLatLngZoom(
                    LatLng(23.805857418790836, 120.9195094280048), 7f
                )
            )
            // Marker 點選
            myMap.setOnMarkerClickListener { marker ->
                if (!marker.isInfoWindowShown) {
                    val dialog = DialogInfo(this)
                    dialog.setMessage(marker.title)
                        .setInfo(object : DialogInfo.IOBtnInfo {
                            override fun btnInfo(dialog: DialogInfo?) {
                                var number = 0
                                details.forEachIndexed{index, _ ->
                                    if (marker.title!!.contains(details[index].name)) number = index
                                }
                                val intent = Intent(this@HomeActivity, DetailActivity::class.java)
                                val bundle = Bundle()
                                bundle.putSerializable("Data", details as Serializable)
                                bundle.putInt("Number", number)
                                intent.putExtras(bundle)
                                startActivity(intent)
                            }
                        })
                        .setGps(object : DialogInfo.IOBtnGps {
                            override fun btnGps(dialog: DialogInfo?) {
                                val intent = Intent(
                                    Intent.ACTION_VIEW,
                                    Uri.parse("http://maps.google.com/maps?daddr=" + marker.position.latitude + "," + marker.position.longitude)
                                )
                                startActivity(intent)
                            }
                        })
                    dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                    dialog.show()
                }
                true
            }
        }
    }

    // 搜尋功能
    private fun search() {
        if (edSearch.length() < 1) {
            Toast.makeText(this, "查無結果", Toast.LENGTH_SHORT).show()
        } else {
            val data: ArrayList<DetailData> = ArrayList()
            details.forEach {
                if (it.name.contains(edSearch.text.toString()) ||
                    it.vicinity.contains(edSearch.text.toString())) {
                    data.add(it)
                }
            }
            if (data.isEmpty()) Toast.makeText(this, "查無結果", Toast.LENGTH_SHORT).show()
            else {
                // 建立 AlertDialog
                val dialogSearch = AlertDialog.Builder(this)
                val rowList = layoutInflater.inflate(R.layout.dialog_search, null)
                dialogSearch.setView(rowList)
                // 使用 dialog_search 的 ListView
                val listView = rowList.findViewById<ListView>(R.id.list_search)
                val adapter = SearchAdapter(this, data, R.layout.item_record)
                listView.adapter = adapter
                // 彈出 Dialog 畫面
                adapter.notifyDataSetChanged()
                val dialog = dialogSearch.create()
                dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialog.show()
                listView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
                        // 儲存該筆資料到Database
                        database.execSQL(
                            "INSERT INTO history(name, vicinity) VALUES(?,?)",
                            arrayOf(
                                data[position].name,
                                data[position].vicinity
                            )
                        )
                        myMap.animateCamera(
                            CameraUpdateFactory.newLatLngZoom(
                                LatLng(
                                    data[position].lat.toDouble(),
                                    data[position].lng.toDouble()
                                ), 13f
                            )
                        )
                        dialog.dismiss()
                    }
            }
        }
    }

    private var details: ArrayList<DetailData> = ArrayList()
    private fun getData() {
        val request: Request = Request.Builder()
            .url(httpURL)
            .build()
        OkHttpClient().newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                Log.e("ERROR", "Data ERROR")
            }

            override fun onResponse(call: Call, response: Response) {
                val json = response.body?.string()
                val dataModel = Gson().fromJson(json, DataModel::class.java)
                Log.d("DATA", json.toString())
                dataModel.results.content.forEach {
                    details.add(DetailData(it.name, it.lat.toFloat(), it.lng.toFloat(), it.vicinity, it.photo, it.star, it.landscape))
                }
                runOnUiThread {
                    details.forEachIndexed { i, _ ->
                        myMap.addMarker(
                            MarkerOptions()
                            .position(LatLng(details[i].lat.toDouble(), details[i].lng.toDouble()))
                            .title(details[i].name))
                    }
                }
            }
        })
    }

    private lateinit var myMap: GoogleMap
    private fun loadMap() {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    // 要求權限
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.isNotEmpty() && requestCode == 1){
            if (grantResults[0] == PackageManager.PERMISSION_DENIED) finish()
            else{
                loadMap()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        database.close() // 關閉資料庫
    }
}