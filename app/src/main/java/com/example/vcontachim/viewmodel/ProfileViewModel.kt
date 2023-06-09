package com.example.vcontachim.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vcontachim.VcontachimApplication
import com.example.vcontachim.models.Users
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {

    val profileLiveData = MutableLiveData<Users>()
    val errorLiveData = MutableLiveData<String>()

    fun loadProfile() {
        viewModelScope.launch {
            try {

                val users: Users =
                    VcontachimApplication.vcontachimService.getUsers()

                profileLiveData.value = users
            } catch (e: Exception) {
                errorLiveData.value = e.message!!
            }
        }
    }
}