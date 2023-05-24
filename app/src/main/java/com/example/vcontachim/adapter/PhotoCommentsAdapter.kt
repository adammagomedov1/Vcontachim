package com.example.vcontachim.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.vcontachim.R
import com.example.vcontachim.databinding.ItemPhotoCommentsBinding
import com.example.vcontachim.models.ItemPhotoComments
import com.example.vcontachim.models.PhotoCommentsUi
import com.example.vcontachim.models.Profile
import java.text.SimpleDateFormat
import java.util.*

class PhotoCommentsAdapter :
    androidx.recyclerview.widget.ListAdapter<PhotoCommentsUi, PhotoCommentsAdapter.PhotoCommentsViewHolder>(
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
        val photoComments: PhotoCommentsUi = getItem(position)

        holder.binding.commentText.text = photoComments.textComments

        val formatter = SimpleDateFormat(/* pattern */ "d MMMM в H:m")
        val dateString = formatter.format(Date(photoComments.dateComments * 1000))
        holder.binding.commentPostDate.text = dateString

        holder.binding.familyName.text = "${photoComments.lastName} ${photoComments.firstName}"

        Glide.with(holder.itemView)
            .load(photoComments.photo)
            .into(holder.binding.shapeableImageViewAvatar)
    }

    object PhotoCommentsDiffCallback : DiffUtil.ItemCallback<PhotoCommentsUi>() {
        override fun areItemsTheSame(
            oldItem: PhotoCommentsUi,
            newItem: PhotoCommentsUi
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: PhotoCommentsUi,
            newItem: PhotoCommentsUi
        ): Boolean {
            return oldItem == newItem
        }
    }
}