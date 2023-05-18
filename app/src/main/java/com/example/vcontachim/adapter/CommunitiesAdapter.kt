package com.example.vcontachim.adapter

import android.annotation.SuppressLint
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.Resource
import com.example.vcontachim.R
import com.example.vcontachim.databinding.ItemCommunitiesBinding
import com.example.vcontachim.models.Communities
import com.example.vcontachim.models.ItemCommunities
import com.example.vcontachim.models.ResponseLikes

class CommunitiesAdapter :
    ListAdapter<ItemCommunities, CommunitiesAdapter.CommunitiesViewHolder>(CommunitiesDiffCallback) {

    class CommunitiesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding: ItemCommunitiesBinding = ItemCommunitiesBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommunitiesViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)

        val itemView: View = layoutInflater.inflate(
            R.layout.item_communities,
            parent,
            false
        )
        return CommunitiesViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CommunitiesViewHolder, position: Int) {
        val communitie: ItemCommunities = getItem(position)

        Glide.with(holder.itemView)
            .load(communitie.photo50)
            .into(holder.binding.imageView)

        holder.binding.communityName.setText(communitie.screenName)
        holder.binding.numberOfParticipants.setText("${communitie.membersCount} участников")

        if (communitie.verified == 1L) {
            holder.binding.imageViewVerified.visibility = View.VISIBLE
        } else {
            holder.binding.imageViewVerified.visibility = View.GONE
        }
    }

    object CommunitiesDiffCallback : DiffUtil.ItemCallback<ItemCommunities>() {
        override fun areItemsTheSame(
            oldItem: ItemCommunities,
            newItem: ItemCommunities
        ): Boolean {
            return oldItem.membersCount == newItem.membersCount
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(
            oldItem: ItemCommunities,
            newItem: ItemCommunities
        ): Boolean {
            return oldItem == newItem
        }
    }
}