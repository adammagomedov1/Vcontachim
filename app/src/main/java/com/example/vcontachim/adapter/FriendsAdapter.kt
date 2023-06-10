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
import com.example.vcontachim.VcontachimApplication
import com.example.vcontachim.databinding.ItemFriendsBinding
import com.example.vcontachim.models.Friends
import com.example.vcontachim.models.Item

class FriendsAdapter : ListAdapter<Item, FriendsAdapter.FriendsViewHolder>(FriendsDiffCallback) {

    class FriendsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding: ItemFriendsBinding = ItemFriendsBinding.bind(itemView)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FriendsViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)

        val itemView: View = layoutInflater.inflate(
            R.layout.item_friends,
            parent,
            false
        )

        return FriendsViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: FriendsViewHolder, position: Int) {
        val friend: Item = getItem(position)

        holder.binding.friends.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                VcontachimApplication.router.navigateTo(Screens.profileDetails(friend.id))
            }
        })

        Glide.with(holder.itemView)
            .load(friend.photo200)
            .into(holder.binding.imageView)

        holder.binding.textView.text = "${friend.firstName} ${friend.lastName}"

    }

    object FriendsDiffCallback : DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.lastName == newItem.lastName
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem == newItem
        }
    }
}