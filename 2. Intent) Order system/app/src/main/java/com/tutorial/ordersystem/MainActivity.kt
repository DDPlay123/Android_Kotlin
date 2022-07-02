package com.tutorial.ordersystem

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts

class MainActivity : AppCompatActivity() {
    private lateinit var menu: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val select = findViewById<Button>(R.id.select)
        menu = findViewById<TextView>(R.id.menu)

        select.setOnClickListener {
            mStartForResult.launch(Intent(this, SecondActivity::class.java))
        }
    }
    // https://developer.android.com/training/basics/intents/result
    @SuppressLint("SetTextI18n")
    private val mStartForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        result: ActivityResult -> if (result.resultCode == Activity.RESULT_OK) {
            val intent = result.data
            if (intent != null) {
                val drink = intent.getStringExtra("drink")
                val sweet  = intent.getStringExtra("sweet")
                val ice  = intent.getStringExtra("ice")
                menu.text = "飲料: $drink\n\n甜度: $sweet\n\n冰塊: $ice"
            }
        }
    }
}