package com.example.vcontachim.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.vcontachim.R
import com.example.vcontachim.adapter.PhotoAlbumsAdapter
import com.example.vcontachim.databinding.FragmentPhotoAlbumsBinding
import com.example.vcontachim.VcontachimApplication
import com.example.vcontachim.utility.showSnackbar
import com.example.vcontachim.viewmodel.PhotoAlbumsViewModel

class PhotoAlbumsFragment : Fragment(R.layout.fragment_photo_albums) {
    private var binding: FragmentPhotoAlbumsBinding? = null

    private val photoAlbumsAdapter = PhotoAlbumsAdapter()

    private val viewModel: PhotoAlbumsViewModel by lazy {
        ViewModelProvider(this)[PhotoAlbumsViewModel::class.java]
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPhotoAlbumsBinding.bind(view)

        binding!!.toolbar.setNavigationOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                VcontachimApplication.router.exit()
            }
        })

        binding!!.recyclerView.adapter = photoAlbumsAdapter

        viewModel.photoAlbumsLiveData.observe(viewLifecycleOwner) {
            photoAlbumsAdapter.submitList(it.response.items)
        }

        viewModel.progressBarLiveData.observe(viewLifecycleOwner, object : Observer<Boolean> {
            override fun onChanged(t: Boolean) {
                if (t == true) {
                    binding!!.progressBar.visibility = View.VISIBLE
                } else {
                    binding!!.progressBar.visibility = View.GONE
                }
            }
        })

        viewModel.errorLiveData.observe(viewLifecycleOwner) {
            showSnackbar(text = it)
        }
        viewModel.loadList()
    }
}