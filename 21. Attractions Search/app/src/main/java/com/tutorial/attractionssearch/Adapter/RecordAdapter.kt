package com.tutorial.attractionssearch.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.tutorial.attractionssearch.Data.RecordData
import com.tutorial.attractionssearch.R

class RecordAdapter(context: Context, data: ArrayList<RecordData>, private val layout: Int)
    : ArrayAdapter<RecordData>(context, layout, data) {
    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        //依據傳入的 Layout 建立畫面
        val view = View.inflate(parent.context, layout, null)
        //依據 position 取得對應的資料內容
        val item = getItem(position) ?: return view
        // 指定文字內容
        val tvName: TextView = view.findViewById(R.id.tv_name)
        val tvVicinity: TextView = view.findViewById(R.id.tv_vicinity)
        tvName.text = item.name
        tvVicinity.text = item.vicinity

        //回傳此項目的畫面
        return view
    }
}