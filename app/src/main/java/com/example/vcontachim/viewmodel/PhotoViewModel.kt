package com.example.vcontachim.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vcontachim.VcontachimApplication
import com.example.vcontachim.models.Likes
import kotlinx.coroutines.launch

class PhotoViewModel : ViewModel() {

    val likesLiveData = MutableLiveData<Likes>()
    val errorLiveData = MutableLiveData<String>()

    fun like(idPhotos: String) {
        viewModelScope.launch {
            try {
                val sharedPreferences = VcontachimApplication.context.getSharedPreferences(
                    "vcontachim",
                    Context.MODE_PRIVATE
                )
                val tookToken: String? = sharedPreferences.getString("auth", null)
                val likes: Likes =
                    VcontachimApplication.vcontachimService.addLike(
                        token = "Bearer ${tookToken!!}",
                        itemId = idPhotos
                    )

                likesLiveData.value = likes
            } catch (e: Exception) {
                errorLiveData.value = e.message
            }
        }
    }

    fun deleteLike(idPhotos: String) {
        viewModelScope.launch {
            try {

                val tookToken = VcontachimApplication.token.tookToken
                val deleteLike: Likes =
                    VcontachimApplication.vcontachimService.deleteLike(
                        token = "Bearer $tookToken",
                        itemId = idPhotos
                    )

                likesLiveData.value = deleteLike
            } catch (e: Exception) {
                errorLiveData.value = e.message
            }
        }
    }
}