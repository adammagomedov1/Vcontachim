package com.example.vcontachim.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.vcontachim.R
import com.example.vcontachim.adapter.PhotosAdapter
import com.example.vcontachim.databinding.FragmentPhotoAlbumsBinding
import com.example.vcontachim.databinding.FragmentPhotosBinding
import com.example.vcontachim.models.ItemPhotoAlbums
import com.example.vcontachim.models.Photos
import com.example.vcontachim.viewmodel.PhotosViewModels
import com.google.android.material.snackbar.Snackbar

class PhotosFragment : Fragment(R.layout.fragment_photos) {
    private var binding: FragmentPhotosBinding? = null

    private val viewModel: PhotosViewModels by lazy {
        ViewModelProvider(this)[PhotosViewModels::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPhotosBinding.bind(view)

        val photosAdapter = PhotosAdapter()
        binding!!.recyclerViewPhotos.adapter = photosAdapter

        viewModel.photosLiveData.observe(viewLifecycleOwner) {
            photosAdapter.photosList = it.response.items
        }

        val photosLong = requireArguments().getLong(ARGUMENT_PHOTO)
        val photosLongName = requireArguments().getString(ARGUMENT_PHOTO_NAME)
        val photosLongThumbSrc = requireArguments().getLong(ARGUMENT_PHOTO_SIZE)

        binding!!.toolbarPhotos.title = photosLongName

//        binding!!.toolbarPhotos.subtitle = "$photosLongThumbSrc $followersCount"

        viewModel.progressBarLiveData.observe(viewLifecycleOwner, object : Observer<Boolean> {
            override fun onChanged(t: Boolean?) {
                if (t == true) {
                    binding!!.progressBarPhotos.visibility = View.VISIBLE
                } else {
                    binding!!.progressBarPhotos.visibility = View.GONE
                }
            }
        })

        viewModel.errorLiveData.observe(viewLifecycleOwner) {
            val snackbar = Snackbar.make(
                requireView(),
                it,
                Snackbar.LENGTH_LONG
            )
            snackbar.show()
        }
        viewModel.loadList(photosLong)
    }

    companion object {
        const val ARGUMENT_PHOTO = "photo"
        const val ARGUMENT_PHOTO_NAME = "name"
        const val ARGUMENT_PHOTO_SIZE = "size"


        fun createFragment(photoAlbums: ItemPhotoAlbums): Fragment {
            val fragment = PhotosFragment()
            val bundle = Bundle()
            bundle.putLong(ARGUMENT_PHOTO, photoAlbums.id)
            bundle.putString(ARGUMENT_PHOTO_NAME, photoAlbums.title)
            bundle.putLong(ARGUMENT_PHOTO_SIZE, photoAlbums.size)
            fragment.arguments = bundle

            return fragment

        }
    }
}