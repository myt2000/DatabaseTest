package com.brooks.databasetest

import android.content.ContentValues
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.contentValuesOf
import androidx.core.content.edit

class SharedPreferencesTest:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val editor = getSharedPreferences("data", Context.MODE_PRIVATE).edit()
        editor.putString("name", "Tom")
        editor.putInt("age", 28)
        editor.putBoolean("married", false)
        editor.apply()
    }

    fun SharedPreferences.open(block: SharedPreferences.Editor.() -> Unit) {
        val editor = edit()
        editor.block()
        editor.apply()
    }

    override fun onDestroy() {
        super.onDestroy()
        getSharedPreferences("data", Context.MODE_PRIVATE).edit {
            putString("name", "Tom")
            putInt("age", 28)
            putBoolean("married", false)
        }
//        val values = cvOf("name" to "Game of Thrones", "author" to "George Martin", "pages" to 720, "price" to 20.85)
//        db.insert("Book", null, values)
//        val values = contentValuesOf("name" to "Game of Thrones", "author" to "George Martin", "pages" to 720, "price" to 20.85)
//        db.insert("Book", null, values)
    }

//    fun cvOf(vararg  paris: Pair<String, Any?>): ContentValues{}

    fun cvOf(vararg pairs: Pair<String, Any?>) = ContentValues().apply {
        for (pair in pairs) {
            val key = pair.first
            val value = pair.second
            when(value) {
                is Int -> put(key, value)
                is Long -> put(key, value)
                is Short -> put(key, value)
                is Float -> put(key, value)
                is Double -> put(key, value)
                is Boolean -> put(key, value)
                is String -> put(key, value)
                is Byte -> put(key, value)
                is ByteArray -> put(key, value)
                null -> putNull(key)
            }
        }
    }
}