package com.example.vcontachim.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.vcontachim.R
import com.example.vcontachim.databinding.ItemPhotoCommentsBinding
import com.example.vcontachim.models.ItemPhotoComments
import com.example.vcontachim.models.Profile

class PhotoCommentsAdapter :
    androidx.recyclerview.widget.ListAdapter<ItemPhotoComments, PhotoCommentsAdapter.PhotoCommentsViewHolder>(
        PhotoCommentsDiffCallback
    ) {

    class PhotoCommentsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding: ItemPhotoCommentsBinding = ItemPhotoCommentsBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoCommentsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        val itemView: View = layoutInflater.inflate(
            R.layout.item_photo_comments,
            parent,
            false
        )
        return PhotoCommentsViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: PhotoCommentsViewHolder, position: Int) {
        val photoComments: ItemPhotoComments = getItem(position)

        holder.binding.commentText.text = photoComments.textComments

    }

    object PhotoCommentsDiffCallback : DiffUtil.ItemCallback<ItemPhotoComments>() {
        override fun areItemsTheSame(
            oldItem: ItemPhotoComments,
            newItem: ItemPhotoComments
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ItemPhotoComments,
            newItem: ItemPhotoComments
        ): Boolean {
            return oldItem == newItem
        }
    }
}