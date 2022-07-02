package com.example.ch4

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tutorial.life_cycledetect.R

class FirstFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("First","onCreate")
    }
    //在 onCreateView 中定義 FirstFragment 的畫面為 fragment_first
    override fun onCreateView(inflater: LayoutInflater, container:
    ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.e("First","onCreateView")
        return inflater.inflate(R.layout.fragment_first, container, false)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.e("First","onActivityCreated")
    }
    override fun onStart() {
        super.onStart()
        Log.e("First","onStart")
    }
    override fun onResume() {
        super.onResume()
        Log.e("First","onResume")
    }
    override fun onPause() {
        super.onPause()
        Log.e("First","onPause")
    }
    override fun onStop() {
        super.onStop()
        Log.e("First","onStop")
    }
    override fun onDestroyView() {
        super.onDestroyView()
        Log.e("First","onDestroyView")
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.e("First","onDestroy")
    }
    override fun onDetach() {
        super.onDetach()
        Log.e("First","onDetach")
    }
}