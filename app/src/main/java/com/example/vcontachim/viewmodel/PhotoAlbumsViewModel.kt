package com.example.vcontachim.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vcontachim.fragment.VcontachimApplication
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

                val sharedPreferences = VcontachimApplication.context.getSharedPreferences(
                    "vcontachim",
                    Context.MODE_PRIVATE
                )

                val photoAlbumsToken = sharedPreferences.getString("auth", null)

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