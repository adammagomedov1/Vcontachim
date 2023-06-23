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
import com.zhpan.indicator.enums.IndicatorSlideMode
import com.zhpan.indicator.enums.IndicatorStyle
import java.util.*

class NewsAdapter(private val likeListener: LikeListener) :
    ListAdapter<NewsUi, NewsAdapter.HomeViewHolder>(MainScreenDiffCallback) {

    class HomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding: ItemHomeBinding = ItemHomeBinding.bind(itemView)

        val photoSwitcherAdapter = PhotoSwitcherAdapter()

        init {
            binding.viewPager2.adapter = photoSwitcherAdapter
            binding.indicator.setSliderColor(R.color.white, R.color.blue)
            binding.indicator.setSlideMode(IndicatorSlideMode.WORM)
            binding.indicator.setIndicatorStyle(IndicatorStyle.CIRCLE)
        }
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

    @SuppressLint(
        "NewApi", "SimpleDateFormat", "ResourceAsColor",
        "UseCompatLoadingForColorStateLists"
    )
    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val newsUi: NewsUi = getItem(position)

        holder.photoSwitcherAdapter.submitList(newsUi.attachments)

        holder.binding.indicator.setupWithViewPager(holder.binding.viewPager2)

        holder.binding.buttonNumberOfLikes.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                likeListener.onClick(newsUi)
            }
        })

        Glide.with(holder.itemView)
            .load(newsUi.photo200)
            .into(holder.binding.imageViewAvatar)

        holder.binding.textViewName.text = newsUi.name

        holder.binding.textViewNumberViews.text = "${newsUi.countViews}"

        holder.binding.buttonNumberOfComments.text = "${newsUi.countComment}"

        holder.binding.buttonNumberOfLikes.text = "${newsUi.countLike}"

        holder.binding.buttonNumberOfReposts.text = "${newsUi.countReposts}"

        val formatter = SimpleDateFormat(/* pattern = */ "d MMMM yyyy")
        val dateString = formatter.format(Date(newsUi.date * 1000))
        holder.binding.textViewDate.text = dateString

        if (newsUi.userLikes == 1L) {
            holder.binding.imageView.setImageResource(R.drawable.group_16)
            holder.binding.buttonNumberOfLikes.setIconTintResource(R.color.white)
            holder.binding.buttonNumberOfLikes.setTextColor(
                holder.itemView.context.resources.getColor(
                    R.color.pink
                )
            )
            holder.binding.buttonNumberOfLikes.backgroundTintList =
                holder.itemView.context.resources.getColorStateList(R.color.pink15)
        } else {
            holder.binding.buttonNumberOfLikes.setIconResource(R.drawable.like_outline_24)
            holder.binding.buttonNumberOfLikes.setIconTintResource(R.color.grey2)
            holder.binding.buttonNumberOfLikes.setTextColor(
                holder.itemView.context.resources.getColor(
                    R.color.grey2
                )
            )
            holder.binding.buttonNumberOfLikes.backgroundTintList =
                holder.itemView.context.resources.getColorStateList(R.color.grey5)
        }
    }

    object MainScreenDiffCallback : DiffUtil.ItemCallback<NewsUi>() {
        override fun areItemsTheSame(oldItem: NewsUi, newItem: NewsUi): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: NewsUi, newItem: NewsUi): Boolean {
            return oldItem == newItem
        }
    }

    interface LikeListener {
        fun onClick(newsUi: NewsUi)
    }
}