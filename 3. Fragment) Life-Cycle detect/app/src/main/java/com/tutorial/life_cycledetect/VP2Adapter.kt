package com.tutorial.life_cycledetect

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.ch4.FirstFragment
import com.example.ch4.SecondFragment
import com.example.ch4.ThirdFragment

class VP2Adapter(fa: FragmentActivity): FragmentStateAdapter(fa) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment = when(position) {
        0 -> FirstFragment()
        1 -> SecondFragment()
        else -> ThirdFragment()
    }
}