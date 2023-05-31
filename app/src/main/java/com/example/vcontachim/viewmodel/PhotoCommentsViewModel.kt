package com.example.vcontachim.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vcontachim.VcontachimApplication
import com.example.vcontachim.models.*
import kotlinx.coroutines.launch

class PhotoCommentsViewModel : ViewModel() {

    val addCommentLiveData = MutableLiveData<String>()
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
                        id = profile.id,
                        personOnline = profile.personOnline == 1,
                        idComment = it.id,
                        userLikes = it.likes.userLikes
                    )
                    photoCommentsUi
                }

                photoCommentsLiveData.value = photoCommentsUi
            } catch (e: Exception) {
                errorLiveData.value = e.message
            }
        }
    }

    fun likeComment(photoCommentsUi: PhotoCommentsUi) {
        viewModelScope.launch {
            try {

                VcontachimApplication.vcontachimService.addLikeComment(
                    itemId = photoCommentsUi.idComment
                )
                val photoCommentsList = photoCommentsLiveData.value!!.toMutableList()
                // обновляем элемент коментария на котором был клик
                var newPhotoCommentsUi: PhotoCommentsUi = photoCommentsUi.copy(
                    userLikes = if (photoCommentsUi.userLikes == 1) 0 else 1
                )

                val saveIndexFromList = photoCommentsList.indexOf(photoCommentsUi)
                photoCommentsList.set(index = saveIndexFromList, newPhotoCommentsUi)

                photoCommentsLiveData.value = photoCommentsList
            } catch (e: Exception) {
                errorLiveData.value = e.message
            }
        }
    }

    fun deleteLikeComment(photoCommentsUi: PhotoCommentsUi) {
        viewModelScope.launch {
            try {

                VcontachimApplication.vcontachimService.deleteLikeComment(
                    itemId = photoCommentsUi.idComment
                )

                val photoCommentsList = photoCommentsLiveData.value!!.toMutableList()
                // обновляем элемент коментария на котором был клик
                val newPhotoComments: PhotoCommentsUi = photoCommentsUi.copy(
                    userLikes = if (photoCommentsUi.userLikes == 1) 0 else 1
                )

                val saveIndexFromList = photoCommentsList.indexOf(photoCommentsUi)
                photoCommentsList.set(index = saveIndexFromList, newPhotoComments)

                photoCommentsLiveData.value = photoCommentsList
            } catch (e: Exception) {
                errorLiveData.value = e.message
            }
        }
    }

    fun createCommentPhotos(photos: ItemPhotos, message: String) {
        viewModelScope.launch {
            try {

                VcontachimApplication.vcontachimService.createCommentPhotos(
                    photoId = photos.id,
                    message = message
                )

                addCommentLiveData.value = ""
            } catch (e: Exception) {
                errorLiveData.value = e.message
            }
        }
    }
}