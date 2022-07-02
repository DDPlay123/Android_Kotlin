package com.tutorial.smartcamera

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.appcompat.app.AppCompatActivity
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.label.ImageLabel
import com.google.mlkit.vision.label.ImageLabeling
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions

class MainActivity : AppCompatActivity() {
    private lateinit var imageView: ImageView
    private var angle = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageView = findViewById(R.id.imageView)
        val btnPhoto = findViewById<Button>(R.id.btn_photo)
        val btnRotate = findViewById<Button>(R.id.btn_rotate)

        btnPhoto.setOnClickListener {
            mStartForResult.launch(Intent(MediaStore.ACTION_IMAGE_CAPTURE))
        }
        btnRotate.setOnClickListener {
            angle += 90f
            imageView.rotation = angle
            val bitmapDrawable =
                imageView.drawable as BitmapDrawable //取得 Bitmap
            val photo = bitmapDrawable.bitmap
            detector(photo)
        }
    }
    private val mStartForResult = registerForActivityResult(StartActivityForResult()) {
        if (it.resultCode == RESULT_OK) {
            val intent = it.data
            if (intent != null) {
                val bundle = intent.extras
                val photo = bundle!!["data"] as Bitmap?
                imageView.setImageBitmap(photo)
                if (photo != null) {
                    detector(photo)
                }
            }
        }
    }

    private fun detector(bitmap: Bitmap) {
        try {
            Log.d("Detector", "開始辨識")
            // 取得辨識標籤
            val labeler = ImageLabeling.getClient(ImageLabelerOptions.DEFAULT_OPTIONS)
            // 建立 InputImage 物件
            val inputImage = InputImage.fromBitmap(bitmap, 0)
            // 匹配辨識標籤與圖像，並建立執行成功與失敗的監聽器
            labeler.process(inputImage).addOnSuccessListener { imageLabels: List<ImageLabel> ->
                    // 取得辨識結果與準確度
                    val result: MutableList<String> = ArrayList()
                    for (label in imageLabels) {
                        val text = label.text
                        val confidence = label.confidence
                        result.add("$text, 準確度: $confidence")
                    }
                    // 將結果顯示於 ListView
                    val listView = findViewById<ListView>(R.id.listView)
                    listView.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, result)
                }
                .addOnFailureListener {
                    Toast.makeText(this, "辨識錯誤", Toast.LENGTH_SHORT).show()
                }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}