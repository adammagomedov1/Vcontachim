package com.example.vcontachim.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vcontachim.fragment.VcontachimApplication
import com.example.vcontachim.models.Likes
import com.example.vcontachim.models.Photos
import kotlinx.coroutines.launch
import kotlin.Exception as Exception

class PhotoViewModel : ViewModel() {

    val likesLiveData = MutableLiveData<Likes>()
    val errorLiveData = MutableLiveData<String>()

    fun like(id: String) {
        viewModelScope.launch {
            try {
                val sharedPreferences = VcontachimApplication.context.getSharedPreferences(
                    "vcontachim",
                    Context.MODE_PRIVATE
                )
                val tookToken: String? = sharedPreferences.getString("auth", null)
                val likes: Likes =
                    VcontachimApplication.vcontachimService.addLikes(
                        token = "Bearer ${tookToken!!}",
                        itemId = id
                    )

                likesLiveData.value = likes
            } catch (e: Exception) {
                errorLiveData.value = e.message
            }
        }
    }
}