package com.example.vcontachim.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.vcontachim.enum1.MyEnum
import com.example.vcontachim.R
import com.example.vcontachim.adapter.NewsAdapter
import com.example.vcontachim.databinding.FragmentNewsBinding
import com.example.vcontachim.models.NewsUi
import com.example.vcontachim.viewmodel.NewsViewModel

class NewsFragment(val myEnum: MyEnum) : Fragment(R.layout.fragment_news) {
    private var binding: FragmentNewsBinding? = null

    private val viewModel: NewsViewModel by lazy {
        ViewModelProvider(this)[NewsViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNewsBinding.bind(view)

        val newsAdapter = NewsAdapter(likeListener = object : NewsAdapter.LikeListener {
            override fun onClick(newsUi: NewsUi) {
                if (newsUi.userLikes == 0L) {
                    viewModel.addLike(newsUi)
                } else {
                    viewModel.deleteLike(newsUi)
                }
            }
        })
        binding!!.recyclerView.adapter = newsAdapter

        viewModel.progressBarLiveData.observe(viewLifecycleOwner) {
            if (it == true) {
                binding!!.progressBar.visibility = View.VISIBLE
            } else {
                binding!!.progressBar.visibility = View.GONE
            }
        }

        viewModel.newsUiLiveData.observe(viewLifecycleOwner) {
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

        if (myEnum == MyEnum.LEFT) {
            viewModel.loadNews()
        }
        if (myEnum == MyEnum.RIGHT) {
            viewModel.loadRecommendedNewsfeed()
        }
    }
}