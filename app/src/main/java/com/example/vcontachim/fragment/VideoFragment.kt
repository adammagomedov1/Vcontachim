package com.example.vcontachim.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.vcontachim.R
import com.example.vcontachim.adapter.VideoAdapter
import com.example.vcontachim.databinding.FragmentVideoBinding
import com.example.vcontachim.VcontachimApplication
import com.example.vcontachim.dalogs.AddBottomDialogDeleteVideo
import com.example.vcontachim.models.ItemPhotos
import com.example.vcontachim.models.ItemVideo
import com.example.vcontachim.viewmodel.VideoViewModel
import com.google.android.material.snackbar.Snackbar

class VideoFragment : Fragment(R.layout.fragment_video) {
    private var binding: FragmentVideoBinding? = null

    private val viewModel: VideoViewModel by lazy {
        ViewModelProvider(this)[VideoViewModel::class.java]
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentVideoBinding.bind(view)

        binding!!.toolbar.setNavigationOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                VcontachimApplication.router.exit()
            }
        })

        val videoAdapter = VideoAdapter(videoListener = object : VideoAdapter.VideoListener {
            override fun onClick(itemVideo: ItemVideo) {
                        val addBottomDialogDeleteVideo =
                            AddBottomDialogDeleteVideo(itemVideo = itemVideo,
                                context = view.context,
                                addVideoListener = object : AddBottomDialogDeleteVideo.AddVideoListener {
                                    override fun onVideoDelete(video: ItemVideo) {

                                        viewModel.loadDeleteVideo(itemVideo = video.id)
                                    }
                                })
                        addBottomDialogDeleteVideo.show()
            }
        })

        binding!!.recyclerView.adapter = videoAdapter

        viewModel.deleteVideoLiveData.observe(viewLifecycleOwner) {

        }

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
    companion object {
        private const val SAVE_PHOTOS_KEY = "photos"

        fun createFragment(photos: ItemVideo): Fragment {
            val fragment = VideoFragment()
            val bundle = Bundle()
            bundle.putSerializable(SAVE_PHOTOS_KEY, photos)
            fragment.arguments = bundle

            return fragment
        }
    }
}