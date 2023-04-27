package com.example.vcontachim.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vcontachim.fragment.VcontachimApplication
import com.example.vcontachim.models.Communities
import kotlinx.coroutines.launch

class CommunitiesViewModel : ViewModel() {

    val communitiesLiveData = MutableLiveData<Communities>()
    val progressBarLiveData = MutableLiveData<Boolean>()
    val errorLiveData = MutableLiveData<String>()

    fun loadCommunities() {
        viewModelScope.launch {
            try {
                progressBarLiveData.value = true

                val sharedPreferens = VcontachimApplication.context.getSharedPreferences(
                    "vcontachim",
                    Context.MODE_PRIVATE
                )

                val communitiesToken = sharedPreferens.getString("auth", null)
                val communities: Communities =
                    VcontachimApplication.vcontachimService.getGroups(token = "Bearer ${communitiesToken!!}")

                progressBarLiveData.value = false
                communitiesLiveData.value = communities
            } catch (e: Exception) {
                progressBarLiveData.value = false
                errorLiveData.value = e.message
            }
        }
    }
}