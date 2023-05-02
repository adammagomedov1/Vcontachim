package com.example.vcontachim.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vcontachim.fragment.VcontachimApplication
import com.example.vcontachim.models.PhotoAlbums
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

                val sharedPreferences = VcontachimApplication.context.getSharedPreferences(
                    "vcontachim",
                    Context.MODE_PRIVATE
                )

                val photosToken = sharedPreferences.getString("auth", null)

                val photos: Photos = VcontachimApplication.vcontachimService.getPhotos(
                    token = "Bearer ${photosToken!!}",
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