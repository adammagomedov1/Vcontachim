package com.example.vcontachim.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.vcontachim.enum1.MyEnum
import com.example.vcontachim.fragment.NewsFragment

class MainFragmentAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    private lateinit var myEnum: MyEnum

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                myEnum = MyEnum.LEFT
                NewsFragment(myEnum)
            }
            else -> {
                myEnum = MyEnum.RIGHT
                NewsFragment(myEnum)
            }
        }
    }
}