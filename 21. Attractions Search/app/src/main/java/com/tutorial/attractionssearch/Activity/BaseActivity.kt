package com.tutorial.attractionssearch.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.tutorial.attractionssearch.Adapter.SliderAdapter
import com.tutorial.attractionssearch.Data.GalleryData
import com.tutorial.attractionssearch.R
import java.util.ArrayList

class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)

        val viewPager = findViewById<ViewPager>(R.id.viewPager)

        val gallery: ArrayList<GalleryData> = intent.getSerializableExtra("Gallery") as ArrayList<GalleryData>
        val count = intent.getIntExtra("Count", 0)
        val position = intent.getIntExtra("Position", 0)
        countVP = count

        val adapter: PagerAdapter = SliderAdapter(this, gallery)
        setToolbar((position + 1).toString() + "/" + count)
        viewPager.adapter = adapter
        // 起始位置
        // 起始位置
        viewPager.currentItem = position
        // 監聽畫面滑動，以變換 actionbar
        // 監聽畫面滑動，以變換 actionbar
        viewPager.addOnPageChangeListener(listener)
    }

    // 監聽 ViewPager 頁面切換
    private var countVP = 0
    private var listener: ViewPager.OnPageChangeListener = object : ViewPager.OnPageChangeListener {
        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
            // 頁面滾動時觸發
            setToolbar((position + 1).toString() + "/" + countVP)
        }

        override fun onPageSelected(position: Int) {
            // 當新的頁面被選擇時觸發
        }

        override fun onPageScrollStateChanged(state: Int) {
            // 頁面滑動狀態改變時觸發
        }
    }

    private fun setToolbar(title: String) {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        val tvTitle = toolbar.findViewById<TextView>(R.id.toolbar_title)
        toolbar.title = ""
        setSupportActionBar(toolbar)
        tvTitle.text = title
        supportActionBar!!.setDisplayShowCustomEnabled(false)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}