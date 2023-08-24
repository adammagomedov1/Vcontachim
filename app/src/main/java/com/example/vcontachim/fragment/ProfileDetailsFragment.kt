package com.example.vcontachim.fragment

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.vcontachim.R
import com.example.vcontachim.VcontachimApplication
import com.example.vcontachim.databinding.FragmentProfileDetailsBinding
import com.example.vcontachim.models.*
import com.example.vcontachim.utility.showToast
import com.example.vcontachim.viewmodel.ProfileDetailsViewModel

class ProfileDetailsFragment : Fragment(R.layout.fragment_profile_details) {
    private var binding: FragmentProfileDetailsBinding? = null

    val viewModel: ProfileDetailsViewModel by lazy {
        ViewModelProvider(this)[ProfileDetailsViewModel::class.java]
    }

    @SuppressLint("SetTextI18n", "ResourceAsColor")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileDetailsBinding.bind(view)

        binding!!.iconBack.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                VcontachimApplication.router.exit()
            }
        })

        val profileDetailSerializable = requireArguments().getLong(SAVE_ID_KEY)
        viewModel.loadProfileDetails(profileDetailSerializable)

        viewModel.profileDetailLiveData.observe(viewLifecycleOwner) {
            val responseProfileDetail: ResponseProfileDetail = it

            Glide.with(this)
                .load(responseProfileDetail.photo200)
                .into(binding!!.imageViewAvatar)

            if (responseProfileDetail.online == 1) {
                binding!!.imageViewOnline.setImageResource(R.drawable.ellipse_185)
            } else {
                binding!!.imageViewOnline.setImageResource(R.drawable.emptiness)
            }

            if (responseProfileDetail.canSendFriendRequest == 1) {
                binding!!.subscribeOrAddFriend.setText(R.string.subscribe)
                binding!!.subscribeOrAddFriend.setIconResource(R.drawable.add_square_outline_16)

                val numberOfViews: String =
                    VcontachimApplication.context.resources.getQuantityString(
                        R.plurals.number_of_subscribers,
                        responseProfileDetail.counters.followers.toInt()
                    )

                binding!!.numberFollowersOrFriends.text =
                    "${responseProfileDetail.counters.followers} $numberOfViews"
            } else {
                if (responseProfileDetail.isFriend == 1) {
                    binding!!.subscribeOrAddFriend.apply {
                        setText(R.string.in_friends)
                        setIconResource(R.drawable.verified_20)
                        setIconTintResource(R.color.light_blue)
                        background.setTint(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.light_grey
                            )
                        )
                    }
                    binding!!.subscribeOrAddFriend.setTextColor(Color.parseColor("#2688EB"))
                    binding!!.subscribeOrAddFriend.setOnClickListener {
                        viewModel.deleteFriend(profileDetailSerializable)
                    }
                } else {
                    binding!!.subscribeOrAddFriend.apply {
                        setText(R.string.add_friend)
                        setIconResource(R.drawable.user_add_outline_20)
                        setIconTintResource(R.color.white)
                        setTextColor(Color.parseColor("#FFFFFF"))
                        background.setTint(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.light_blue
                            )
                        )
                    }
                    binding!!.subscribeOrAddFriend.setOnClickListener {
                        viewModel.addFriend(profileDetailSerializable)
                    }
                }

                val numberOfViews: String =
                    VcontachimApplication.context.resources.getQuantityString(
                        R.plurals.number_of_friends,
                        responseProfileDetail.counters.friends.toInt()
                    )

                binding!!.numberFollowersOrFriends.text =
                    "${responseProfileDetail.counters.friends} $numberOfViews"
            }
            binding!!.textViewFirstNameLastName.text =
                "${responseProfileDetail.firstName} ${responseProfileDetail.lastName}"

            if (responseProfileDetail.status != "") {
                binding!!.textViewBriefInformation.text = responseProfileDetail.status
            }

            binding!!.textViewLocation.text = responseProfileDetail.city?.title

            if (responseProfileDetail.verified == 1) {
                binding!!.imageViewVerified.setImageResource(R.drawable.verified_20)
            }

            if (responseProfileDetail.career != null && responseProfileDetail.career.isNotEmpty()) {
                binding!!.textViewCareer.text = responseProfileDetail.career[0].position
            } else {
                binding!!.textViewCareer.setText(R.string.not_filled)
            }
        }

        viewModel.errorLiveData.observe(viewLifecycleOwner) {
            showToast(text = it)
        }
    }

    companion object {
        private const val SAVE_ID_KEY = "id"

        fun createFragment(id: Long): Fragment {
            val fragment = ProfileDetailsFragment()
            val bundle = Bundle()
            bundle.putLong(SAVE_ID_KEY, id)
            fragment.arguments = bundle
            return fragment
        }
    }
}