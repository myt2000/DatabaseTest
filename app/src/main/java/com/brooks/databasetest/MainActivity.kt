package com.brooks.databasetest

import android.annotation.SuppressLint
import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.NullPointerException

class MainActivity : AppCompatActivity() {
    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val dbHelper = MyDatabaseHelper(this, "BookStore.db", 2)
        createDatabase.setOnClickListener {
            dbHelper.writableDatabase
        }

        addData.setOnClickListener {
            val db = dbHelper.writableDatabase
            val values1 = ContentValues().apply {
                // 开始组装第一条数据
                put("name", "The Da Vinci Code")
                put("author", "Dan Brown")
                put("pages", 454)
                put("price", 16.96)
            }
            db.insert("Book", null, values1) // 输入第一条数据
            val values2 = ContentValues().apply {
                // 开始组装第二条数据
                put("name", "The Lost Symbol")
                put("author", "Dan Brown")
                put("pages", 510)
                put("price", 16.95)
            }
            db.insert("Book", null, values2) // 输入第二条数据

            Log.d("数据插入", "onCreate: 插入成功")
        }
        updateData.setOnClickListener {
            val db = dbHelper.writableDatabase
            val values = ContentValues()
            values.put("price", 10.99)
            db.update("Book", values, "name = ?", arrayOf("The Da Vinci Code"))
        }
        deleteData.setOnClickListener {
            val db = dbHelper.writableDatabase
            db.delete("Book", "pages > ?", arrayOf("500"))
        }
        queryData.setOnClickListener {
            val db = dbHelper.writableDatabase
            // 查询Book表中所有的数据
            val cursor = db.query("Book", null, null, null, null, null, null)
            if(cursor.moveToFirst()){
                do {
                    val name = cursor.getString(cursor.getColumnIndex("name"))
                    val author = cursor.getString(cursor.getColumnIndex("author"))
                    val pages = cursor.getInt(cursor.getColumnIndex("pages"))
                    val price = cursor.getDouble(cursor.getColumnIndex("price"))
                    Log.d("数据查询", "book name is $name")
                    Log.d("数据查询", "book author is $author")
                    Log.d("数据查询", "book page is $pages")
                    Log.d("数据查询", "book price is $price")
                } while (cursor.moveToNext())
            }
            cursor.close()
            // 添加数据
//            db.execSQL("insert into Book (name, author, pages, price) values(?, ?, ?, ?)",
//                arrayOf("The Da Vinci Code", "Dan Brown", "454", "16.96"))
//            db.execSQL("insert into Book (name, author, pages, price) values(?, ?, ?, ?)",
//                arrayOf("The Lost Symbol", "Dan Brown", "510", "16.95"))
//            // 更新数据
//            db.execSQL("update Book set price = ? where name = ?",
//                arrayOf("10.99", "The Da Vinci Code"))
//            // 删除数据
//            db.execSQL("delete from Book where pages > ?", arrayOf("500"))
//            // 查询数据
//            val curosr = db.rawQuery("select * from Book", null)
        }
        replaceData.setOnClickListener {
            val db = dbHelper.writableDatabase
            db.beginTransaction() // 开启事务
            try {
                db.delete("Book", null, null)
                if (true){
                    // 手动抛出一个异常，让事务失败
                    throw NullPointerException()
                }
                val values = ContentValues().apply {
                    put("name", "Game of Thrones")
                    put("author", "Georage Martin")
                    put("pages", 720)
                    put("price", 20.85)
                }
                db.insert("Book", null, values)
                db.setTransactionSuccessful() // 事务已经执行成功
            } catch (e: Exception){
                e.printStackTrace()
            } finally {
                db.endTransaction()
            }
        }
    }
}