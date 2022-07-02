package com.tutorial.shoppinglist

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class Adapter(context: Context, data: ArrayList<Items>, private val layout: Int): ArrayAdapter<Items>(context, layout, data) {
    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = View.inflate(parent.context, layout, null)
        val item = getItem(position) ?: return view
        val imgPhoto = view.findViewById<ImageView>(R.id.img_photo)
        imgPhoto.setImageResource(item.photo)
        val tvMsg = view.findViewById<TextView>(R.id.tv_msg)
        tvMsg.text = if (layout == R.layout.adapter_vertical) item.name
        else "${item.name}: ${item.price}元"
        return view
    }
}