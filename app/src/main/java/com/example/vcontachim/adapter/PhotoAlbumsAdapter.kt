package com.example.vcontachim.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.vcontachim.R
import com.example.vcontachim.databinding.ItemPhotoAlbumsBinding
import com.example.vcontachim.models.ItemPhotoAlbums

class PhotoAlbumsAdapter : RecyclerView.Adapter<PhotoAlbumsAdapter.PhotoAlbumsViewHolder>() {
    var photoAlbumsList: List<ItemPhotoAlbums> = emptyList()

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
        val photoAlbums: ItemPhotoAlbums = photoAlbumsList[position]

        Glide.with(holder.itemView)
            .load(photoAlbums.thumbSrc)
            .into(holder.binding.imageViewPhotoAlbums)

        if (photoAlbums.size == 0L) {
            holder.binding.textViewNumberOfPhotos.text = "${photoAlbums.size} фотографий нет"
        } else if (photoAlbums.size == 1L) {
            holder.binding.textViewNumberOfPhotos.text = "${photoAlbums.size} фотография"
        } else if (photoAlbums.size >= 5L) {
            holder.binding.textViewNumberOfPhotos.text = "${photoAlbums.size} фотографий"
        } else {
            holder.binding.textViewNumberOfPhotos.text = "${photoAlbums.size} фотографии"
        }
        holder.binding.textViewName.text = photoAlbums.title
    }

    override fun getItemCount() = photoAlbumsList.size

}