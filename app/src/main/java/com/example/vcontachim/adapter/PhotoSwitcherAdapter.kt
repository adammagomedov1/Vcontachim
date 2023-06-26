package com.example.vcontachim.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.vcontachim.R
import com.example.vcontachim.databinding.ItemHomeBinding
import com.example.vcontachim.databinding.ItemPhotoSwitcherBinding
import com.example.vcontachim.models.Attachment
import com.example.vcontachim.models.NewsUi

class PhotoSwitcherAdapter :
    ListAdapter<Attachment, PhotoSwitcherAdapter.PhotoSwitcherViewHolder>(PhotoSwitcherDiffCallback) {

    class PhotoSwitcherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding: ItemPhotoSwitcherBinding = ItemPhotoSwitcherBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoSwitcherViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)

        val itemView: View = layoutInflater.inflate(
            R.layout.item_photo_switcher,
            parent,
            false
        )

        return PhotoSwitcherViewHolder(itemView = itemView)
    }

    override fun onBindViewHolder(holder: PhotoSwitcherViewHolder, position: Int) {
        val newsUi: Attachment = getItem(position)

        Glide.with(holder.itemView)
            .load(newsUi.photo?.sizes?.lastOrNull()?.url)
            .into(holder.binding.imageViewPhoto)
    }

    object PhotoSwitcherDiffCallback : DiffUtil.ItemCallback<Attachment>() {
        override fun areItemsTheSame(oldItem: Attachment, newItem: Attachment): Boolean {
            return oldItem.type == newItem.type
        }

        override fun areContentsTheSame(oldItem: Attachment, newItem: Attachment): Boolean {
            return oldItem == newItem
        }
    }
}