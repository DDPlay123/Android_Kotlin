package com.tutorial.finger_guessinggame

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editName = findViewById<EditText>(R.id.edit_name)
        val radioGroup = findViewById<RadioGroup>(R.id.radioGroup)
        val btnMora = findViewById<Button>(R.id.mora)
        val tvPlayerName = findViewById<TextView>(R.id.player_name)
        val tvWinner = findViewById<TextView>(R.id.winner)
        val tvPlayerMora = findViewById<TextView>(R.id.player_mora)
        val tvPcMora = findViewById<TextView>(R.id.pc_mora)

        btnMora.setOnClickListener {
            val selectMora = when(radioGroup.checkedRadioButtonId) {
                R.id.paper -> "布"
                R.id.scissor -> "剪刀"
                R.id.stone -> "石頭"
                else -> "布"
            }
            val pcMora = when((0..2).random()) {
                0 -> "布"
                1-> "剪刀"
                2 -> "石頭"
                else -> "布"
            }
            if (editName.length() < 1) {
                Toast.makeText(this, "請輸入你的名字!!!", Toast.LENGTH_SHORT).show()
                tvPlayerName.text = ""
                tvWinner.text = ""
                tvPlayerMora.text = ""
                tvPcMora.text = ""
            } else {
                tvPlayerName.text = editName.text
                tvPlayerMora.text = selectMora
                tvPcMora.text = pcMora
                if (selectMora == "布" && pcMora == "石頭" ||
                    selectMora == "剪刀" && pcMora == "布" ||
                    selectMora == "石頭" && pcMora == "剪刀") {
                    tvWinner.text = editName.text
                    tvWinner.setTextColor(Color.parseColor("#00FF00"))
                    Toast.makeText(this, "你獲勝了", Toast.LENGTH_SHORT).show()
                } else if (selectMora == "布" && pcMora == "剪刀" ||
                           selectMora == "剪刀" && pcMora == "石頭" ||
                           selectMora == "石頭" && pcMora == "布") {
                    tvWinner.text = "電腦"
                    tvWinner.setTextColor(Color.parseColor("#FF0000"))
                    Toast.makeText(this, "你輸了", Toast.LENGTH_SHORT).show()
                } else {
                    tvWinner.text = "平局"
                    tvWinner.setTextColor(Color.parseColor("#000000"))
                    Toast.makeText(this, "平局", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}