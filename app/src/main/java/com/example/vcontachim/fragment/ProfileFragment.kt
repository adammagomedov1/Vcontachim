package com.example.vcontachim.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.vcontachim.R
import com.example.vcontachim.Screens
import com.example.vcontachim.databinding.FragmentProfileBinding
import com.example.vcontachim.models.Response
import com.example.vcontachim.viewmodel.ProfileViewModel
import com.google.android.material.snackbar.Snackbar

class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private var binding: FragmentProfileBinding? = null

    private val viewModel: ProfileViewModel by lazy {
        ViewModelProvider(this)[ProfileViewModel::class.java]
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileBinding.bind(view)

        binding!!.friends.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                VcontachimApplication.router.navigateTo(Screens.friends())
            }
        })

        viewModel.profileLiveData.observe(
            viewLifecycleOwner
        ) {
            val response: Response = it.response[0]

            Glide.with(this@ProfileFragment)
                .load(response.photo100)
                .into(binding!!.iconViewProfile)

            binding!!.nameSurname.text = "${response.firstName} ${response.lastName}"

            binding!!.number.text = response.mobilePhone
        }

        viewModel.errorLiveData.observe(
            viewLifecycleOwner
        ) {
            val error: Snackbar = Snackbar.make(
                requireView(),
                it,
                Snackbar.LENGTH_LONG
            )
            error.show()
        }

        viewModel.loadProfile()
    }
}