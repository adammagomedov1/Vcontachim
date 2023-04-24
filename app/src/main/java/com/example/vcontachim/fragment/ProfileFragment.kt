package com.example.vcontachim.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.vcontachim.R
import com.example.vcontachim.databinding.FragmentProfileBinding
import com.example.vcontachim.models.Users
import com.example.vcontachim.viewmodel.ProfileViewModel
import com.google.android.material.snackbar.Snackbar

class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private var binding: FragmentProfileBinding? = null

    private val viewModel: ProfileViewModel by lazy {
        ViewModelProvider(this)[ProfileViewModel::class.java]
    }

    var users: Users? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileBinding.bind(view)

        viewModel.profileLiveData.observe(
            viewLifecycleOwner
        ) { t ->
            users = t

            Glide.with(this@ProfileFragment)
                .load(t!!.response[0].photo100)
                .into(binding!!.iconViewProfile)

            binding!!.name.text = t.response[0].firstName

            binding!!.surname.text = t.response[0].lastName

            binding!!.number.text = t.response[0].mobilePhone
        }

        viewModel.errorLiveData.observe(viewLifecycleOwner
        ) { t ->
            val error: Snackbar = Snackbar.make(
                requireView(),
                t,
                Snackbar.LENGTH_LONG
            )
            error.show()
        }

        viewModel.loadProfile()
    }
}