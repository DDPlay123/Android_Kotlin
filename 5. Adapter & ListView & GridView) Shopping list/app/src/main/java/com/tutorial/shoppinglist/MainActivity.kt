package com.tutorial.shoppinglist

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.GridView
import android.widget.ListView
import android.widget.Spinner

class MainActivity : AppCompatActivity() {
    @SuppressLint("Recycle")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val spinner = findViewById<Spinner>(R.id.spinner)
        val gridView = findViewById<GridView>(R.id.gridView)
        val listView = findViewById<ListView>(R.id.listView)

        val count = ArrayList<String>()      //儲存購買數量資訊
        val item = ArrayList<Items>()        //儲存水果資訊
        val priceRange = IntRange(10, 100)   //建立價格範圍

        val array = resources.obtainTypedArray(R.array.image_list) //從 R 類別讀取圖檔

        for(i in 0 until array.length()) {
            val photo = array.getResourceId(i,0) //水果圖片 Id
            val name = "水果${i+1}" //水果名稱
            val price = priceRange.random() //亂數產生價格
            count.add("${i+1}個")   //新增可購買數量資訊
            item.add(Items(photo, name, price)) //新增水果資訊
        }
        array.recycle() //釋放圖檔資源

        //建立 ArrayAdapter 物件，並傳入字串與 simple_list_item_1.xml
        spinner.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, count)
        //設定橫向顯示列數
        gridView.numColumns = 3
        //建立 MyAdapter 物件，並傳入 adapter_vertical 作為畫面
        gridView.adapter = Adapter(this, item, R.layout.adapter_vertical)
        //建立 MyAdapter 物件，並傳入 adapter_horizontal 作為畫面
        listView.adapter = Adapter(this, item, R.layout.adapter_horizontal)
    }
}