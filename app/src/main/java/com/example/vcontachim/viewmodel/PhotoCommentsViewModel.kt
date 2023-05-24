package com.example.vcontachim.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vcontachim.VcontachimApplication
import com.example.vcontachim.models.ItemPhotos
import com.example.vcontachim.models.PhotoComments
import com.example.vcontachim.models.PhotoCommentsUi
import com.example.vcontachim.models.Profile
import kotlinx.coroutines.launch

class PhotoCommentsViewModel : ViewModel() {

    val photoCommentsLiveData = MutableLiveData<List<PhotoCommentsUi>>()
    val errorLiveData = MutableLiveData<String>()

    fun loadPhotoComments(itemPhotos: ItemPhotos) {
        viewModelScope.launch {
            try {

                val photoComments: PhotoComments =
                    VcontachimApplication.vcontachimService.getCommentsPhotos(
                        photoId = itemPhotos.id
                    )

                val photoCommentsUi: List<PhotoCommentsUi> = photoComments.response.items.map {
                    val profile: Profile =
                        photoComments.response.profiles.first { profile -> profile.id == it.fromId }
                    val photoCommentsUi = PhotoCommentsUi(
                        fromId = it.fromId,
                        textComments = it.textComments,
                        dateComments = it.dateComments,
                        firstName = profile.firstName,
                        lastName = profile.lastName,
                        photo = profile.photo,
                        id = profile.id
                    )
                    photoCommentsUi
                }

                photoCommentsLiveData.value = photoCommentsUi
            } catch (e: Exception) {
                errorLiveData.value = e.message
            }
        }
    }
}