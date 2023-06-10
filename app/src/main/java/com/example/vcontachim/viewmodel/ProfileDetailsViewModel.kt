package com.example.vcontachim.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vcontachim.VcontachimApplication
import com.example.vcontachim.models.ProfileDetail
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

    fun deleteFriend(id: Long) {
        viewModelScope.launch {
            try {

                VcontachimApplication.vcontachimService.deleteFriends(
                    userId = id
                )

                profileDetailLiveData.value = profileDetailLiveData.value?.also {
                    it.response[0].isFriend = if (it.response[0].isFriend == 1) 0 else 1
                }
            } catch (e: Exception) {
                errorLiveData.value = e.message
            }
        }
    }

    fun addFriend(id: Long) {
        viewModelScope.launch {
            try {

                VcontachimApplication.vcontachimService.addFriends(userId = id)

                profileDetailLiveData.value = profileDetailLiveData.value?.also {
                    it.response[0].isFriend = if (it.response[0].isFriend == 1) 0 else 1
                }
            } catch (e: Exception) {
                errorLiveData.value = e.message
            }
        }
    }
}