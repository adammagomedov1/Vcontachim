package com.example.vcontachim.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.vcontachim.R
import com.example.vcontachim.adapter.CommunitiesAdapter
import com.example.vcontachim.databinding.FragmentCommunitiesBinding
import com.example.vcontachim.models.Communities
import com.example.vcontachim.viewmodel.CommunitiesViewModel
import com.google.android.material.snackbar.Snackbar
import kotlin.io.path.fileVisitor

class CommunitiesFragment : Fragment(R.layout.fragment_communities) {

    private var binding: FragmentCommunitiesBinding? = null

    private val viewModel: CommunitiesViewModel by lazy {
        ViewModelProvider(this)[CommunitiesViewModel::class.java]
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCommunitiesBinding.bind(view)

        val communitiesAdapter = CommunitiesAdapter()
        binding!!.recyclerView.adapter = communitiesAdapter

        binding!!.toolbar.setNavigationOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                VcontachimApplication.router.exit()
            }
        })

        viewModel.communitiesLiveData.observe(viewLifecycleOwner) {
            communitiesAdapter.communitiesList = it.response.items
            communitiesAdapter.notifyDataSetChanged()
        }

        viewModel.progressBarLiveData.observe(viewLifecycleOwner, object : Observer<Boolean> {
            override fun onChanged(t: Boolean) {
                if (t == true) {
                    binding!!.progressBar.visibility = View.VISIBLE
                } else {
                    binding!!.progressBar.visibility = View.GONE
                }
            }
        })

        viewModel.errorLiveData.observe(viewLifecycleOwner) {
            val snacbar: Snackbar = Snackbar.make(
                requireView(),
                it,
                Snackbar.LENGTH_LONG
            )
            snacbar.show()
        }

        viewModel.loadCommunities()
    }
}