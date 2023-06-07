package com.example.vcontachim.adapter

import android.annotation.SuppressLint
import android.graphics.ColorFilter
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.vcontachim.R
import com.example.vcontachim.Screens
import com.example.vcontachim.VcontachimApplication
import com.example.vcontachim.databinding.ItemVideoCommentBinding
import com.example.vcontachim.models.VideoCommentUi
import java.text.SimpleDateFormat
import java.util.*

class VideoCommentAdapter(val videoListener: VideoListener) :
    ListAdapter<VideoCommentUi, VideoCommentAdapter.VideoCommentViewHolder>(
        VideoCommentDiffCallback
    ) {

    class VideoCommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding: ItemVideoCommentBinding = ItemVideoCommentBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoCommentViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        val itemView: View = layoutInflater.inflate(
            R.layout.item_video_comment,
            parent,
            false
        )

        return VideoCommentViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: VideoCommentViewHolder, position: Int) {
        val videoCommentUi: VideoCommentUi = getItem(position)

        holder.binding.videoComment.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                VcontachimApplication.router.navigateTo(
                    Screens.infoProfileCommentVideo(
                        videoCommentUi
                    )
                )
            }
        })

        holder.binding.imageViewLike.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                videoListener.onClick(videoCommentUi)
            }
        })

        holder.binding.textView.text = videoCommentUi.text

        val formatter = SimpleDateFormat(/* pattern */ "d MMMM в H:m")
        val dateString = formatter.format(Date(videoCommentUi.date * 1000))
        holder.binding.textViewDate.text = dateString

        holder.binding.textViewName.text = "${videoCommentUi.firstName} ${videoCommentUi.lastName}"

        if (videoCommentUi.userLikes == 1) {
            holder.binding.imageViewLike.setImageResource(R.drawable.like_filled_red_28)
            holder.binding.imageViewLike.setColorFilter(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.red
                ), PorterDuff.Mode.MULTIPLY
            )
        } else {
            holder.binding.imageViewLike.setImageResource(R.drawable.like_outline_24)
            holder.binding.imageViewLike.setColorFilter(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.grey
                ), PorterDuff.Mode.MULTIPLY
            )
        }
    }

    object VideoCommentDiffCallback : DiffUtil.ItemCallback<VideoCommentUi>() {
        override fun areItemsTheSame(oldItem: VideoCommentUi, newItem: VideoCommentUi): Boolean {
            return oldItem.idProfile == newItem.idProfile
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: VideoCommentUi, newItem: VideoCommentUi): Boolean {
            return oldItem == newItem
        }
    }

    interface VideoListener {
        fun onClick(videoCommentUi: VideoCommentUi)
    }
}