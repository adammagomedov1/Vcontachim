package com.example.vcontachim.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vcontachim.VcontachimApplication
import com.example.vcontachim.models.PhotoAlbums
import kotlinx.coroutines.launch

class PhotoAlbumsViewModel : ViewModel() {

    val photoAlbumsLiveData = MutableLiveData<PhotoAlbums>()
    val progressBarLiveData = MutableLiveData<Boolean>()
    val errorLiveData = MutableLiveData<String>()

    fun loadList() {
        viewModelScope.launch {
            try {
                progressBarLiveData.value = true

                val photoAlbumsToken = VcontachimApplication.token.tookToken

                val photoAlbums: PhotoAlbums =
                    VcontachimApplication.vcontachimService.getAlbumsPhotos(token = "Bearer ${photoAlbumsToken!!}")

                progressBarLiveData.value = false
                photoAlbumsLiveData.value = photoAlbums
            } catch (e: Exception) {
                progressBarLiveData.value = false
                errorLiveData.value = e.message
            }
        }
    }
}