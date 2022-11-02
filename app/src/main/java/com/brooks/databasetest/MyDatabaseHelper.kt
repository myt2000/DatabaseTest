package com.brooks.databasetest

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast

class MyDatabaseHelper(val context: Context, name: String, version: Int) : SQLiteOpenHelper(context, name, null, version){
    private val createBook = "create table Book (" +
            "id integer primary key autoincrement," +
            "author text," +
            "price real," +
            "pages integer," +
            "name text)"

    private val createCategory = "create table Category (" +
            "id integer primary key autoincrement," +
            "category_name text," +
            "category_code integer" +
            ")"

    override fun onCreate(db: SQLiteDatabase) {
        Log.d("数据库表创建", "onCreate: 开始创建" )
//        db.execSQL("create table Category (id integer primary key autoincrement, category_name text, category_code integer)")
        db.execSQL(createBook)
        db.execSQL(createCategory)
        Log.d("数据库表创建", "onCreate: 创建成功" )
        Toast.makeText(context, "Create succeeded", Toast.LENGTH_SHORT).show()
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("drop table if exists Book")
        db.execSQL("drop table if exists Category")

        onCreate(db)
    }


}