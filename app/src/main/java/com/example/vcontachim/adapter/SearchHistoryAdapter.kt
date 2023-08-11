package com.example.vcontachim.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.vcontachim.R
import com.example.vcontachim.databinding.ItemSearchHistoryBinding
import com.example.vcontachim.models.SearchHistory

class SearchHistoryAdapter(val clickListener: ClickListener) :
    ListAdapter<SearchHistory, SearchHistoryAdapter.SearchHistoryViewHolder>(
        SearchHistoryDiffCallback
    ) {

    class SearchHistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding: ItemSearchHistoryBinding = ItemSearchHistoryBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchHistoryViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)

        val itemView: View = layoutInflater.inflate(
            R.layout.item_search_history,
            parent,
            false
        )
        return SearchHistoryViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SearchHistoryViewHolder, position: Int) {
        val searchHistory: SearchHistory = getItem(position)

        holder.binding.textViewSearchHistory.setOnClickListener {
            clickListener.onClick(holder.binding.textViewSearchHistory.text.toString())
        }

        holder.binding.textViewSearchHistory.text = searchHistory.searchHistory

        holder.binding.imageViewSearchHistory.setOnClickListener {
            clickListener.deleteButton(searchHistory)
        }
    }


    object SearchHistoryDiffCallback : DiffUtil.ItemCallback<SearchHistory>() {
        override fun areItemsTheSame(
            oldItem: SearchHistory,
            newItem: SearchHistory
        ): Boolean {
            return oldItem.searchHistory == newItem.searchHistory
        }

        override fun areContentsTheSame(
            oldItem: SearchHistory,
            newItem: SearchHistory
        ): Boolean {
            return oldItem == newItem
        }
    }

    interface ClickListener {
        fun onClick(text: String)
        fun deleteButton(searchHistory: SearchHistory)
    }
}