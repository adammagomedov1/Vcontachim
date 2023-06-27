package com.example.vcontachim.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.vcontachim.R
import com.example.vcontachim.VcontachimApplication
import com.example.vcontachim.adapter.PeopleSearchAdapter
import com.example.vcontachim.databinding.FragmentPeopleSearchBinding
import com.example.vcontachim.viewmodel.PeopleSearchViewModel

class PeopleSearchFragment : Fragment(R.layout.fragment_people_search) {
    private var binding: FragmentPeopleSearchBinding? = null

    private val viewModel: PeopleSearchViewModel by lazy {
        ViewModelProvider(this)[PeopleSearchViewModel::class.java]
    }

    private val peopleSearchAdapter = PeopleSearchAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPeopleSearchBinding.bind(view)

        binding!!.backButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                VcontachimApplication.router.exit()
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

        binding!!.editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                s?.filter { s.contains(s, ignoreCase = true) }
                viewModel.loadPeopleSearch(s.toString())

                binding!!.imageViewDeleteIcon.setOnClickListener(object : View.OnClickListener {
                    override fun onClick(v: View?) {
                        s!!.clear()
                        viewModel.deleteSearchList()
                    }
                })
            }
        })
    }
}