package com.tutorial.librarysystem

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.annotation.Nullable

class SQLite(
    @Nullable context: Context,
    name: String = "Library", // 資料庫名稱
    factory: SQLiteDatabase.CursorFactory ?= null,
    version: Int = 1 // 資料庫版本
): SQLiteOpenHelper(context, name, factory, version) {
    override fun onCreate(sqLiteDatabase: SQLiteDatabase?) {
        // 建Table
        sqLiteDatabase?.execSQL("create table library(book text primary key, price integer not null)")
    }

    override fun onUpgrade(sqLiteDatabase: SQLiteDatabase?, p1: Int, p2: Int) {
        // 更新資料庫版本，再次執行onCreate
        sqLiteDatabase?.execSQL("drop table if exists library")
        onCreate(sqLiteDatabase)
    }
}