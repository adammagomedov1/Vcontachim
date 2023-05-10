package com.example.vcontachim.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.vcontachim.R
import com.example.vcontachim.Screens
import com.example.vcontachim.databinding.FragmentPhotoBinding
import com.example.vcontachim.models.ItemPhotos
import java.io.Serializable

class PhotoFragment : Fragment(R.layout.fragment_photo) {
    private var binding: FragmentPhotoBinding? = null

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

        if (photos != null) {
            binding!!.textViewLikes.setText(photos.likes.count)
            binding!!.textViewRepos.setText(photos.reposts.count)
            binding!!.textViewCimentaries.setText(photos.comments.count)

            Glide.with(this@PhotoFragment)
                .load(photos.sizes[0].url)
                .into(binding!!.imageView)
        }
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
