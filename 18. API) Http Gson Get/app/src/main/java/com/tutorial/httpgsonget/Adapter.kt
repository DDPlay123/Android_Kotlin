package com.tutorial.httpgsonget

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class Adapter(context: Context, data: ArrayList<Items>): ArrayAdapter<Items>(context, R.layout.list_item, data) {
    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = View.inflate(parent.context, R.layout.list_item, null)
        val item = getItem(position) ?: return view

        val tvMsg = view.findViewById<TextView>(R.id.tv_name)
        val tvPosition = view.findViewById<TextView>(R.id.tv_position)
        tvMsg.text = item.name
        tvPosition.text = "lat:${item.lat}\nlng:${item.lng}"

        return view
    }
}