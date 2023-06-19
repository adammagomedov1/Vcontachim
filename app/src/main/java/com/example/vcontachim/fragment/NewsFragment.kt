package com.example.vcontachim.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.vcontachim.R
import com.example.vcontachim.adapter.NewsAdapter
import com.example.vcontachim.databinding.FragmentNewsBinding
import com.example.vcontachim.viewmodel.NewsViewModel

class NewsFragment : Fragment(R.layout.fragment_news) {
    private var binding: FragmentNewsBinding? = null

    private val viewModel: NewsViewModel by lazy {
        ViewModelProvider(this)[NewsViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNewsBinding.bind(view)

        val newsAdapter = NewsAdapter()
        binding!!.recyclerView.adapter = newsAdapter

        viewModel.progressBarLiveData.observe(viewLifecycleOwner) {
            if (it == true) {
                binding!!.progressBar.visibility = View.VISIBLE
            } else {
                binding!!.progressBar.visibility = View.GONE
            }
        }

        viewModel.newsLiveData.observe(viewLifecycleOwner) {
            newsAdapter.submitList(it)
        }

        viewModel.errorLiveData.observe(viewLifecycleOwner) {
            val toast = Toast.makeText(
                requireContext(),
                it,
                Toast.LENGTH_LONG
            )
            toast.show()
        }

        viewModel.loadNews()
    }
}