package com.example.vcontachim.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.vcontachim.R
import com.example.vcontachim.adapter.FriendsAdapter
import com.example.vcontachim.databinding.FragmentFriendsBinding
import com.example.vcontachim.VcontachimApplication
import com.example.vcontachim.viewmodel.FriendsViewModel
import com.google.android.material.snackbar.Snackbar

class FriendsFragment : Fragment(R.layout.fragment_friends) {

    private var binding: FragmentFriendsBinding? = null

    private val viewModel: FriendsViewModel by lazy {
        ViewModelProvider(this)[FriendsViewModel::class.java]
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFriendsBinding.bind(view)

        val friendsAdapter = FriendsAdapter()
        binding!!.recyclerView.adapter = friendsAdapter

        binding!!.toolbar.setNavigationOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                VcontachimApplication.router.exit()
            }
        })

        viewModel.friendsLiveData.observe(viewLifecycleOwner) {
            friendsAdapter.fieldsList = it.response.items
            friendsAdapter.notifyDataSetChanged()
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
            val snackbar: Snackbar = Snackbar.make(
                requireView(),
                it,
                Snackbar.LENGTH_LONG
            )
            snackbar.show()
        }
        viewModel.loadList()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}
