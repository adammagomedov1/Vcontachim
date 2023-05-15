package com.example.vcontachim.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vcontachim.VcontachimApplication
import com.example.vcontachim.models.Photos
import kotlinx.coroutines.launch

class PhotosViewModels : ViewModel() {

    val photosLiveData = MutableLiveData<Photos>()
    val errorLiveData = MutableLiveData<String>()
    val progressBarLiveData = MutableLiveData<Boolean>()

    fun loadList(photos: Long) {
        viewModelScope.launch {
            try {
                progressBarLiveData.value = true

                val tokenSetter = VcontachimApplication.sharedPreferencesHelper.tookToken

                val photos: Photos = VcontachimApplication.vcontachimService.getPhotos(
                    token = "Bearer ${tokenSetter!!}",
                    albumId = photos
                )

                progressBarLiveData.value = false
                photosLiveData.value = photos
            } catch (e: Exception) {
                progressBarLiveData.value = false
                errorLiveData.value = e.message
            }
        }
    }

}