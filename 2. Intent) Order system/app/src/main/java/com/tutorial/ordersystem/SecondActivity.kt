package com.tutorial.ordersystem

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity


class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val editDrink = findViewById<EditText>(R.id.edit_drink)
        val selectSweet = findViewById<RadioGroup>(R.id.select_sweet)
        val selectIce = findViewById<RadioGroup>(R.id.select_ice)
        val btnSend = findViewById<Button>(R.id.send)

        btnSend.setOnClickListener {
            if (editDrink.length() < 1) {
                Toast.makeText(this, "請輸入飲料名稱", Toast.LENGTH_SHORT).show()
            } else {
                val bundle = Bundle()
                bundle.putString("drink", editDrink.text.toString())
                bundle.putString("sweet", selectSweet.findViewById<RadioButton>(selectSweet.checkedRadioButtonId).text.toString())
                bundle.putString("ice", selectIce.findViewById<RadioButton>(selectIce.checkedRadioButtonId).text.toString())
                setResult(Activity.RESULT_OK, Intent().putExtras(bundle))
                finish()
            }
        }
    }
}