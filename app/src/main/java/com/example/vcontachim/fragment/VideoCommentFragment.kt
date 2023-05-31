package com.example.vcontachim.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.vcontachim.R
import com.example.vcontachim.databinding.FragmentVideoCommentBinding
import com.example.vcontachim.models.ItemVideo
import com.example.vcontachim.viewmodel.VideoCommentViewModel

class VideoCommentFragment : Fragment(R.layout.fragment_video_comment) {
    private var binding: FragmentVideoCommentBinding? = null

    val viewModel: VideoCommentViewModel by lazy {
        ViewModelProvider(this)[VideoCommentViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentVideoCommentBinding.bind(view)

        val videoSerializable = requireArguments().getSerializable(SAVE_VIDEO_KEY)
        val itemVideo: ItemVideo = videoSerializable as ItemVideo

        viewModel.videoCommentUiLiveData.observe(viewLifecycleOwner) {

        }

        viewModel.errorLiveData.observe(viewLifecycleOwner) {
            val toast = Toast.makeText(
                requireContext(),
                it,
                Toast.LENGTH_LONG
            )
            toast.show()
        }

        viewModel.loadVideoComment(itemVideo)
    }

    companion object {
        private const val SAVE_VIDEO_KEY = "video"

        fun createFragment(itemVideo: ItemVideo): Fragment {
            val fragment = VideoCommentFragment()
            val bundle = Bundle()
            bundle.putSerializable(SAVE_VIDEO_KEY, itemVideo)
            fragment.arguments = bundle

            return fragment
        }
    }
}