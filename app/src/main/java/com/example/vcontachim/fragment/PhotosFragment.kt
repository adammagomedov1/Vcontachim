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
import com.example.vcontachim.VcontachimApplication
import com.example.vcontachim.utility.showSnackbar
import com.example.vcontachim.viewmodel.PhotosViewModels

class PhotosFragment : Fragment(R.layout.fragment_photos) {
    private var binding: FragmentPhotosBinding? = null

    private val photosAdapter = PhotosAdapter()

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

        binding!!.recyclerViewPhotos.adapter = photosAdapter

        viewModel.photosLiveData.observe(viewLifecycleOwner) {
            photosAdapter.submitList(it.response.items)
        }

        val takeALongId = requireArguments().getLong(SAVE_PHOTO_KEY)
        val takeStringName = requireArguments().getString(SAVE_NAME_KEY)
        val findOutTheNumberOfPhotos = requireArguments().getLong(NUMBER_OF_PHOTOS_KEY)

        val numberOfPhotos: String = VcontachimApplication.context.resources.getQuantityString(
            R.plurals.number_of_photos,
            findOutTheNumberOfPhotos.toInt()
        )

        binding!!.toolbarPhotos.title = takeStringName

        binding!!.toolbarPhotos.subtitle = "$findOutTheNumberOfPhotos $numberOfPhotos"

        viewModel.progressBarLiveData.observe(viewLifecycleOwner, object : Observer<Boolean> {
            override fun onChanged(value: Boolean) {
                if (value) {
                    binding!!.progressBarPhotos.visibility = View.VISIBLE
                } else {
                    binding!!.progressBarPhotos.visibility = View.GONE
                }
            }
        })

        viewModel.errorLiveData.observe(viewLifecycleOwner) {
            showSnackbar(text = it)
        }
        viewModel.loadList(takeALongId)
    }

    companion object {
        private const val SAVE_PHOTO_KEY = "photo"
        private const val SAVE_NAME_KEY = "name"
        private const val NUMBER_OF_PHOTOS_KEY = "size"

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