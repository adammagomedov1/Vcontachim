package com.example.vcontachim.fragment

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.vcontachim.R
import com.example.vcontachim.VcontachimApplication
import com.example.vcontachim.databinding.FragmentVideoPlaybackBinding
import com.example.vcontachim.models.ItemVideo
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.dash.DashMediaSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import java.util.*

class VideoPlaybackFragment : Fragment(R.layout.fragment_video_playback) {
    private var binding: FragmentVideoPlaybackBinding? = null

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
        val itemVideo: ItemVideo = videoSerializable as ItemVideo

        binding!!.textViewName.text = itemVideo.title

        binding!!.numberOfLikes.text = itemVideo.likes.likes.toString()

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
        player?.playWhenReady
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