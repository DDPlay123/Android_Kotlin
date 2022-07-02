package com.tutorial.youtubemediaplayer.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.tutorial.youtubemediaplayer.Adapter.ViewPagerAdapter
import com.tutorial.youtubemediaplayer.R

class BaseActivity : AppCompatActivity() {
    private lateinit var viewPager: ViewPager2
    private lateinit var navigation: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        // Define ViewPager2 and Adapter
        val adapter = ViewPagerAdapter(this)
        viewPager = findViewById(R.id.baseFragment)
        viewPager.adapter = adapter
        // Listener ViewPager2
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                // Listener Fragment slide to change BottomNavigationView
                navigation.menu.getItem(position).isChecked = true
            }
        })
        // Define BottomNavigationView
        navigation = findViewById(R.id.navigation)
        // Listener BottomNavigationView
        navigation.setOnItemSelectedListener { item ->
            when(item.itemId){
                R.id.navigation_main ->{
                    viewPager.currentItem = 0
                    return@setOnItemSelectedListener true
                }
                R.id.navigation_community ->{
                    viewPager.currentItem = 1
                    return@setOnItemSelectedListener true
                }
                R.id.navigation_chat ->{
                    viewPager.currentItem = 2
                    return@setOnItemSelectedListener true
                }
                R.id.navigation_information ->{
                    viewPager.currentItem = 3
                    return@setOnItemSelectedListener true
                }else -> viewPager.currentItem = 0
            }
            true
        }
    }
}