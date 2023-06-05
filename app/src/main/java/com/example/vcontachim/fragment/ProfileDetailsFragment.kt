package com.example.vcontachim.fragment

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.vcontachim.R
import com.example.vcontachim.VcontachimApplication
import com.example.vcontachim.databinding.FragmentProfileDetailsBinding
import com.example.vcontachim.models.Response
import com.example.vcontachim.models.ResponseProfileDetail
import com.example.vcontachim.viewmodel.ProfileDetailsViewModel
import com.google.android.material.color.ColorRoles
import com.google.android.material.color.MaterialColors

class ProfileDetailsFragment : Fragment(R.layout.fragment_profile_details) {
    private var binding: FragmentProfileDetailsBinding? = null

    val viewModel: ProfileDetailsViewModel by lazy {
        ViewModelProvider(this)[ProfileDetailsViewModel::class.java]
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileDetailsBinding.bind(view)

        binding!!.iconBack.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                VcontachimApplication.router.exit()
            }
        })

        val profileDetailSerializable = requireArguments().getSerializable(SAVE_USERS_KEY)
        val profileDetail = profileDetailSerializable as Response

        viewModel.profileDetailLiveData.observe(viewLifecycleOwner) {
            val responseProfileDetail: ResponseProfileDetail = it.response[0]

            if (responseProfileDetail.id == profileDetail.id) {
                binding!!.subscribeOrAddFriend.visibility = View.GONE
            }

            Glide.with(this)
                .load(responseProfileDetail.photo200)
                .into(binding!!.imageViewAvatar)

            if (responseProfileDetail.online == 1) {
                binding!!.imageViewOnline.setImageResource(R.drawable.ellipse_185)
            } else {
                binding!!.imageViewOnline.setImageResource(R.drawable.emptiness)
            }

            binding!!.textViewFirstNameLastName.text =
                "${responseProfileDetail.firstName} ${responseProfileDetail.lastName}"

            binding!!.textViewBriefInformation.text = responseProfileDetail.status

            binding!!.textViewLocation.text = responseProfileDetail.city.title

            if (responseProfileDetail.verified == 1) {
                binding!!.imageViewVerified.setImageResource(R.drawable.verified_20)
            }

        }

        viewModel.errorLiveData.observe(viewLifecycleOwner) {
            val toast = Toast.makeText(
                requireContext(),
                it,
                Toast.LENGTH_LONG
            )
            toast.show()
        }

        viewModel.loadProfileDetails(profileDetail)
    }

    companion object {
        private const val SAVE_USERS_KEY = "users"

        fun createFragment(response: Response): Fragment {
            val fragment = ProfileDetailsFragment()
            val bundle = Bundle()
            bundle.putSerializable(SAVE_USERS_KEY, response)
            fragment.arguments = bundle

            return fragment
        }
    }
}