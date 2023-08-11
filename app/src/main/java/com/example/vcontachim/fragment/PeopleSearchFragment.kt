package com.example.vcontachim.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.view.View.OnClickListener
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.vcontachim.R
import com.example.vcontachim.adapter.PeopleSearchAdapter
import com.example.vcontachim.adapter.SearchHistoryAdapter
import com.example.vcontachim.databinding.FragmentPeopleSearchBinding
import com.example.vcontachim.models.PeopleSearchUi
import com.example.vcontachim.models.SearchHistory
import com.example.vcontachim.viewmodel.PeopleSearchViewModel

class PeopleSearchFragment : Fragment(R.layout.fragment_people_search) {
    private var binding: FragmentPeopleSearchBinding? = null

    private val viewModel: PeopleSearchViewModel by lazy {
        ViewModelProvider(this)[PeopleSearchViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPeopleSearchBinding.bind(view)
        binding!!.apply {

            val peopleSearchAdapter =
                PeopleSearchAdapter(object : PeopleSearchAdapter.FriendListener {
                    override fun onClick(peopleSearchUi: PeopleSearchUi) {
                        if (peopleSearchUi.isFriend == 1) {
                            viewModel.deleteFriends(peopleSearchUi)
                        } else {
                            viewModel.addFriends(peopleSearchUi)
                        }
                    }
                })

            val adapterSearchHistory =
                SearchHistoryAdapter(clickListener = object :
                    SearchHistoryAdapter.ClickListener {
                    override fun onClick(text: String) {
                        editText.setText(text)
                    }

                    override fun onDeleteClick(searchHistory: SearchHistory) {
                        viewModel.onRemovalSearchHistoryClick(searchHistory)
                    }
                })

            recyclerView.adapter = peopleSearchAdapter
            recyclerViewSearchHistory.adapter = adapterSearchHistory

            editText.setOnEditorActionListener(object : TextView.OnEditorActionListener {
                override fun onEditorAction(
                    v: TextView?,
                    actionId: Int,
                    event: KeyEvent?
                ): Boolean {
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        val takeTextFromEditText = editText.text.toString()
                        if (takeTextFromEditText.isNotBlank()) {
                            val saveToLibrary =
                                SearchHistory(searchHistory = takeTextFromEditText)
                            viewModel.onSearchHistoryAdded(saveToLibrary)
                            viewModel.getSearchHistoryList()
                        }
                        return true
                    }
                    return false
                }
            })

            clearList.setOnClickListener {
                viewModel.onDeletingSearchHistoryListPress()
            }

            viewModel.processBarLiveData.observe(viewLifecycleOwner) {
                if (it == true) {
                    progressBar.visibility = View.VISIBLE
                } else {
                    progressBar.visibility = View.GONE
                }
            }

            editText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {

                }

                override fun afterTextChanged(s: Editable?) {
                    viewModel.loadPeopleSearch(s.toString())

                    if (s!!.isNotEmpty()) {
                        recyclerViewSearchHistory.visibility = View.GONE
                        recyclerView.visibility = View.VISIBLE
                        textViewForHistoryAndSearch.setText(R.string.global_search)
                        clearList.visibility = View.GONE
                    } else {
                        recyclerView.visibility = View.GONE
                        recyclerViewSearchHistory.visibility = View.VISIBLE
                        textViewForHistoryAndSearch.setText(R.string.search_history)
                        clearList.visibility = View.VISIBLE
                    }
                }
            })

            imageViewDeleteIcon.setOnClickListener(object : OnClickListener {
                override fun onClick(v: View?) {
                    editText.text.clear()
                    viewModel.deleteSearchList()
                }
            })

            viewModel.errorLiveData.observe(viewLifecycleOwner) {
                val toast = Toast.makeText(
                    requireContext(),
                    it,
                    Toast.LENGTH_LONG
                )
                toast.show()
            }

            viewModel.searchHistoryLiveData.observe(viewLifecycleOwner) {
                adapterSearchHistory.submitList(it)
            }

            viewModel.searchPeopleSearch.observe(viewLifecycleOwner) {
                peopleSearchAdapter.submitList(it)
            }

            viewModel.getSearchHistoryList()
        }
    }
}