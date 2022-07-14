package com.tutorial.bmi

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.pow

class MainActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val edHeight = findViewById<EditText>(R.id.edit_height)
        val edWeight = findViewById<EditText>(R.id.edit_weight)
        val edAge = findViewById<TextView>(R.id.edit_age)
        val rgBoy = findViewById<RadioButton>(R.id.rg_boy)
        val btnCalculate = findViewById<Button>(R.id.btn_calculate)
        val tvWeight = findViewById<TextView>(R.id.tv_weight)
        val tvFat = findViewById<TextView>(R.id.tv_fat)
        val tvBmi = findViewById<TextView>(R.id.tv_bmi)
        val llProgress = findViewById<LinearLayout>(R.id.ll_progress)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar2)
        val tvProgressBar = findViewById<TextView>(R.id.tv_progress)

        btnCalculate.setOnClickListener {
            if (edHeight.length() < 1 || edWeight.length() < 1 || edAge.length() < 1) {
                Toast.makeText(this, "請輸入身高、體重及年齡", Toast.LENGTH_SHORT).show()
            } else {
                val height = edHeight.text.toString().toDouble()
                val weight = edWeight.text.toString().toDouble()
                val age = edAge.text.toString().toDouble()

                val bmi = weight / ((height / 100).pow(2))
                val standWeight: Double
                val bodyFat: Double

                if (rgBoy.isChecked){
                    standWeight = (height - 80) * 0.7
                    bodyFat = (1.39 * bmi + 0.16 * age - 19.34)
                }else{
                    standWeight = (height - 70) * 0.6
                    bodyFat = (1.39 * bmi + 0.16 * age - 9)
                }

                llProgress.visibility = View.VISIBLE
                GlobalScope.launch(Dispatchers.Main) {
                    var progress = 0
                    while (progress < 100){
                        delay(10)
                        progressBar.progress = progress
                        tvProgressBar.text = "${progress}%"
                        progress += 1
                    }
                    // 關閉進度條
                    llProgress.visibility = View.GONE
                    tvWeight.text = "標準體重\n${String.format("%.2f", standWeight)}"
                    tvFat.text = "體脂肪\n${String.format("%.2f", bodyFat)}"
                    tvBmi.text = "BMI\n${String.format("%.2f", bmi)}"
                }
            }
        }
    }
}