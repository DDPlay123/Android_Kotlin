package com.tutorial.youtubemediaplayer.Adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.tutorial.youtubemediaplayer.Fragment.FragmentChat
import com.tutorial.youtubemediaplayer.Fragment.FragmentCommunity
import com.tutorial.youtubemediaplayer.Fragment.FragmentHome
import com.tutorial.youtubemediaplayer.Fragment.FragmentInfo

class ViewPagerAdapter(fa: FragmentActivity): FragmentStateAdapter(fa) {
    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment = when(position){
        0 -> FragmentHome()
        1 -> FragmentCommunity()
        2 -> FragmentChat()
        else -> FragmentInfo()
    }
}