package com.example.vcontachim.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.vcontachim.R
import com.example.vcontachim.adapter.PhotosAdapter
import com.example.vcontachim.databinding.FragmentPhotosBinding
import com.example.vcontachim.models.ItemPhotoAlbums
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

        binding!!.toolbarPhotos.setNavigationOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                VcontachimApplication.router.exit()
            }
        })

        val photosAdapter = PhotosAdapter()
        binding!!.recyclerViewPhotos.adapter = photosAdapter

        viewModel.photosLiveData.observe(viewLifecycleOwner) {
            photosAdapter.photosList = it.response.items
        }

        val takeALongId = requireArguments().getLong(SAVE_PHOTO_KEY)
        val takeStringName = requireArguments().getString(SAVE_NAME_KEY)
        val findOutTheNumberOfPhotos = requireArguments().getLong(NUMBER_OF_PHOTOS_KEY)

        val followersCount: String = VcontachimApplication.context.resources.getQuantityString(
            R.plurals.number_of_photos,
            findOutTheNumberOfPhotos.toInt()
        )

        binding!!.toolbarPhotos.title = takeStringName

        binding!!.toolbarPhotos.subtitle = "$findOutTheNumberOfPhotos $followersCount"

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
        viewModel.loadList(takeALongId)
    }

    companion object {
        const val SAVE_PHOTO_KEY = "photo"
        const val SAVE_NAME_KEY = "name"
        const val NUMBER_OF_PHOTOS_KEY = "size"


        fun createFragment(photoAlbums: ItemPhotoAlbums): Fragment {
            val fragment = PhotosFragment()
            val bundle = Bundle()
            bundle.putLong(SAVE_PHOTO_KEY, photoAlbums.id)
            bundle.putString(SAVE_NAME_KEY, photoAlbums.title)
            bundle.putLong(NUMBER_OF_PHOTOS_KEY, photoAlbums.size)
            fragment.arguments = bundle

            return fragment

        }
    }
}