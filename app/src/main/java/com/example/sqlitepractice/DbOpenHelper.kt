package com.example.sqlitepractice

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DbOpenHelper(context: Context) :
    SQLiteOpenHelper(context, dbName, null, dbVersion) {

    override fun onCreate(db: SQLiteDatabase) {
        val sql = "CREATE TABLE if not exists dbTABLE(id integer PRIMARY KEY AUTOINCREMENT,name NOT NULL)"
        db.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS dbTABLE")
        onCreate(db)
    }

    companion object
    {
        val dbName = "mydatabase.db"
        val dbVersion = 1
    }
}