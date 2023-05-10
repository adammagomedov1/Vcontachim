package com.example.vcontachim.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.vcontachim.R
import com.example.vcontachim.Screens
import com.example.vcontachim.databinding.ItemPhotosBinding
import com.example.vcontachim.fragment.VcontachimApplication
import com.example.vcontachim.models.ItemPhotos

class PhotosAdapter : RecyclerView.Adapter<PhotosAdapter.PhotosViewHolder>() {
    var photosList: List<ItemPhotos> = emptyList()

    class PhotosViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding: ItemPhotosBinding = ItemPhotosBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotosViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)

        val itemView: View = layoutInflater.inflate(
            R.layout.item_photos,
            parent,
            false
        )
        return PhotosViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PhotosViewHolder, position: Int) {
        val itemPhotos: ItemPhotos = photosList[position]
        val sizes = itemPhotos.sizes[0]

        holder.binding.linearLayoutPhotos.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                VcontachimApplication.router.navigateTo(Screens.photoActivity(itemPhotos))
            }
        })

        Glide.with(holder.itemView)
            .load(sizes.url)
            .into(holder.binding.imageViewPhotos)

    }

    override fun getItemCount() = photosList.size
}