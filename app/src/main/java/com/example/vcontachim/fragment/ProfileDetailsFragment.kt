package com.example.vcontachim.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.vcontachim.R
import com.example.vcontachim.VcontachimApplication
import com.example.vcontachim.databinding.FragmentProfileDetailsBinding

class ProfileDetailsFragment : Fragment(R.layout.fragment_profile_details) {
    private var binding: FragmentProfileDetailsBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileDetailsBinding.bind(view)

        binding!!.iconBack.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                VcontachimApplication.router.exit()
            }
        })

    }
}