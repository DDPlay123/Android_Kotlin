package com.tutorial.librarysubsystem

import android.annotation.SuppressLint
import android.content.ContentValues
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private val uri = Uri.parse("content://com.tutorial.librarymastersystem")

    @SuppressLint("Recycle")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val edBook = findViewById<EditText>(R.id.edit_book)
        val edPrice = findViewById<EditText>(R.id.edit_price)
        val btnInsert = findViewById<Button>(R.id.btn_insert)
        val btnUpdate = findViewById<Button>(R.id.btn_update)
        val btnDelete = findViewById<Button>(R.id.btn_delete)
        val btnQuery = findViewById<Button>(R.id.btn_query)
        val listView = findViewById<ListView>(R.id.listView)

        // 宣告 Adapter並連結listView
        val items: ArrayList<String> = ArrayList()
        val adapter: ArrayAdapter<String> = ArrayAdapter(this, android.R.layout.simple_list_item_1, items)
        listView.adapter = adapter

        btnInsert.setOnClickListener {
            if (edBook.length() == 0 || edPrice.length() == 0) {
                toast("請填入書籍及價格")
            } else {
                // 建立ContentValues物件，用於存放要新增的資料
                val values = ContentValues()
                values.put("book", edBook.text.toString())
                values.put("price", edPrice.text.toString())
                // 透過 Resolver 向 Provider 新增一筆書籍紀錄，並取得該紀錄的 Uri
                if (contentResolver.insert(uri, values) == null) {
                    toast("新增失敗")
                } else {
                    toast(
                        "成功新增:書籍 ${edBook.text},價格 ${edPrice.text}"
                    )
                }
            }
        }

        btnUpdate.setOnClickListener {
            if (edBook.length() == 0 || edPrice.length() == 0) {
                toast("請填入書籍及需修改的價格")
            } else {
                val values = ContentValues()
                values.put("price", edPrice.text.toString())
                if (contentResolver.update(
                        uri,
                        values,
                        edBook.text.toString(),
                        null
                    ) == 0
                ) {
                    toast("更新失敗")
                } else {
                    toast(
                        "成功更新:書籍 ${edBook.text},價格 ${edPrice.text}"
                    )
                }
            }
        }

        btnDelete.setOnClickListener {
            if (edBook.length() == 0) {
                toast("請填入需刪除的書籍")
            } else {
                if (contentResolver.delete(uri, edBook.text.toString(), null) == 0) {
                    toast("刪除失敗")
                } else {
                    toast("成功刪除:書籍 ${edBook.text}")
                }
            }
        }

        btnQuery.setOnClickListener {
            val query: String? = if (edBook.length() == 0) {
                null
            } else {
                "\'${edBook.text}\'"
            }
            val cursor = contentResolver.query(uri, null, query, null, null)
            cursor!!.moveToFirst() // 從第一筆開始輸出
            items.clear() // 清空舊資料
            toast("共有${cursor.count}筆")
            for (i in 0 until cursor.count) {
                // 加入新資料到items
                items.add("書籍: ${cursor.getString(0)} 價格: ${cursor.getString(1)}")
                cursor.moveToNext()
            }
            adapter.notifyDataSetChanged()
            cursor.close()
        }
    }

    private fun toast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}