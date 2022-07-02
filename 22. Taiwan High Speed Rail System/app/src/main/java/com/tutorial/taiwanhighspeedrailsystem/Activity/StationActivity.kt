package com.tutorial.taiwanhighspeedrailsystem.Activity

import android.annotation.SuppressLint
import android.content.Intent
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
import com.tutorial.taiwanhighspeedrailsystem.Adapter.TrainAdapter
import com.tutorial.taiwanhighspeedrailsystem.Data.ObjectDailyTimetable
import com.tutorial.taiwanhighspeedrailsystem.Data.TrainItem
import com.tutorial.taiwanhighspeedrailsystem.Dialog.DialogLoading
import com.tutorial.taiwanhighspeedrailsystem.Function.GetTime
import com.tutorial.taiwanhighspeedrailsystem.Function.StationID
import com.tutorial.taiwanhighspeedrailsystem.R
import okhttp3.*
import java.io.IOException
import java.text.ParseException
import java.util.*

class StationActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_station)

        findView()

        dialogLoading.startLoading()
        val start = intent.getStringExtra("start")
        val end = intent.getStringExtra("end")
        tvStartStation.text = start
        tvEndStation.text = end
        val startId = StationID().dict(start)
        val endId = StationID().dict(end)
        val time = GetTime().getToday()

        getData(startId!!, endId!!, time!!, start!!, end!!)
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private fun getData(
        OriginStationID: String, DestinationStationID: String,
        TrainDate: String, Start: String, End: String
    ) {
        val request: Request = THSR().API("DailyTimetable", OriginStationID, DestinationStationID, TrainDate, null)
        // GET Method
        OkHttpClient().newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(call: Call, response: Response) {
                val json = response.body?.string()
                val details: Array<ObjectDailyTimetable> = Gson().fromJson(json, Array<ObjectDailyTimetable>::class.java)
                val data: ArrayList<TrainItem> = ArrayList()
                // 垂直顯示
                val linearLayoutManager = LinearLayoutManager(this@StationActivity)
                linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
                for (detail in details) {
                    try {
                        if (GetTime().compareTime(detail.OriginStopTime.DepartureTime)) {
                            data.add(
                                TrainItem( // 車次
                                    detail.DailyTrainInfo.TrainNo,  // 方向
                                    StationID().dict(detail.DailyTrainInfo.Direction),  // 出發時間
                                    detail.OriginStopTime.DepartureTime,  // 出發時間
                                    GetTime().diffTime(
                                        detail.OriginStopTime.DepartureTime,
                                        detail.DestinationStopTime.ArrivalTime
                                    ),  // 到站時間
                                    detail.DestinationStopTime.ArrivalTime))
                        }
                    } catch (e: ParseException) {
                        e.printStackTrace()
                    }
                    runOnUiThread {
                        recycleView.layoutManager = linearLayoutManager
                        val adapter = TrainAdapter(data)
                        recycleView.adapter = adapter
                        adapter.notifyDataSetChanged()
                        dialogLoading.endLoading()
                        adapter.setOnItemClickListener(object : TrainAdapter.onItemClickListener{
                            override fun onItemClick(position: Int) {
                                val bundle = Bundle()
                                bundle.putString("trainNo", data[position].TrainNo)
                                bundle.putString("start", Start)
                                bundle.putString("end", End)
                                val intent = Intent(this@StationActivity, TimeTableActivity::class.java)
                                intent.putExtras(bundle)
                                startActivity(intent)
                            }
                        })
                    }
                }
            }
        })
    }

    private lateinit var tvStartStation: TextView
    private lateinit var tvEndStation: TextView
    private lateinit var recycleView: RecyclerView
    private lateinit var dialogLoading: DialogLoading
    private fun findView() {
        tvStartStation = findViewById(R.id.tv_start_station)
        tvEndStation = findViewById(R.id.tv_end_station)
        recycleView = findViewById(R.id.recycleView)
        dialogLoading = DialogLoading(this)
    }

    fun back(view: View) {
        super.onBackPressed()
    }
}