package com.tutorial.contractbook

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class SecActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sec)

        val editName = findViewById<EditText>(R.id.edit_name)
        val editPhone = findViewById<EditText>(R.id.edit_phone)
        val btnSend = findViewById<Button>(R.id.send)

        btnSend.setOnClickListener {
            when {
                editName.length()  < 1 -> Toast.makeText(this, "請輸入姓名", Toast.LENGTH_SHORT).show()
                editPhone.length() < 1 -> Toast.makeText(this, "請輸入電話", Toast.LENGTH_SHORT).show()
                else -> {
                    val bundle = Bundle()
                    bundle.putString("name", editName.text.toString())
                    bundle.putString("phone", editPhone.text.toString())
                    setResult(Activity.RESULT_OK, Intent().putExtras(bundle))
                    finish()
                }
            }
        }
    }
}