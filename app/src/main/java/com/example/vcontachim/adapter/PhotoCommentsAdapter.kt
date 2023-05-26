package com.example.vcontachim.adapter

import android.annotation.SuppressLint
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.vcontachim.R
import com.example.vcontachim.databinding.ItemPhotoCommentsBinding
import com.example.vcontachim.models.PhotoCommentsUi
import java.text.SimpleDateFormat
import java.util.*

class PhotoCommentsAdapter(private val likeListener: LikeListener) :
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

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    override fun onBindViewHolder(holder: PhotoCommentsViewHolder, position: Int) {
        var photoComments: PhotoCommentsUi = getItem(position)

        holder.binding.commentText.text = photoComments.textComments

        val formatter = SimpleDateFormat(/* pattern */ "d MMMM в H:m")
        val dateString = formatter.format(Date(photoComments.dateComments * 1000))
        holder.binding.commentPostDate.text = dateString

        holder.binding.familyName.text = "${photoComments.lastName} ${photoComments.firstName}"

        Glide.with(holder.itemView)
            .load(photoComments.photo)
            .into(holder.binding.shapeableImageViewAvatar)

        holder.binding.commentLike.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                likeListener.onClick(photoComments)

            }
        })

        if (photoComments.personOnline) {
            holder.binding.personOnline.setImageResource(R.drawable.online_composite_16)
        } else {
            holder.binding.personOnline.setImageResource(R.drawable.emptiness)
        }

        if (photoComments.userLikes == 1) {
            holder.binding.commentLike.setImageResource(R.drawable.like_filled_red_28)
            holder.binding.commentLike.setColorFilter(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.red
                ), PorterDuff.Mode.MULTIPLY
            )
        } else {
            holder.binding.commentLike.setImageResource(R.drawable.like_outline_24)
            holder.binding.commentLike.setColorFilter(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.grey
                ), PorterDuff.Mode.MULTIPLY
            )
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

    interface LikeListener {
        fun onClick(photoCommentsUi: PhotoCommentsUi)
    }
}