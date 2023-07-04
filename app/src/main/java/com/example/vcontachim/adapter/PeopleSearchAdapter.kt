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
import com.example.vcontachim.databinding.ItemPeopleSearchBinding
import com.example.vcontachim.models.PeopleSearchUi

class PeopleSearchAdapter :
    ListAdapter<PeopleSearchUi, PeopleSearchAdapter.PeopleSearchViewHolder>(
        PeopleSearchDiffCallback
    ) {

    class PeopleSearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding: ItemPeopleSearchBinding = ItemPeopleSearchBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeopleSearchViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)

        val itemView: View = layoutInflater.inflate(
            R.layout.item_people_search,
            parent,
            false
        )
        return PeopleSearchViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: PeopleSearchViewHolder, position: Int) {
        val peopleSearchUi: PeopleSearchUi = getItem(position)

        Glide.with(holder.itemView)
            .load(peopleSearchUi.maxPhoto)
            .into(holder.binding.imageViewAvatar)

        holder.binding.textViewName.text = peopleSearchUi.firstName

        if (peopleSearchUi.isFriend == 0) {
            holder.binding.imageViewIcon.setImageResource(R.drawable.user_add_outline_56)
        } else {
            holder.binding.imageViewIcon.setImageResource(R.drawable.done_24)
        }

        if (peopleSearchUi.online == 0) {
            holder.binding.imageViewOnline.setImageResource(R.drawable.emptiness)
        } else {
            holder.binding.imageViewOnline.setImageResource(R.drawable.online_composite_16)
        }

        if (peopleSearchUi.description.isEmpty()) {
            holder.binding.textViewLocation.visibility = View.GONE
        } else {
            holder.binding.textViewLocation.visibility = View.VISIBLE
        }

        holder.binding.textViewLocation.text = peopleSearchUi.description
    }

    object PeopleSearchDiffCallback : DiffUtil.ItemCallback<PeopleSearchUi>() {
        override fun areItemsTheSame(
            oldItem: PeopleSearchUi,
            newItem: PeopleSearchUi
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: PeopleSearchUi,
            newItem: PeopleSearchUi
        ): Boolean {
            return oldItem == newItem
        }
    }
}