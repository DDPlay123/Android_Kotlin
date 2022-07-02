package com.tutorial.librarymastersystem

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SQLite(
    context: Context?,
    name: String = "Library",
    factory: SQLiteDatabase.CursorFactory ?= null,
    version: Int = 1
): SQLiteOpenHelper(context, name, factory, version) {
    override fun onCreate(p0: SQLiteDatabase?) {
        // 建Table
        p0?.execSQL("create table library(book text primary key, price integer not null)")
    }

    override fun onUpgrade(sqLiteDatabase: SQLiteDatabase?, p1: Int, p2: Int) {
        // 更新資料庫版本，再次執行onCreate
        sqLiteDatabase?.execSQL("drop table if exists library")
        onCreate(sqLiteDatabase)
    }
}