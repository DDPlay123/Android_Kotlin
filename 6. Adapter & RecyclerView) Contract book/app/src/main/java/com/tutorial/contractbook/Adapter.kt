package com.example.ch7

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tutorial.contractbook.Items
import com.tutorial.contractbook.R

class Adapter(private val data: ArrayList<Items>):RecyclerView.Adapter<Adapter.ViewHolder>(){
    class ViewHolder(v: View): RecyclerView.ViewHolder(v){
        val tvName: TextView = v.findViewById(R.id.show_name)
        val tvPhone: TextView = v.findViewById(R.id.show_phone)
        val btnDelete: ImageView = v.findViewById(R.id.delete)
    }
    //回傳資料數量
    override fun getItemCount() = data.size
    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int):ViewHolder{
        val v = LayoutInflater.from(viewGroup.context).inflate(R.layout.adapter_row, viewGroup, false)
        return ViewHolder(v)
    }
    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ViewHolder, position: Int){
        holder.tvName.text = data[position].name
        holder.tvPhone.text = data[position].phone
        holder.btnDelete.setOnClickListener{
            data.removeAt(position)
            notifyDataSetChanged()
        }
    }
}