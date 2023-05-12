package com.example.vcontachim.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.vcontachim.R
import com.example.vcontachim.databinding.FragmentPhotoBinding
import com.example.vcontachim.models.ItemPhotos
import com.example.vcontachim.viewmodel.PhotoViewModel
import com.google.android.material.snackbar.Snackbar
import java.io.Serializable

class PhotoFragment : Fragment(R.layout.fragment_photo) {
    private var binding: FragmentPhotoBinding? = null

    private val viewModel: PhotoViewModel by lazy {
        ViewModelProvider(this)[PhotoViewModel::class.java]
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPhotoBinding.bind(view)

        binding!!.toolbar.setNavigationOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                VcontachimApplication.router.exit()
            }
        })

        val photosSerializable: Serializable? = arguments?.getSerializable(SAVE_PHOTOS_KEY)
        val photos: ItemPhotos? = photosSerializable as? ItemPhotos

        if (photos!!.likes.userLikes > 0) {
            binding!!.likesImageView.setImageResource(R.drawable.like_filled_red_28)
        }

        if (photos != null) {
            binding!!.textViewLikes.text = photos.likes.count.toString()

            binding!!.textViewRepos.text = photos.reposts.count

            binding!!.textViewCimentaries.text = photos.comments.count

            Glide.with(this@PhotoFragment)
                .load(photos.sizes[0].url)
                .into(binding!!.imageView)
        }

        viewModel.errorLiveData.observe(viewLifecycleOwner) {
            val snackbar: Snackbar = Snackbar.make(
                requireView(),
                it,
                Snackbar.LENGTH_LONG
            )
            snackbar.show()
        }

        viewModel.likesLiveData.observe(viewLifecycleOwner) {
            if (photos.likes.userLikes < 1) {
                binding!!.textViewLikes.text = "${photos.likes.count + 1}"
                binding!!.likesImageView.setImageResource(R.drawable.like_filled_red_28)
            }
        }

        binding!!.linearLayoutLikes.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                viewModel.like(photos.id)
            }
        })
    }

    companion object {
        private const val SAVE_PHOTOS_KEY = "photos"

        fun createFragment(photos: ItemPhotos): Fragment {
            val fragment = PhotoFragment()
            val bundle = Bundle()
            bundle.putSerializable(SAVE_PHOTOS_KEY, photos)
            fragment.arguments = bundle

            return fragment
        }
    }
}
