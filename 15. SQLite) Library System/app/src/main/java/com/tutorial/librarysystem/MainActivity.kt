package com.tutorial.librarysystem

import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class MainActivity : AppCompatActivity() {
    private lateinit var database: SQLiteDatabase

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

        // 取得資料庫
        database = SQLite(this).writableDatabase

        // 宣告 Adapter並連結listView
        val items: ArrayList<String> = ArrayList()
        val adapter: ArrayAdapter<String> = ArrayAdapter(this, android.R.layout.simple_list_item_1, items)
        listView.adapter = adapter

        btnInsert.setOnClickListener {
            if (edBook.length() == 0 || edPrice.length() == 0){
                toast("請填入書籍及價格")
            }else{
                try {
                    database.execSQL("insert into library(book,price) values(?,?)",
                        arrayOf(edBook.text.toString(), edPrice.text.toString()))
                    toast("成功新增:書籍${edBook.text},價格${edPrice.text}")
                    edBook.setText("")
                    edPrice.setText("")
                }catch (e: Exception){
                    e.printStackTrace()
                    toast("新增失敗")
                }
            }
        }
        btnUpdate.setOnClickListener {
            if (edBook.length() == 0 || edPrice.length() == 0){
                toast("請填入書籍及需修改的價格")
            }else{
                try {
                    database.execSQL("update library set price = ${edPrice.text} where book = '${edBook.text}'")
                    toast("成功更新:書籍${edBook.text},價格${edPrice.text}")
                    edBook.setText("")
                    edPrice.setText("")
                }catch (e: Exception){
                    e.printStackTrace()
                    toast("更新失敗")
                }
            }
        }
        btnDelete.setOnClickListener {
            if (edBook.length() == 0){
                toast("請填入需刪除的書籍")
            }else{
                try {
                    database.execSQL("delete from library where book = '${edBook.text}'")
                    toast("成功刪除:書籍${edBook.text}")
                    edBook.setText("")
                    edPrice.setText("")
                }catch (e: Exception){
                    e.printStackTrace()
                    toast("刪除失敗")
                }
            }
        }
        btnQuery.setOnClickListener {
            val query: String = if (edBook.length() == 0){
                "select * from library"
            } else{
                "select * from library where book = ${edBook.text}"
            }
            val cursor = database.rawQuery(query, null)
            cursor.moveToFirst() // 從第一筆開始輸出
            items.clear() // 清空舊資料
            toast("共有${cursor.count}筆")
            for (i in 0 until cursor.count){
                // 加入新資料到items
                items.add("書籍:${cursor.getString(0)}, 價格:${cursor.getString(1)}")
                cursor.moveToNext()
            }
            adapter.notifyDataSetChanged() // 更新列表
            cursor.close()
        }
    }

    private fun toast(msg: String){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        database.close() // 關閉資料庫
    }
}