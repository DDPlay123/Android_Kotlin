package com.tutorial.messagehint

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    @SuppressLint("InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val presetToast = findViewById<Button>(R.id.toast)
        val customToast = findViewById<Button>(R.id.custom)
        val buttonSnackBar = findViewById<Button>(R.id.snackBar)
        val buttonAlertdialog = findViewById<Button>(R.id.dialog1)
        val tableAlertdialog = findViewById<Button>(R.id.dialog2)
        val singleButtonAlertdialog = findViewById<Button>(R.id.dialog3)

        val item = arrayOf("選項 1", "選項 2", "選項 3", "選項 4", "選項 5")

        presetToast.setOnClickListener{
            Toast.makeText(this, "預設Toast", Toast.LENGTH_SHORT).show()
        }
        customToast.setOnClickListener{
            val toast = Toast(this)
            toast.setGravity(Gravity.BOTTOM, 0, 50)
            toast.duration = Toast.LENGTH_SHORT
            toast.view = layoutInflater.inflate(R.layout.custom_toast, null)
            toast.show()
        }
        buttonSnackBar.setOnClickListener{
            Snackbar.make(it, "按鈕式SnackBar", Snackbar.LENGTH_LONG).setAction("按鈕"){
                Toast.makeText(this, "已回應", Toast.LENGTH_SHORT).show()
            }.show()
        }
        buttonAlertdialog.setOnClickListener{
            AlertDialog.Builder(this)
                .setTitle("按鈕式AlertDialog")
                .setMessage("AlertDialog 內容")
                .setPositiveButton("右按鈕"){ _, _ ->
                    Toast.makeText(this, "右按鈕", Toast.LENGTH_SHORT).show()
                }
                .setNeutralButton("左按鈕"){ _, _ ->
                    Toast.makeText(this, "左按鈕", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("中按鈕"){ _, _ ->
                    Toast.makeText(this, "中按鈕", Toast.LENGTH_SHORT).show()
                }.show()
        }
        tableAlertdialog.setOnClickListener{
            AlertDialog.Builder(this)
                .setTitle("列表式AlertDialog")
                .setItems(item){ _, i ->
                    Toast.makeText(this, "你選的是${item[i]}", Toast.LENGTH_SHORT).show()
                }.show()
        }
        singleButtonAlertdialog.setOnClickListener{
            var position = 0
            AlertDialog.Builder(this)
                .setTitle("單選式AlertDialog")
                .setSingleChoiceItems(item, 0){ _, i ->
                    //紀錄按下的位置
                    position = i
                }
                .setPositiveButton("確定"){ _, _ ->
                    Toast.makeText(this, "你選的是${item[position]}", Toast.LENGTH_SHORT).show()
                }.show()
        }
    }
}