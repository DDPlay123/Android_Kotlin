package com.tutorial.attractionssearch.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ProgressBar
import com.tutorial.attractionssearch.Data.GalleryData
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.tutorial.attractionssearch.R
import java.lang.Exception

class GalleryAdapter(context: Context, data: ArrayList<GalleryData>, private val layout: Int)
    : ArrayAdapter<GalleryData>(context, layout, data) {
    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        //依據傳入的 Layout 建立畫面
        val view = View.inflate(parent.context, layout, null)
        //依據 position 取得對應的資料內容
        val item = getItem(position) ?: return view
        //將圖片指派給 ImageView 呈現
        val imgLandscape = view.findViewById<ImageView>(R.id.img_photo)
        val progressBar = view.findViewById<ProgressBar>(R.id.progressBar)
        Picasso.get().load(item.photo).into(imgLandscape, object : Callback{
            override fun onSuccess() {
                progressBar.visibility = View.INVISIBLE
            }

            override fun onError(e: Exception?) {
                progressBar.visibility = View.VISIBLE
            }

        })
        //回傳此項目的畫面
        return view
    }
}