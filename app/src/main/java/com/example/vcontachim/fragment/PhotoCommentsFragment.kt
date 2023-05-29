package com.example.vcontachim.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.vcontachim.R
import com.example.vcontachim.VcontachimApplication
import com.example.vcontachim.adapter.PhotoCommentsAdapter
import com.example.vcontachim.databinding.FragmentPhotoCommentsBinding
import com.example.vcontachim.models.ItemPhotos
import com.example.vcontachim.models.PhotoCommentsUi
import com.example.vcontachim.viewmodel.PhotoCommentsViewModel
import java.io.Serializable

class PhotoCommentsFragment : Fragment(R.layout.fragment_photo_comments) {
    private var binding: FragmentPhotoCommentsBinding? = null

    private val viewModel: PhotoCommentsViewModel by lazy {
        ViewModelProvider(this)[PhotoCommentsViewModel::class.java]
    }

    private var photoCommentsAdapter =
        PhotoCommentsAdapter(likeListener = object : PhotoCommentsAdapter.LikeListener {
            override fun onClick(
                photoCommentsUi: PhotoCommentsUi
            ) {
                if (photoCommentsUi.userLikes == 0) {
                    viewModel.commentLike(photoCommentsUi)
                } else {
                    viewModel.deleteLikeComment(photoCommentsUi)
                }
            }
        })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPhotoCommentsBinding.bind(view)

        val photosSerializable: Serializable =
            arguments?.getSerializable(SAVE_PHOTOS_COMMENTS_KEY)!!
        val photos = photosSerializable as ItemPhotos

        binding!!.toolbarFromPhotoCommentsScreen.setNavigationOnClickListener {
            VcontachimApplication.router.exit()
        }

        binding!!.toolbarFromPhotoCommentsScreen.subtitle = photos.comments.count

        binding!!.recyclerViewFromPhotoCommentsScreen.adapter = photoCommentsAdapter

        viewModel.photoCommentsLiveData.observe(viewLifecycleOwner) {
            photoCommentsAdapter.submitList(it)

        }

        viewModel.errorLiveData.observe(viewLifecycleOwner) {
            val toast = Toast.makeText(
                requireContext(),
                it,
                Toast.LENGTH_LONG
            )
            toast.show()
        }

        viewModel.loadPhotoComments(photos)
    }

    companion object {
        private const val SAVE_PHOTOS_COMMENTS_KEY = "photoComments"

        fun createFragment(photos: ItemPhotos): Fragment {
            val fragment = PhotoCommentsFragment()
            val bundle = Bundle()
            bundle.putSerializable(SAVE_PHOTOS_COMMENTS_KEY, photos)
            fragment.arguments = bundle

            return fragment
        }
    }
}