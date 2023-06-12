package com.example.vcontachim.fragment

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.vcontachim.R
import com.example.vcontachim.VcontachimApplication
import com.example.vcontachim.adapter.VideoAdapter
import com.example.vcontachim.dalogs.VideoMenuBottomSheetDialog
import com.example.vcontachim.databinding.FragmentVideoBinding
import com.example.vcontachim.models.ItemVideo
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

        val videoAdapter = VideoAdapter(videoListener = object : VideoAdapter.VideoListener {
            override fun onClick(itemVideo: ItemVideo) {
                val addBottomDialogDeleteVideo =
                    VideoMenuBottomSheetDialog(
                        itemVideo = itemVideo,
                        context = view.context,
                        videoListener = object : VideoMenuBottomSheetDialog.VideoListener {
                            override fun onVideoDelete(video: ItemVideo) {
                                viewModel.deleteVideo(itemVideo = video)
                            }

                            override fun copyVideoLink(itemVideo: ItemVideo) {
                                val clipboard =
                                    view.context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                val clip = ClipData.newPlainText("label", itemVideo.player)
                                clipboard.setPrimaryClip(clip)

                                val toast: Toast = Toast.makeText(
                                    context,
                                    R.string.link_copied,
                                    Toast.LENGTH_LONG
                                )
                                toast.show()
                            }
                        })
                addBottomDialogDeleteVideo.show()
            }
        })

        binding!!.recyclerView.adapter = videoAdapter

        viewModel.videoLiveData.observe(viewLifecycleOwner) {
            videoAdapter.submitList(it.response.items)
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

        viewModel.videoDeleteLiveData.observe(viewLifecycleOwner) {
            val itemVideoDelete = videoAdapter.currentList.toMutableList()
            itemVideoDelete.remove(it)
            videoAdapter.submitList(itemVideoDelete)
        }

        viewModel.loadVideo()

    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}