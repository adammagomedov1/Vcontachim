package com.example.vcontachim.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vcontachim.fragment.VcontachimApplication
import com.example.vcontachim.models.Users
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {

    val profileLiveData = MutableLiveData<Users>()
    val errorLiveData = MutableLiveData<String>()

    fun loadProfile() {
        viewModelScope.launch {
            try {
                val sharedPreferences = VcontachimApplication.context.getSharedPreferences(
                    "vcontachim",
                    Context.MODE_PRIVATE
                )

                val tookToken: String? = sharedPreferences.getString("auth", null)
                val users: Users =
                    VcontachimApplication.vcontachimService.getUsers(token = "Bearer ${tookToken!!}")

                profileLiveData.value = users
            } catch (e: Exception) {
                errorLiveData.value = e.message!!
            }
        }
    }
}