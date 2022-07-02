package com.tutorial.taiwanhighspeedrailsystem.Dialog

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.tutorial.taiwanhighspeedrailsystem.Adapter.ListAdapter
import com.tutorial.taiwanhighspeedrailsystem.Data.StationData
import com.tutorial.taiwanhighspeedrailsystem.R

class DialogStationSearch(context: Context, private val items: ArrayList<StationData>, private val map: GoogleMap): AlertDialog(context) {

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
            map.animateCamera(
                CameraUpdateFactory.newLatLngZoom(
                    LatLng(items[position].latitude.toDouble(),
                           items[position].longitude.toDouble()), 13f)
            )
            dismiss()
        }

        btnClear!!.setOnClickListener {
            edStation!!.setText("")
        }
        edStation!!.isSingleLine = true


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
                    map.animateCamera(
                        CameraUpdateFactory.newLatLngZoom(
                            LatLng(temp[position].latitude.toDouble(),
                                temp[position].longitude.toDouble()), 13f)
                    )
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