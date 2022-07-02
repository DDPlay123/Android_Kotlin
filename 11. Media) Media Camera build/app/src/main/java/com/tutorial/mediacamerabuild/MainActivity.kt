package com.tutorial.mediacamerabuild

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var imageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageView = findViewById(R.id.image)
        val btnPhoto = findViewById<Button>(R.id.btn_photo)
        val btnRotate = findViewById<Button>(R.id.btn_rotate)

        btnPhoto.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            mStartForResult.launch(intent)
        }
        btnRotate.setOnClickListener {
            imageView.rotation = 90f
        }
    }
    private val mStartForResult = registerForActivityResult(StartActivityForResult()) {
        if (it.resultCode == RESULT_OK) {
            val intent = it.data
            if (intent != null) {
                val bundle = intent.extras
                val photo = bundle!!["data"] as Bitmap?
                imageView.setImageBitmap(photo)
            }
        }
    }
}