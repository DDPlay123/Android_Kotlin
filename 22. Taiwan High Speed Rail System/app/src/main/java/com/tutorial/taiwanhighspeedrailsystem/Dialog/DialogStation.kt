package com.tutorial.taiwanhighspeedrailsystem.Dialog

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MotionEvent
import android.widget.*
import com.tutorial.taiwanhighspeedrailsystem.Adapter.ListAdapter
import com.tutorial.taiwanhighspeedrailsystem.Data.StationData
import com.tutorial.taiwanhighspeedrailsystem.R

class DialogStation(context: Context, private val items: ArrayList<StationData>, private val  text: TextView): AlertDialog(context) {

    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_search)

        val btnClear = findViewById<ImageButton>(R.id.btn_clear)
        val edStation = findViewById<EditText>(R.id.ed_station)
        val listView = findViewById<ListView>(R.id.listView)

        var adapter = ListAdapter(context, items, R.layout.item_search)
        listView!!.adapter = adapter
        adapter.notifyDataSetChanged()

        listView.setOnItemClickListener{ _, _, position, _ ->
            text.text = items[position].station
            dismiss()
        }

        btnClear?.setOnClickListener {edStation.setText("")}
        edStation.isSingleLine = true

        edStation.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                val temp: ArrayList<StationData> = ArrayList()
                val string = s.toString()
                items.forEach {
                    if (it.address.contains(string)) {
                        temp.add(it)
                    }
                }
                adapter = ListAdapter(context, temp, R.layout.item_search)
                listView.adapter = adapter
                adapter.notifyDataSetChanged()
                listView.setOnItemClickListener{ _, _, position, _ ->
                    text.text = temp[position].station
                    dismiss()
                }
            }
        })
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        dismiss()
        return super.onTouchEvent(event)
    }
}