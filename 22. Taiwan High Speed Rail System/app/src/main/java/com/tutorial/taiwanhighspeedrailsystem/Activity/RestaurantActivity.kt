package com.tutorial.taiwanhighspeedrailsystem.Activity

import android.content.Intent
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.tutorial.taiwanhighspeedrailsystem.Adapter.RestaurantAdapter
import com.tutorial.taiwanhighspeedrailsystem.Data.ObjectRestaurant
import com.tutorial.taiwanhighspeedrailsystem.Data.RestaurantItem
import com.tutorial.taiwanhighspeedrailsystem.Dialog.DialogLoading
import com.tutorial.taiwanhighspeedrailsystem.R
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException

class RestaurantActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant)

        findView()
        dialogLoading.startLoading()

        val lat: Double = intent.getDoubleExtra("lat", 0.0)
        val lng: Double = intent.getDoubleExtra("lng", 0.0)

        getData(lat, lng)
    }

    private fun getData(lat: Double, lng: Double) {
        // URL
        val url = "https://api-dev.bluenet-ride.com/v2_0/lineBot/restaurant/get/"
        // Body
        val mediaType = "application/json; charset=utf-8".toMediaTypeOrNull()
        val param = "{\"lastIndex\":-1," +
                    "\"count\":15," +
                    "\"type\":[7]," +
                    "\"lat\":$lat," +
                    "\"lng\":$lng," +
                    "\"range\":\"2000\"," + // 2 km
                    "\"money\":0}"          // Any
        val body = param.toRequestBody(mediaType)
        val request: Request = Request.Builder()
            .url(url)
            .addHeader("Content-Type", "application/json")
            .post(body)
            .build()
        // Call OkHttp
        OkHttpClient().newCall(request).enqueue(object : Callback {
            // 失敗執行此方法
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }
            // 成功執行此方法
            override fun onResponse(call: Call, response: Response) {
                val json = response.body?.string()
                val details = Gson().fromJson(json, ObjectRestaurant::class.java)
                val data: ArrayList<RestaurantItem> = ArrayList()
                // 垂直顯示
                val linearLayoutManager = LinearLayoutManager(this@RestaurantActivity)
                linearLayoutManager.orientation = LinearLayoutManager.VERTICAL

                details.results.content.forEach {
                    try {
                        val distance = FloatArray(1)
                        Location.distanceBetween(
                            lat, lng,
                            it.lat, it.lng,
                            distance)
                        data.add(
                            RestaurantItem(
                            it.name, it.vicinity, it.lat, it.lng, (distance[0]/1000), it.rating, it.reviewsNumber, it.photo))
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                runOnUiThread {
                    recycleView.layoutManager = linearLayoutManager
                    val adapter = RestaurantAdapter(data)
                    recycleView.adapter = adapter
                    dialogLoading.endLoading()
                    adapter.setOnItemClickListener(object : RestaurantAdapter.onItemClickListener {
                        override fun onItemClick(position: Int) {
                            val intent = Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("http://maps.google.com/maps?daddr=" + data[position].lat + "," + data[position].lng))
                            startActivity(intent)
                        }
                    })
                }
            }
        })
    }

    private lateinit var recycleView: RecyclerView
    private lateinit var dialogLoading: DialogLoading

    private fun findView() {
        recycleView = findViewById(R.id.recycleView)
        dialogLoading = DialogLoading(this)
    }

    fun back(view: View) {
        super.onBackPressed()
    }
}