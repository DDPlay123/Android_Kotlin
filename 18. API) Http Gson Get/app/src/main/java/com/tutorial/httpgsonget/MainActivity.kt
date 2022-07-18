package com.tutorial.httpgsonget

import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnSearch = findViewById<Button>(R.id.btn_search)
        val listView = findViewById<ListView>(R.id.listview)

        // GET Method
        btnSearch.setOnClickListener {
            // 1. 建立 Request.Builder 物件，藉由 url() 將網址傳入，再建立 Request 物件
            val req: Request = Request.Builder()
                .url("https://android-quiz-29a4c.web.app/")
                .build()
            // 2. 建立 OkHttpClient 物件，藉由 newCall()發送請求，並在 enqueue()接收回傳
            OkHttpClient().newCall(req).enqueue(object : Callback {
                // 失敗執行此方法
                override fun onFailure(call: Call, e: IOException) {
                    Toast.makeText(this@MainActivity, "查詢失敗", Toast.LENGTH_SHORT).show()
                }

                // 成功執行此方法
                override fun onResponse(call: Call, response: Response) {
                    // 3. 使用 response.body?.string()取得 JSON 字串
                    val json = response.body?.string()
                    // 4. 建立 Gson 並使用其 fromJson()方法，將 JSON 字串以 MyObject 格式輸出
                    val data = Gson().fromJson(json, DataModel::class.java)
                    // 5. 顯示結果
                    val items = ArrayList<Items>()
                    data.results.content.forEach {
                        items.add(Items(it.name, it.lat, it.lng))
                    }
                    // 切換到主執行緒將畫面更新
                    runOnUiThread {
                        listView.adapter = Adapter(this@MainActivity, items)
                    }
                }
            })
        }
    }
}