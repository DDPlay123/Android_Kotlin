package com.example.ch4

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tutorial.life_cycledetect.R

class ThirdFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("Third","onCreate")
    }
    //在 onCreateView 中定義 ThirdFragment 的畫面為 fragment_third
    override fun onCreateView(inflater: LayoutInflater, container:
    ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.e("Third","onCreateView")
        return inflater.inflate(R.layout.fragment_third, container, false)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.e("Third","onActivityCreated")
    }
    override fun onStart() {
        super.onStart()
        Log.e("Third","onStart")
    }
    override fun onResume() {
        super.onResume()
        Log.e("Third","onResume")
    }
    override fun onPause() {
        super.onPause()
        Log.e("Third","onPause")
    }
    override fun onStop() {
        super.onStop()
        Log.e("Third","onStop")
    }
    override fun onDestroyView() {
        super.onDestroyView()
        Log.e("Third","onDestroyView")
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.e("Third","onDestroy")
    }
    override fun onDetach() {
        super.onDetach()
        Log.e("Third","onDetach")
    }
}