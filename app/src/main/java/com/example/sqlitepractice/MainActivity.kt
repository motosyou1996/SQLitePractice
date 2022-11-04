package com.example.sqlitepractice

import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Adapter
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private var items : ArrayList<String> = ArrayList()
    private lateinit var adapter : ArrayAdapter<String>
    private lateinit var db : SQLiteDatabase
    private lateinit var editTxt : EditText
    private lateinit var btnEnter:Button
    private lateinit var btnDelete:Button
    private lateinit var listView:ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        db = DbOpenHelper(this).writableDatabase
        title = "排隊"
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1,items)
        editTxt  = findViewById(R.id.nameEditTxt)
        btnEnter  = findViewById(R.id.btnEnter)
        btnDelete = findViewById(R.id.btnDelete)
        listView  = findViewById(R.id.nameListView)
        listView.adapter = adapter
        show()

        btnEnter.setOnClickListener{
            if(editTxt.text.isBlank()) Toast.makeText(this,"請輸入姓名",Toast.LENGTH_SHORT).show()
            else
            {
                db.execSQL("INSERT INTO dbTABLE(Name) VALUES('${editTxt.text}')")
                Toast.makeText(this,"${editTxt.text}已加入駐列",Toast.LENGTH_SHORT).show()
                editTxt.setText("")
                show()

            }
        }
        btnDelete.setOnClickListener{
            db.execSQL("DELETE  FROM dbTABLE")
            items.clear()
            adapter.notifyDataSetChanged()
        }
    }

    fun show()
    {
        val cursor = db.rawQuery("SELECT * FROM dbTABLE",null)
        cursor.moveToFirst()
        items.clear()
        for(i in 0 until cursor.count)
        {
            items.add("姓名:${cursor.getString(1)}")
            cursor.moveToNext()
        }
        cursor.close()
        adapter.notifyDataSetChanged()
    }

    override fun onDestroy() {
        db.close()
        super.onDestroy()
    }
}