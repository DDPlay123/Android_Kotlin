package com.tutorial.taiwanhighspeedrailsystem.Activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.tutorial.taiwanhighspeedrailsystem.R

class RestaurantActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant)
    }

    fun back(view: View) {
        super.onBackPressed()
    }
}