package com.example.vcontachim.fragment

import android.content.Context
import android.inputmethodservice.InputMethodService
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.vcontachim.R
import com.example.vcontachim.VcontachimApplication
import com.example.vcontachim.adapter.VideoCommentAdapter
import com.example.vcontachim.databinding.FragmentVideoCommentBinding
import com.example.vcontachim.models.ItemVideo
import com.example.vcontachim.models.VideoCommentUi
import com.example.vcontachim.utility.HideKeyboard
import com.example.vcontachim.viewmodel.VideoCommentViewModel

class VideoCommentFragment : Fragment(R.layout.fragment_video_comment) {
    private var binding: FragmentVideoCommentBinding? = null

    val viewModel: VideoCommentViewModel by lazy {
        ViewModelProvider(this)[VideoCommentViewModel::class.java]
    }

    private var videoCommentAdapter =
        VideoCommentAdapter(videoListener = object : VideoCommentAdapter.VideoListener {
            override fun onClick(videoCommentUi: VideoCommentUi) {
                if (videoCommentUi.userLikes == 0) {
                    viewModel.addLikes(videoCommentUi)
                } else {
                    viewModel.deleteLikes(videoCommentUi)
                }
            }
        })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentVideoCommentBinding.bind(view)

        val videoSerializable = requireArguments().getSerializable(SAVE_VIDEO_KEY)
        val itemVideo: ItemVideo = videoSerializable as ItemVideo

        binding!!.editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (s!!.toString() == "") {
                    binding!!.imageView.setImageResource(R.drawable.send_28__3_)
                } else {
                    binding!!.imageView.setImageResource(R.drawable.send_28__2_)
                    binding!!.imageView.setOnClickListener(object : View.OnClickListener {
                        override fun onClick(v: View?) {
                            viewModel.createCommentVideo(
                                itemVideo = itemVideo,
                                message = s.toString()
                            )
                            s.clear()

                            HideKeyboard.hideKeyboard(view = view)

                            viewModel.addCommentLiveData.observe(viewLifecycleOwner) {
                                val toast = Toast.makeText(
                                    requireContext(),
                                    R.string.сomment_added,
                                    Toast.LENGTH_LONG
                                )
                                toast.show()
                            }
                        }
                    })
                }
            }
        })

        binding!!.toolbarBack.subtitle = "${itemVideo.comments}"

        binding!!.toolbarBack.setNavigationOnClickListener { VcontachimApplication.router.exit() }

        binding!!.recyclerViewVideoComment.adapter = videoCommentAdapter

        viewModel.videoCommentUiLiveData.observe(viewLifecycleOwner) {
            videoCommentAdapter.submitList(it)
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