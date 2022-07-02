package com.tutorial.librarymastersystem

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.net.Uri

class MyContentProvider : ContentProvider() {
    private lateinit var database: SQLiteDatabase

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        // 取出 Sub System 要刪除的書名.
        return database.delete("library", "book = '$selection'", null)
    }

    override fun getType(uri: Uri): String? = null

    // 2. Resolver要求新增資料，則取得它給予的資料並新增至資料庫
    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        // 取出 Sub System 給予的 book 資料
        val rowId = database.insert("library", null, values)
        return Uri.parse("content://com.tutorial.librarymastersystem/$rowId")
    }

    // 1. 當Resolver要求操作資料時，先開啟資料庫
    override fun onCreate(): Boolean {
        // 取得資料庫
        database = SQLite(context).writableDatabase
        return true
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        // 取出 Sub System 查詢的書名，若沒有書名則搜尋全部書籍
        val query: String? = if (selection == null) {
            null
        } else {
            "book = $selection"
        }
        // 將搜尋完成的 Cursor 回傳
        return database.query("library", null, query, null, null, null, null)
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        return database.update("library", values, "book = '$selection'", null)
    }
}