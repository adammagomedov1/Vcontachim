package com.example.vcontachim.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.vcontachim.R
import com.example.vcontachim.adapter.MainScreenAdapter
import com.example.vcontachim.databinding.FragmentMainScreenBinding
import com.example.vcontachim.viewmodel.NewsViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainScreenFragment : Fragment(R.layout.fragment_main_screen) {
    var binding: FragmentMainScreenBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainScreenBinding.bind(view)

        val newsAdapter = MainScreenAdapter(this)
        binding!!.mainScreenViewPager2.adapter = newsAdapter

        val mainScreenTabLayoutMediator = TabLayoutMediator(
            binding!!.mainScreenTabLayout,
            binding!!.mainScreenViewPager2,
            object : TabLayoutMediator.TabConfigurationStrategy {
                override fun onConfigureTab(tab: TabLayout.Tab, position: Int) {
                    when (position) {
                        0 -> tab.setText(R.string.news)
                        else -> tab.setText(R.string.recommended)
                    }
                }
            })
        mainScreenTabLayoutMediator.attach()
    }
}