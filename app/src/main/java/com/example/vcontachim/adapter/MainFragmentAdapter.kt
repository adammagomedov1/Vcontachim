package com.example.vcontachim.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.vcontachim.enum1.MyEnum
import com.example.vcontachim.fragment.NewsFragment

class MainFragmentAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                NewsFragment(MyEnum.LEFT)
            }
            else -> {
                NewsFragment(MyEnum.RIGHT)
            }
        }
    }
}