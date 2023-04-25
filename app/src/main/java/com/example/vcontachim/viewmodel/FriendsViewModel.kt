package com.example.vcontachim.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vcontachim.fragment.VcontachimApplication
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

                val sharedPreferences = VcontachimApplication.context.getSharedPreferences(
                    "vcontachim",
                    Context.MODE_PRIVATE
                )

                val friendsEditor: String? = sharedPreferences.getString("auth", null)
                val friends: Friends =
                    VcontachimApplication.vcontachimService.getFriends(token = "Bearer ${friendsEditor!!}")

                progressBarLiveData.value = false
                friendsLiveData.value = friends
            } catch (e: Exception) {
                progressBarLiveData.value = false
                errorLiveData.value = e.message
            }
        }
    }

}