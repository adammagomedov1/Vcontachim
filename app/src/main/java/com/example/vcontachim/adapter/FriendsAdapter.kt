package com.example.vcontachim.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.vcontachim.R
import com.example.vcontachim.databinding.ItemFriendsBinding
import com.example.vcontachim.models.Item

class FriendsAdapter : RecyclerView.Adapter<FriendsAdapter.FriendsViewHolder>() {
    var fieldsList: List<Item> = emptyList()

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
        val friend: Item = fieldsList[position]

        Glide.with(holder.itemView)
            .load(friend.photo200)
            .into(holder.binding.imageView)

        holder.binding.textView.text = "${friend.firstName} ${friend.lastName}"

    }

    override fun getItemCount() = fieldsList.size

}