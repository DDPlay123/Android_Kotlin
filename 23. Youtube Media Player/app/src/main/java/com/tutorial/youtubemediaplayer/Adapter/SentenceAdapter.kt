package com.tutorial.youtubemediaplayer.Adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tutorial.youtubemediaplayer.Data.Sentence
import com.tutorial.youtubemediaplayer.R

class SentenceAdapter(private val data: ArrayList<Sentence>):
    RecyclerView.Adapter<SentenceAdapter.ViewHolder>() {
    private lateinit var mListener: OnItemClickListener
    private var item: Int ?= null

    class ViewHolder(v: View, listener: OnItemClickListener): RecyclerView.ViewHolder(v) {
        val tvSentence: TextView = v.findViewById(R.id.tv_sentence)
        val tvPosition: TextView = v.findViewById(R.id.tv_position)
        val background: LinearLayout = v.findViewById(R.id.linearLayout)
        init {
            v.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }

    override fun getItemCount() = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_sentence, parent, false)
        return ViewHolder(v, mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvSentence.text = data[position].content
        holder.tvPosition.text = data[position].position.toString()
        if (position == item) holder.background.setBackgroundColor(Color.parseColor("#C0C0C0"))
        else holder.background.setBackgroundColor(Color.parseColor("#FFFFFF"))
    }

    fun tagItem(position: Int) {
        item = position
    }

    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(Listener: OnItemClickListener) {
        mListener = Listener
    }
}