package com.example.vcontachim.adapter

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.vcontachim.R
import com.example.vcontachim.VcontachimApplication
import com.example.vcontachim.dalogs.AddBottomDialogDeleteVideo
import com.example.vcontachim.databinding.ItemVideoBinding
import com.example.vcontachim.models.ItemVideo
import java.util.*

class VideoAdapter(val videoListener: VideoListener) :
    RecyclerView.Adapter<VideoAdapter.VideoViewHolder>() {
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

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val itemVideo: ItemVideo = videoList[position]
        val sizes = itemVideo.image[0]

        holder.binding.bottomDialog.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                videoListener.onClick(itemVideo = itemVideo)
            }
        })

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

        holder.binding.videoDuration.text = "$hours:$minutes:$seconds"

        val formatter = SimpleDateFormat(/* pattern = */ "d MMMM yyyy")
        val dateString = formatter.format(Date(itemVideo.date * 1000))
        holder.binding.dateAdded.text = dateString

    }

    override fun getItemCount() = videoList.size

    interface VideoListener {
        fun onClick(itemVideo: ItemVideo)
    }
}