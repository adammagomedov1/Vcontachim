package com.example.vcontachim.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vcontachim.VcontachimApplication
import com.example.vcontachim.models.Item
import com.example.vcontachim.models.ProfileDetail
import com.example.vcontachim.models.Response
import kotlinx.coroutines.launch

class ProfileDetailsViewModel : ViewModel() {

    val profileDetailLiveData = MutableLiveData<ProfileDetail>()
    val errorLiveData = MutableLiveData<String>()

    fun loadProfileDetails(id: Long) {
        viewModelScope.launch {
            try {

                val profileDetail =
                    VcontachimApplication.vcontachimService.getInfoProfile(id)

                profileDetailLiveData.value = profileDetail
            } catch (e: Exception) {
                errorLiveData.value = e.message
            }
        }
    }
}