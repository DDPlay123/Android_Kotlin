package com.tutorial.attractionssearch.SQLite

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class Database(
    context: Context,
    name: String = "myDatabase",
    factory: SQLiteDatabase.CursorFactory ?= null,
    version: Int = 1
) : SQLiteOpenHelper(context, name, factory, version){

    override fun onCreate(db: SQLiteDatabase?) {
        // 創建表格 _id, name, vicinity
        db?.execSQL("CREATE TABLE history(_id integer PRIMARY KEY AUTOINCREMENT, name text, vicinity text)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS history")
        onCreate(db)
    }
}