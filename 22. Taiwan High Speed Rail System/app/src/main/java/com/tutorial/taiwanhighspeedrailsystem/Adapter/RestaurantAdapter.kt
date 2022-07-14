package com.tutorial.taiwanhighspeedrailsystem.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.tutorial.taiwanhighspeedrailsystem.Data.RestaurantItem
import com.tutorial.taiwanhighspeedrailsystem.Data.TrainItem
import com.tutorial.taiwanhighspeedrailsystem.R
import java.lang.Exception

class RestaurantAdapter(private val data: ArrayList<RestaurantItem>):
    RecyclerView.Adapter<RestaurantAdapter.ViewHolder>(){
    private lateinit var mListener: onItemClickListener

    class ViewHolder(v: View, listener: onItemClickListener): RecyclerView.ViewHolder(v) {
        val imgPicture: ImageView = v.findViewById(R.id.img_picture)
        val progressBar: ProgressBar = v.findViewById(R.id.progressBar)
        val tvTitle: TextView = v.findViewById(R.id.tv_title)
        val tvAddress: TextView = v.findViewById(R.id.tv_address)
        val tvDistance: TextView = v.findViewById(R.id.tv_distance)
        val tvEvaluate: TextView = v.findViewById(R.id.tv_evaluate)

        init {
            v.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }

    override fun getItemCount() = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_restaurant, parent, false)
        return ViewHolder(v, mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Picasso.get()
            .load(data[position].photo)
            .into(holder.imgPicture, object : Callback {
                override fun onSuccess() {
                    holder.progressBar.visibility = View.INVISIBLE
                }
                override fun onError(e: Exception?) {
                    holder.progressBar.visibility = View.VISIBLE
                }
            })
        holder.tvTitle.text = data[position].name
        holder.tvAddress.text = data[position].vicinity
        holder.tvDistance.text = "距離高鐵站：%.2f公里".format(data[position].distance)
        holder.tvEvaluate.text = "評價：${data[position].rating}（${data[position].reviewsNumber}則評論）"
    }

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }
    fun setOnItemClickListener(Listener: onItemClickListener) {
        mListener = Listener
    }
}