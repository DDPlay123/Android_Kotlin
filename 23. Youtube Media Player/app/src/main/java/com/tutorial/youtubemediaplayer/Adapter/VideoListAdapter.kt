package com.tutorial.youtubemediaplayer.Adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.tutorial.youtubemediaplayer.Data.VideoDetail
import com.tutorial.youtubemediaplayer.R
import java.lang.Exception

class VideoListAdapter(private val data: ArrayList<VideoDetail>):
    RecyclerView.Adapter<VideoListAdapter.ViewHolder>() {
    private lateinit var mListener: OnItemClickListener

    class ViewHolder(v: View, listener: OnItemClickListener): RecyclerView.ViewHolder(v) {
        val imgVideo = v.findViewById<ImageView>(R.id.img_video)!!
        val tvTime = v.findViewById<TextView>(R.id.tv_time)!!
        val tvLevel = v.findViewById<TextView>(R.id.tv_level)!!
        val tvTitle = v.findViewById<TextView>(R.id.tv_title)!!
        val tvFavorite = v.findViewById<TextView>(R.id.tv_favorite)!!
        val tvViewer = v.findViewById<TextView>(R.id.tv_viewer)!!
        val progressBar = v.findViewById<ProgressBar>(R.id.progressBar)!!

        init {
            v.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }

    override fun getItemCount() = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_video, parent, false)
        return ViewHolder(v, mListener)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Picasso.get()
            .load(data[position].thumbnails)
            .into(holder.imgVideo, object : Callback {
                override fun onSuccess() {
                    holder.progressBar.visibility = View.INVISIBLE
                }

                override fun onError(e: Exception?) {
                    holder.progressBar.visibility = View.VISIBLE
                }
            })
        holder.tvTime.text = data[position].duration.min + ":" + data[position].duration.sec
        holder.tvLevel.text = "初級"
        holder.tvTitle.text = data[position].title
        holder.tvFavorite.text = "0"
        holder.tvViewer.text = data[position].viewer.toString()
    }

    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }
    fun setOnItemClickListener(Listener: OnItemClickListener) {
        mListener = Listener
    }
}