package com.example.vcontachim.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.vcontachim.R
import com.example.vcontachim.databinding.ItemVideoCommentBinding
import com.example.vcontachim.models.VideoCommentUi

class VideoCommentAdapter :
    androidx.recyclerview.widget.ListAdapter<VideoCommentUi, VideoCommentAdapter.VideoCommentHolder>(
        VideoCommentDiffCallback
    ) {

    class VideoCommentHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding: ItemVideoCommentBinding = ItemVideoCommentBinding.bind(itemView)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoCommentHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)

        val itemView: View = layoutInflater.inflate(
            R.layout.item_video_comment,
            parent,
            false
        )

        return VideoCommentHolder(itemView)
    }

    override fun onBindViewHolder(holder: VideoCommentHolder, position: Int) {
        val videoCommentUi: VideoCommentUi = getItem(position)

    }

    object VideoCommentDiffCallback : DiffUtil.ItemCallback<VideoCommentUi>() {
        override fun areItemsTheSame(oldItem: VideoCommentUi, newItem: VideoCommentUi): Boolean {
            return oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: VideoCommentUi, newItem: VideoCommentUi): Boolean {
            return oldItem == newItem
        }
    }
}