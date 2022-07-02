package com.tutorial.taiwanhighspeedrailsystem.Adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tutorial.taiwanhighspeedrailsystem.Data.TrainNoItem
import com.tutorial.taiwanhighspeedrailsystem.R

class TrainNoAdapter(private val data: ArrayList<TrainNoItem>,
                     private val start: String?, private val end: String?
):
    RecyclerView.Adapter<TrainNoAdapter.ViewHolder>(){

    class ViewHolder(v: View): RecyclerView.ViewHolder(v) {
        val tvStation: TextView = v.findViewById(R.id.tv_station)
        val tvStartTime: TextView = v.findViewById(R.id.tv_start_time)
        val linearLayout: LinearLayout = v.findViewById(R.id.linearLayout)
    }

    override fun getItemCount() = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_time_table, parent, false)
        return ViewHolder(v)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvStation.text = data[position].StopSequence + " ." + data[position].Zh_tw + "高鐵站"
        holder.tvStartTime.text = data[position].DepartureTime
        if (start == data[position].Zh_tw + "高鐵站" ||
            end == data[position].Zh_tw + "高鐵站") {
            holder.linearLayout.setBackgroundColor(Color.parseColor("#EDCC39"))
        }
    }

}