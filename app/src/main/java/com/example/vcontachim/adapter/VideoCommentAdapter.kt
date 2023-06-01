package com.example.vcontachim.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.vcontachim.R
import com.example.vcontachim.databinding.ItemVideoCommentBinding
import com.example.vcontachim.models.VideoCommentUi
import java.text.SimpleDateFormat
import java.util.*

class VideoCommentAdapter :
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

        holder.binding.textView.text = videoCommentUi.text

        val formatter = SimpleDateFormat(/* pattern */ "d MMMM в H:m")
        val dateString = formatter.format(Date(videoCommentUi.date * 1000))
        holder.binding.textViewDate.text = dateString

        holder.binding.textViewName.text = "${videoCommentUi.firstName} ${videoCommentUi.lastName}"

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
}