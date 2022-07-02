package com.tutorial.contractbook

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ch7.Adapter

class MainActivity : AppCompatActivity() {
    private val item = ArrayList<Items>()
    private val adapter = Adapter(item)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val btnAdd = findViewById<Button>(R.id.add)

        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = linearLayoutManager

        recyclerView.adapter = adapter

        btnAdd.setOnClickListener {
            mStartForResult.launch(Intent(this, SecActivity::class.java))
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    private val mStartForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        result -> if (result.resultCode == Activity.RESULT_OK) {
            val intent = result.data
            if (intent != null) {
                val name = intent.getStringExtra("name") ?: return@registerForActivityResult
                val phone = intent.getStringExtra("phone") ?: return@registerForActivityResult
                item.add(Items(name, phone))

                adapter.notifyDataSetChanged()
            }
        }
    }
}