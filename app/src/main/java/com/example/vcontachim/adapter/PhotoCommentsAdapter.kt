package com.example.vcontachim.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.vcontachim.R
import com.example.vcontachim.databinding.ItemPhotoCommentsBinding
import com.example.vcontachim.models.PhotoCommentsUi
import java.text.SimpleDateFormat
import java.util.*

class PhotoCommentsAdapter :
    ListAdapter<PhotoCommentsUi, PhotoCommentsAdapter.PhotoCommentsViewHolder>(
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

        if (photoComments.personOnline) {
            holder.binding.personOnline.setImageResource(R.drawable.online_composite_16)
        } else {
            holder.binding.personOnline.setImageResource(R.drawable.emptiness)
        }
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