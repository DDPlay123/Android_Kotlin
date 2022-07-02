package com.tutorial.taiwanhighspeedrailsystem.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tutorial.taiwanhighspeedrailsystem.Data.TrainItem
import com.tutorial.taiwanhighspeedrailsystem.R

class TrainAdapter(private val data: ArrayList<TrainItem>):
    RecyclerView.Adapter<TrainAdapter.ViewHolder>(){
    private lateinit var mListener: onItemClickListener

    class ViewHolder(v: View, listener: onItemClickListener): RecyclerView.ViewHolder(v) {
        val tvTrainDirection: TextView = v.findViewById(R.id.tv_train_direction)
        val tvTrainNumber: TextView = v.findViewById(R.id.tv_train_number)
        val tvStartTime: TextView = v.findViewById(R.id.tv_start_time)
        val tvDuration: TextView = v.findViewById(R.id.tv_duration)
        val tvArrivalTime: TextView = v.findViewById(R.id.tv_arrival_time)

        init {
            v.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }

    override fun getItemCount() = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_station, parent, false)
        return ViewHolder(v, mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvTrainDirection.text = data[position].Direction
        holder.tvTrainNumber.text = data[position].TrainNo
        holder.tvStartTime.text = data[position].DepartureTime
        holder.tvDuration.text = data[position].Duration
        holder.tvArrivalTime.text = data[position].ArrivalTime
    }

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }
    fun setOnItemClickListener(Listener: onItemClickListener) {
        mListener = Listener
    }
}