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
import com.example.vcontachim.Screens
import com.example.vcontachim.databinding.ItemPhotoAlbumsBinding
import com.example.vcontachim.VcontachimApplication
import com.example.vcontachim.models.ItemPhotoAlbums

class PhotoAlbumsAdapter :
    ListAdapter<ItemPhotoAlbums, PhotoAlbumsAdapter.PhotoAlbumsViewHolder>(PhotoAlbumsDiffCallback) {

    class PhotoAlbumsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding: ItemPhotoAlbumsBinding = ItemPhotoAlbumsBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoAlbumsViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)

        val itemView: View = layoutInflater.inflate(
            R.layout.item_photo_albums,
            parent,
            false
        )

        return PhotoAlbumsViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: PhotoAlbumsViewHolder, position: Int) {
        val photoAlbums: ItemPhotoAlbums = getItem(position)

        Glide.with(holder.itemView)
            .load(photoAlbums.thumbSrc)
            .into(holder.binding.imageViewPhotoAlbums)

        val followersCount: String = VcontachimApplication.context.resources.getQuantityString(
            R.plurals.number_of_photos,
            photoAlbums.size.toInt()
        )

        holder.binding.textViewNumberOfPhotos.text = "${photoAlbums.size} $followersCount"

        holder.binding.textViewName.text = photoAlbums.title

        holder.binding.linearLayoutPhotoAlbums.setOnClickListener {
            VcontachimApplication.router.navigateTo(Screens.photos(photoAlbums))
        }

    }

    object PhotoAlbumsDiffCallback : DiffUtil.ItemCallback<ItemPhotoAlbums>() {
        override fun areItemsTheSame(oldItem: ItemPhotoAlbums, newItem: ItemPhotoAlbums): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: ItemPhotoAlbums,
            newItem: ItemPhotoAlbums
        ): Boolean {
            return oldItem == newItem
        }
    }
}