package com.example.vcontachim.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.vcontachim.R
import com.example.vcontachim.databinding.FragmentPeopleSearchBinding
import com.example.vcontachim.viewmodel.PeopleSearchViewModel

class PeopleSearchFragment : Fragment(R.layout.fragment_people_search) {
    var binding: FragmentPeopleSearchBinding? = null

    val viewModel: PeopleSearchViewModel by lazy {
        ViewModelProvider(this)[PeopleSearchViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPeopleSearchBinding.bind(view)

        viewModel.searchScreenLiveData.observe(viewLifecycleOwner) {

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

        val textName = binding!!.editText.toString()
        viewModel.loadSearchScreen(textName)
    }
}