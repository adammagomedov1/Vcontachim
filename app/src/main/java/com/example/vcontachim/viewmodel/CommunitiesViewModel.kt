package com.example.vcontachim.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vcontachim.VcontachimApplication
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

                val communities: Communities =
                    VcontachimApplication.vcontachimService.getGroups()

                progressBarLiveData.value = false
                communitiesLiveData.value = communities
            } catch (e: Exception) {
                progressBarLiveData.value = false
                errorLiveData.value = e.message
            }
        }
    }
}