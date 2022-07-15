package com.tutorial.taiwanhighspeedrailsystem.Activity

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.tutorial.taiwanhighspeedrailsystem.API.THSR
import com.tutorial.taiwanhighspeedrailsystem.Adapter.TrainNoAdapter
import com.tutorial.taiwanhighspeedrailsystem.Data.ObjectTrainNumber
import com.tutorial.taiwanhighspeedrailsystem.Data.TrainNoItem
import com.tutorial.taiwanhighspeedrailsystem.Dialog.DialogLoading
import com.tutorial.taiwanhighspeedrailsystem.R
import okhttp3.*
import java.io.IOException
import java.util.*

class TimeTableActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_time_table)

        findView()

        dialogLoading.startLoading()
        val trainNo = intent.getStringExtra("trainNo")
        val start = intent.getStringExtra("start")
        val end = intent.getStringExtra("end")
        tvTimeTable.text = trainNo + "時刻表"
        getData(trainNo!!, start!!, end!!)
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private fun getData(trainNo: String, start: String, end: String) {
        val request: Request = THSR.API("TrainNumber", null, null, null, trainNo)
        // GET Method
        OkHttpClient().newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }
            @SuppressLint("NotifyDataSetChanged")
            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                val json = response.body?.string()
                val details = Gson().fromJson(json, Array<ObjectTrainNumber>::class.java)
                val data: ArrayList<TrainNoItem> = ArrayList()
                // 垂直顯示
                val linearLayoutManager = LinearLayoutManager(this@TimeTableActivity)
                linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
                details.forEach { detail ->
                    detail.StopTimes.forEach{ stopTimes ->
                        data.add(
                            TrainNoItem(
                                stopTimes.StopSequence,
                                stopTimes.StationName.Zh_tw,
                                stopTimes.DepartureTime))
                    }
                }
                runOnUiThread {
                    recycleView.layoutManager = linearLayoutManager
                    val adapter = TrainNoAdapter(data, start, end)
                    recycleView.adapter = adapter
                    adapter.notifyDataSetChanged()
                    dialogLoading.endLoading()
                }
            }
        })
    }

    private lateinit var tvTimeTable: TextView
    private lateinit var recycleView: RecyclerView
    private lateinit var dialogLoading: DialogLoading
    private fun findView() {
        tvTimeTable = findViewById(R.id.tv_time_table)
        recycleView = findViewById(R.id.recycleView)
        dialogLoading = DialogLoading(this)
    }

    fun back(view: View) {
        super.onBackPressed()
    }
}