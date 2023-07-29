package com.example.vcontachim.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.View.OnClickListener
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.vcontachim.R
import com.example.vcontachim.VcontachimApplication
import com.example.vcontachim.adapter.PeopleSearchAdapter
import com.example.vcontachim.adapter.SearchHistoryAdapter
import com.example.vcontachim.database.SearchHistoryDao
import com.example.vcontachim.databinding.FragmentPeopleSearchBinding
import com.example.vcontachim.models.PeopleSearchUi
import com.example.vcontachim.models.SearchHistory
import com.example.vcontachim.viewmodel.PeopleSearchViewModel

class PeopleSearchFragment : Fragment(R.layout.fragment_people_search) {
    private var binding: FragmentPeopleSearchBinding? = null

    private val viewModel: PeopleSearchViewModel by lazy {
        ViewModelProvider(this)[PeopleSearchViewModel::class.java]
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPeopleSearchBinding.bind(view)

        val adapter =
            SearchHistoryAdapter(clickListener = object : SearchHistoryAdapter.ClickListener {
                override fun onClick(text: String) {

                    binding!!.editText.setText(text)
                }

                override fun deleteButton(searchHistory: SearchHistory) {
                    viewModel.onRemovalClick(searchHistory)
                }
            })

        val peopleSearchAdapter = PeopleSearchAdapter(object : PeopleSearchAdapter.FriendListener {
            override fun onClick(peopleSearchUi: PeopleSearchUi) {
                if (peopleSearchUi.isFriend == 1) {
                    viewModel.deleteFriends(peopleSearchUi)
                } else {
                    viewModel.addFriends(peopleSearchUi)
                }
            }
        })

        binding!!.backButton.setOnClickListener {
            val bob = SearchHistory(searchHistory = binding!!.editText.toString())
            viewModel.onFoodAdded(bob)
        }

        if (binding!!.editText.text.isNotEmpty()) {
            binding!!.recyclerViewSearchHistory.adapter = adapter
            binding!!.recyclerView.visibility = View.GONE
            binding!!.recyclerViewSearchHistory.visibility = View.VISIBLE
        } else {
            binding!!.recyclerView.adapter = peopleSearchAdapter
            binding!!.recyclerViewSearchHistory.visibility = View.GONE
            binding!!.recyclerView.visibility = View.VISIBLE
        }

        viewModel.searchPeopleSearch.observe(viewLifecycleOwner) {
            peopleSearchAdapter.submitList(it)
        }

        viewModel.processBarLiveData.observe(viewLifecycleOwner) {
            if (it == true) {
                binding!!.progressBar.visibility = View.VISIBLE
            } else {
                binding!!.progressBar.visibility = View.GONE
            }
        }

        viewModel.errorLiveData.observe(viewLifecycleOwner) {
            val toast = Toast.makeText(
                requireContext(),
                it,
                Toast.LENGTH_LONG
            )
            toast.show()
        }

        viewModel.searchHistoryLiveData.observe(viewLifecycleOwner) {
            adapter.submitList(it)
            adapter.notifyDataSetChanged()
        }

        viewModel.getSearchHistoryList()

        binding!!.editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                viewModel.loadPeopleSearch(s.toString())
            }
        })

        binding!!.imageViewDeleteIcon.setOnClickListener(object : OnClickListener {
            override fun onClick(v: View?) {
                binding!!.editText.text.clear()
                viewModel.deleteSearchList()
            }
        })
    }
}