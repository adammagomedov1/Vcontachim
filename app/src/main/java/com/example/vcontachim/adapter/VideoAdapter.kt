package com.example.vcontachim.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.Group
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.vcontachim.R
import com.example.vcontachim.databinding.ItemVideoBinding
import com.example.vcontachim.fragment.VcontachimApplication
import com.example.vcontachim.models.ItemVideo
import com.example.vcontachim.models.ResponseVideo
import com.example.vcontachim.models.Video

class VideoAdapter : RecyclerView.Adapter<VideoAdapter.VideoViewHolder>() {
    var videoList: List<ItemVideo> = emptyList()

    class VideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding: ItemVideoBinding = ItemVideoBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)

        val itemView: View = layoutInflater.inflate(
            R.layout.item_video,
            parent,
            false
        )
        return VideoViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val itemVideo: ItemVideo = videoList[position]
        val sizes = itemVideo.image[0]

        val numberOfViews: String = VcontachimApplication.context.resources.getQuantityString(
            R.plurals.number_of_views,
            itemVideo.views.toInt()
        )

        Glide.with(holder.itemView)
            .load(sizes.url)
            .into(holder.binding.imageViewVideo)

        holder.binding.textViewName.text = itemVideo.title

        holder.binding.numberOfViews.text = "${itemVideo.views} $numberOfViews"

        val hours = itemVideo.duration / 3600
        val minutes = (itemVideo.duration % 3600) / 60
        val seconds = itemVideo.duration % 60

        val timeString = String.format("%02d:%02d:%02d", hours, minutes, seconds)
        holder.binding.sizeVideo.text = timeString

    }

    override fun getItemCount() = videoList.size
}