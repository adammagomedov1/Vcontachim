package com.example.vcontachim.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.vcontachim.R
import com.example.vcontachim.adapter.VideoAdapter
import com.example.vcontachim.databinding.FragmentVideoBinding
import com.example.vcontachim.VcontachimApplication
import com.example.vcontachim.viewmodel.VideoViewModel
import com.google.android.material.snackbar.Snackbar

class VideoFragment : Fragment(R.layout.fragment_video) {
    private var binding: FragmentVideoBinding? = null

    private val viewModel: VideoViewModel by lazy {
        ViewModelProvider(this)[VideoViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentVideoBinding.bind(view)

        binding!!.toolbar.setNavigationOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                VcontachimApplication.router.exit()
            }
        })

        val videoAdapter = VideoAdapter()
        binding!!.recyclerView.adapter = videoAdapter

        viewModel.videoLiveData.observe(viewLifecycleOwner) {
            videoAdapter.videoList = it.response.items
            videoAdapter.notifyDataSetChanged()
        }

        viewModel.progressBarLiveData.observe(
            viewLifecycleOwner
        ) { t ->
            if (t == true) {
                binding!!.progressBar.visibility = View.VISIBLE
            } else {
                binding!!.progressBar.visibility = View.GONE
            }
        }

        viewModel.errorLiveData.observe(viewLifecycleOwner) {
            val snackbar: Snackbar = Snackbar.make(
                requireView(),
                it,
                Snackbar.LENGTH_LONG
            )
            snackbar.show()
        }
        viewModel.loadVideo()
    }
}