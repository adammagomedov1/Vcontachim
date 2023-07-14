package com.example.vcontachim.fragment

import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.vcontachim.R
import com.example.vcontachim.adapter.PeopleSearchAdapter
import com.example.vcontachim.databinding.FragmentPeopleSearchBinding
import com.example.vcontachim.models.PeopleSearchUi
import com.example.vcontachim.viewmodel.PeopleSearchViewModel

class PeopleSearchFragment : Fragment(R.layout.fragment_people_search) {
    private var binding: FragmentPeopleSearchBinding? = null

    private val viewModel: PeopleSearchViewModel by lazy {
        ViewModelProvider(this)[PeopleSearchViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPeopleSearchBinding.bind(view)

        val peopleSearchAdapter = PeopleSearchAdapter(object : PeopleSearchAdapter.FriendListener {
            override fun onClick(peopleSearchUi: PeopleSearchUi) {
                if (peopleSearchUi.isFriend == 1) {
                    viewModel.deleteFriends(peopleSearchUi)
                } else {
                    viewModel.addFriends(peopleSearchUi)
                }
            }
        })

        binding!!.backButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
            }
        })

        binding!!.recyclerView.adapter = peopleSearchAdapter

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

        Handler().postDelayed({
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
                    s?.filter { s.contains(s, ignoreCase = true) }
                    viewModel.loadPeopleSearch(s.toString())

                }
            })
        }, 1500)


        binding!!.imageViewDeleteIcon.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                binding!!.editText.text.clear()
                viewModel.deleteSearchList()
            }
        })
    }
}