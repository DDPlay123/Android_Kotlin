package com.tutorial.attractionssearch.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.viewpager.widget.PagerAdapter
import com.tutorial.attractionssearch.Data.GalleryData
import com.tutorial.attractionssearch.R
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception

class SliderAdapter(private var context: Context, image: ArrayList<GalleryData>): PagerAdapter() {
    // URL圖片
    private var images: ArrayList<GalleryData> = image
    private lateinit var inflater: LayoutInflater
    // 回傳 ViewPager 的子項目個數
    override fun getCount(): Int {
        return images.size
    }
    // 用來判斷頁面的 View 和下面 instantiateItem 方法回傳的物件是否一樣
    override fun isViewFromObject(view: View, `object`: Any): Boolean = view == `object` as RelativeLayout
    // 實例化 ViewPager 中相對應位置的頁面
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        // 創建頁面
        inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = inflater.inflate(R.layout.slider_image, container, false)
        val image: ImageView = view.findViewById(R.id.img_photo)
        val progressBar: ProgressBar = view.findViewById(R.id.progressBar)
        // 顯示圖片
        Picasso.get().load(images[position].photo).into(image, object : Callback{
            override fun onSuccess() {
                progressBar.visibility = View.INVISIBLE
            }

            override fun onError(e: Exception?) {
                progressBar.visibility = View.VISIBLE
            }

        })
        // 將view加進container
        container.addView(view)
        return view
    }
    // 當子項目 View 被滑出預備範圍內（當前及前後各一個頁面）時會被移除
    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as RelativeLayout)
    }
}