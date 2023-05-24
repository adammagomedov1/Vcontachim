package com.example.vcontachim.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vcontachim.VcontachimApplication
import com.example.vcontachim.models.ItemPhotos
import com.example.vcontachim.models.PhotoComments
import kotlinx.coroutines.launch

class PhotoCommentsViewModel : ViewModel() {

    val photoCommentsLiveData = MutableLiveData<PhotoComments>()
    val errorLiveData = MutableLiveData<String>()

    fun loadPhotoComments(itemPhotos: ItemPhotos) {
        viewModelScope.launch {
            try {

                val photoComments = VcontachimApplication.vcontachimService.getCommentsPhotos(
                    photoId = itemPhotos.id
                )

                photoCommentsLiveData.value = photoComments
            } catch (e: Exception) {
                errorLiveData.value = e.message
            }
        }
    }
}