package com.tutorial.life_cycledetect

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.viewpager2.widget.ViewPager2

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = VP2Adapter(this)
        val viewPager2 = findViewById<ViewPager2>(R.id.viewPager)
        viewPager2.adapter = adapter
    }
    override fun onRestart() {
        super.onRestart()
        Log.e("Main","onRestart")
    }
    override fun onStart() {
        super.onStart()
        Log.e("Main","onStart")
    }
    override fun onResume() {
        super.onResume()
        Log.e("Main","onResume")
    }
    override fun onPause() {
        super.onPause()
        Log.e("Main","onPause")
    }
    override fun onStop() {
        super.onStop()
        Log.e("Main","onStop")
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.e("Main","onDestroy")
    }
}