package com.example.ch4

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tutorial.life_cycledetect.R

class SecondFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("Second","onCreate")
    }
    //在 onCreateView 中定義 SecondFragment 的畫面為 fragment_second
    override fun onCreateView(inflater: LayoutInflater, container:
    ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.e("Second","onCreateView")
        return inflater.inflate(R.layout.fragment_second, container, false)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.e("Second","onActivityCreated")
    }
    override fun onStart() {
        super.onStart()
        Log.e("Second","onStart")
    }
    override fun onResume() {
        super.onResume()
        Log.e("Second","onResume")
    }
    override fun onPause() {
        super.onPause()
        Log.e("Second","onPause")
    }
    override fun onStop() {
        super.onStop()
        Log.e("Second","onStop")
    }
    override fun onDestroyView() {
        super.onDestroyView()
        Log.e("Second","onDestroyView")
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.e("Second","onDestroy")
    }
    override fun onDetach() {
        super.onDetach()
        Log.e("Second","onDetach")
    }
}