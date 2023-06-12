package com.example.vcontachim.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vcontachim.VcontachimApplication
import com.example.vcontachim.models.ProfileDetail
import com.example.vcontachim.models.ResponseProfileDetail
import kotlinx.coroutines.launch

class ProfileDetailsViewModel : ViewModel() {

    val profileDetailLiveData = MutableLiveData<ResponseProfileDetail>()
    val errorLiveData = MutableLiveData<String>()

    fun loadProfileDetails(id: Long) {
        viewModelScope.launch {
            try {

                val profileDetail =
                    VcontachimApplication.vcontachimService.getInfoProfile(id)

                profileDetailLiveData.value = profileDetail.response[0]
            } catch (e: Exception) {
                errorLiveData.value = e.message
            }
        }
    }

    fun deleteFriend(id: Long) {
        viewModelScope.launch {
            try {

                VcontachimApplication.vcontachimService.deleteFriend(userId = id)

                val responseProfileDetail = profileDetailLiveData.value!!

                val modifiedResponseProfileDetail =
                    responseProfileDetail.copy(isFriend = if (responseProfileDetail.isFriend == 1) 0 else 1)

                profileDetailLiveData.value = modifiedResponseProfileDetail
            } catch (e: Exception) {
                errorLiveData.value = e.message
            }
        }
    }

    fun addFriend(id: Long) {
        viewModelScope.launch {
            try {

                VcontachimApplication.vcontachimService.addFriend(userId = id)

                val responseProfileDetail = profileDetailLiveData.value!!

                val modifiedResponseProfileDetail =
                    responseProfileDetail.copy(isFriend = if (responseProfileDetail.isFriend == 1) 0 else 1)

                profileDetailLiveData.value = modifiedResponseProfileDetail
            } catch (e: Exception) {
                errorLiveData.value = e.message
            }
        }
    }
}