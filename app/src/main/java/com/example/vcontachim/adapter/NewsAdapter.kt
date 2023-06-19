package com.example.vcontachim.adapter

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.vcontachim.R
import com.example.vcontachim.databinding.ItemHomeBinding
import com.example.vcontachim.models.NewsUi
import java.util.*

class NewsAdapter : ListAdapter<NewsUi, NewsAdapter.HomeViewHolder>(MainScreenDiffCallback) {

    class HomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding: ItemHomeBinding = ItemHomeBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)

        val itemView: View = layoutInflater.inflate(
            R.layout.item_home,
            parent,
            false
        )
        return HomeViewHolder(itemView)
    }

    @SuppressLint("NewApi", "SimpleDateFormat")
    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val newsUi: NewsUi = getItem(position)

        Glide.with(holder.itemView)
            .load(newsUi.photo200)
            .into(holder.binding.imageViewAvatar)

        Glide.with(holder.itemView)
            .load(newsUi.url)
            .into(holder.binding.imageViewPhoto)

        holder.binding.textViewName.text = newsUi.name

        holder.binding.textViewNumberViews.text = "${newsUi.countViews}"

        holder.binding.buttonNumberOfComments.text = "${newsUi.countComment}"

        holder.binding.buttonNumberOfLikes.text = "${newsUi.countLike}"

        holder.binding.buttonNumberOfReposts.text = "${newsUi.countReposts}"

        val formatter = SimpleDateFormat(/* pattern = */ "d MMMM yyyy")
        val dateString = formatter.format(Date(newsUi.date * 1000))
        holder.binding.textViewDate.text = dateString

    }

    object MainScreenDiffCallback : DiffUtil.ItemCallback<NewsUi>() {
        override fun areItemsTheSame(oldItem: NewsUi, newItem: NewsUi): Boolean {
            return oldItem.sourceId == newItem.sourceId
        }

        override fun areContentsTheSame(oldItem: NewsUi, newItem: NewsUi): Boolean {
            return oldItem == newItem
        }
    }
}