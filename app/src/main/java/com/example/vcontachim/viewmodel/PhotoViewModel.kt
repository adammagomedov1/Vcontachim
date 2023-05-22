package com.example.vcontachim.viewmodel

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

                val likes: Likes =
                    VcontachimApplication.vcontachimService.addLike(
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

                val deleteLike: Likes =
                    VcontachimApplication.vcontachimService.deleteLike(
                        itemId = idPhotos
                    )

                likesLiveData.value = deleteLike
            } catch (e: Exception) {
                errorLiveData.value = e.message
            }
        }
    }
}