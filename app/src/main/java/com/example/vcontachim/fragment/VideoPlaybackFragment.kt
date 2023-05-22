package com.example.vcontachim.fragment

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.vcontachim.R
import com.example.vcontachim.VcontachimApplication
import com.example.vcontachim.databinding.FragmentVideoPlaybackBinding
import com.example.vcontachim.models.ItemVideo
import com.example.vcontachim.viewmodel.VideoPlaybackViewModel
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.dash.DashMediaSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.material.snackbar.Snackbar
import java.util.*

class VideoPlaybackFragment : Fragment(R.layout.fragment_video_playback) {
    private var binding: FragmentVideoPlaybackBinding? = null

    private val videoModel: VideoPlaybackViewModel by lazy {
        ViewModelProvider(this)[VideoPlaybackViewModel::class.java]
    }

    private var player: ExoPlayer? = null
    private var playbackPosition = 0L
    private var playWhenReady = true

    @SuppressLint("NewApi")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentVideoPlaybackBinding.bind(view)

        binding!!.toolbarBack.setNavigationOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                VcontachimApplication.router.exit()
            }
        })

        val videoSerializable = requireArguments().getSerializable(SAVE_VIDEO_KEY)
        var itemVideo: ItemVideo = videoSerializable as ItemVideo

        binding!!.textViewName.text = itemVideo.title

        binding!!.numberOfLikes.text = itemVideo.likes.countLikes.toString()

        binding!!.numberOfComments.text = itemVideo.comments.toString()

        binding!!.numberOfReposts.text = itemVideo.reposts.reposted.toString()

        val numberOfViews: String = VcontachimApplication.context.resources.getQuantityString(
            R.plurals.number_of_views,
            itemVideo.views.toInt()
        )

        binding!!.numberOfViews.text = "${itemVideo.views} $numberOfViews"

        val formatter = SimpleDateFormat(/* pattern = */ "d MMMM yyyy")
        val dateString = formatter.format(Date(itemVideo.date * 1000))
        binding!!.textViewDate.text = dateString

        if (itemVideo.likes.userLikes > 0) {
            binding!!.imageViewLike.setImageResource(R.drawable.like_filled_red_28)
            binding!!.imageViewLike.setColorFilter(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.red
                ), android.graphics.PorterDuff.Mode.MULTIPLY
            )
        }

        binding!!.likeButtonForVideo.setOnClickListener(object : View.OnClickListener {
            @SuppressLint("SetTextI18n")
            override fun onClick(v: View?) {
                if (itemVideo.likes.userLikes < 1) {
                    videoModel.likeVideo(itemVideo)
                } else {
                    videoModel.deleteLike(itemVideo)
                }
            }
        })

        videoModel.errorLiveData.observe(viewLifecycleOwner) {
            val toast = Toast.makeText(
                requireContext(),
                "У вас проблемы, хотите об этом поговорить",
                Toast.LENGTH_LONG
            )
            toast.show()
        }

        videoModel.videoLikesViewData.observe(viewLifecycleOwner) {
            itemVideo = itemVideo.copy(
                likes = itemVideo.likes.copy(
                    userLikes = if (itemVideo.likes.userLikes == 1) {
                        0
                    } else {
                        1
                    },
                    countLikes = it.response.likes
                )
            )

            binding!!.numberOfLikes.text = "${itemVideo.likes.countLikes}"

            if (itemVideo.likes.userLikes < 1) {
                binding!!.imageViewLike.setImageResource(R.drawable.like_outline_24)
                binding!!.imageViewLike.setColorFilter(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.grey
                    ), android.graphics.PorterDuff.Mode.MULTIPLY
                )
            } else {
                binding!!.imageViewLike.setImageResource(R.drawable.like_filled_red_28)
                binding!!.imageViewLike.setColorFilter(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.red
                    ), android.graphics.PorterDuff.Mode.MULTIPLY
                )
            }
        }

        preparePlayer(itemVideo)
    }

    private fun preparePlayer(itemVideo: ItemVideo) {
        player = ExoPlayer.Builder(requireContext()).build()
        player?.playWhenReady = true
        binding!!.videoView.player = player
        val defaultHttpDataSource = DefaultHttpDataSource.Factory()
        val mediaItem =
            MediaItem.fromUri(itemVideo.player)
        val mediaSource =
            DashMediaSource.Factory(defaultHttpDataSource).createMediaSource(mediaItem)
        player?.setMediaSource(mediaSource)
        player?.seekTo(playbackPosition)
        player?.prepare()
    }

    private fun releasePlayer() {
        player?.let {
            playbackPosition = it.currentPosition
            playWhenReady = it.playWhenReady
            it.release()
            player = null
        }
    }

    override fun onPause() {
        super.onPause()
        releasePlayer()
    }

    companion object {
        private const val SAVE_VIDEO_KEY = "video"

        fun createFragment(itemVideo: ItemVideo): Fragment {
            val fragment = VideoPlaybackFragment()
            val bundle = Bundle()
            bundle.putSerializable(SAVE_VIDEO_KEY, itemVideo)
            fragment.arguments = bundle

            return fragment
        }
    }
}