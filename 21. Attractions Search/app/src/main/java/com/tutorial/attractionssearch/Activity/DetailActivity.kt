package com.tutorial.attractionssearch.Activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.widget.Toolbar
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.tutorial.attractionssearch.Adapter.GalleryAdapter
import com.tutorial.attractionssearch.Data.DetailData
import com.tutorial.attractionssearch.Data.GalleryData
import com.tutorial.attractionssearch.R
import java.io.Serializable

class DetailActivity : AppCompatActivity() {
    // 定義 5星陣列
    private val img = intArrayOf(R.drawable.icon_star, R.drawable.icon_star, R.drawable.icon_star, R.drawable.icon_star, R.drawable.icon_star)

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setToolbar()
        // 讀取資料
        val data: ArrayList<DetailData> = intent.getSerializableExtra("Data") as ArrayList<DetailData>
        val index: Int = intent.getIntExtra("Number", 0)
        // 定義元件
        val imgPhoto = findViewById<ImageView>(R.id.img_photo)
        val tvName = findViewById<TextView>(R.id.tv_name)
        val tvAddress = findViewById<TextView>(R.id.tv_address)
        val gridStar = findViewById<GridView>(R.id.grid_star)
        val tvPhotos = findViewById<TextView>(R.id.tv_photos)
        val gridPhotos = findViewById<GridView>(R.id.grid_photos)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        // 大圖
        Picasso.get().load(data[index].photo).into(imgPhoto, object : Callback {
            override fun onSuccess() {
                progressBar.visibility = View.INVISIBLE
            }

            override fun onError(e: Exception?) {
                progressBar.visibility = View.VISIBLE
            }

        })
        // 名稱
        tvName.text = data[index].name
        // 地址
        tvAddress.text = data[index].vicinity
        // 星級
        val items = ArrayList<Map<String, Any>>()
        for (i in 0 until data[index].star){
            val item = HashMap<String, Any>()
            item["image"] = img[i]
            items.add(item)
        }
        // 定義 SimpleAdapter
        val adapter = SimpleAdapter(this, items, R.layout.grid_star, arrayOf("image"), intArrayOf(R.id.img_star))
        // 最多顯示5顆星
        gridStar.numColumns = 5
        gridStar.adapter = adapter
        // 總圖片數
        tvPhotos.text = "景觀圖(${data[index].landscape.size})"
        //圖片集
        val gallery: ArrayList<GalleryData> = ArrayList()
        data[index].landscape.forEachIndexed{i, _ ->
            val photo: String = data[index].landscape[i]
            gallery.add(GalleryData(photo))
        }
        gridPhotos.numColumns = 3
        gridPhotos.adapter = GalleryAdapter(this, gallery, R.layout.grid_gallery)
        gridPhotos.setOnItemClickListener{_, _, position, _ ->
            val intent = Intent(this@DetailActivity, BaseActivity::class.java)
            val bundle = Bundle()
            bundle.putSerializable("Gallery", gallery as Serializable)
            bundle.putInt("Count", data[index].landscape.size)
            bundle.putInt("Position", position)
            intent.putExtras(bundle)
            startActivity(intent)
        }
    }

    private fun setToolbar() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        val tvTitle = toolbar.findViewById<TextView>(R.id.toolbar_title)
        toolbar.title = ""
        setSupportActionBar(toolbar)
        tvTitle.text = "詳細資料"
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