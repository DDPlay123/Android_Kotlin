package com.tutorial.taiwanhighspeedrailsystem.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.tutorial.taiwanhighspeedrailsystem.Data.StationData
import com.tutorial.taiwanhighspeedrailsystem.R

class ListAdapter(context: Context, data: ArrayList<StationData>, private val layout: Int) :
    ArrayAdapter<StationData>(context, layout, data) {

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // 依據傳入的 Layout 建立畫面
        val view = View.inflate(parent.context, layout, null)
        // 依據 position 取得對應的資料內容
        val item = getItem(position) ?: return view
        // 顯示項目
        val station = view.findViewById<TextView>(R.id.tv_station)
        val address = view.findViewById<TextView>(R.id.tv_address)

        station.text = item.station
        address.text = item.address
        // 回傳此項目的畫面
        return view
    }
}