package com.example.vcontachim.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vcontachim.VcontachimApplication
import com.example.vcontachim.models.Friends
import kotlinx.coroutines.launch

class FriendsViewModel : ViewModel() {

    val friendsLiveData = MutableLiveData<Friends>()
    val progressBarLiveData = MutableLiveData<Boolean>()
    val errorLiveData = MutableLiveData<String>()

    fun loadList() {
        viewModelScope.launch {
            try {
                progressBarLiveData.value = true

                val tokenSetter = VcontachimApplication.SharedPreferencesHelper.tookToken
                val friends: Friends =
                    VcontachimApplication.vcontachimService.getFriends(token = "Bearer ${tokenSetter!!}")

                progressBarLiveData.value = false
                friendsLiveData.value = friends
            } catch (e: Exception) {
                progressBarLiveData.value = false
                errorLiveData.value = e.message
            }
        }
    }

}