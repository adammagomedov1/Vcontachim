package com.example.vcontachim.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vcontachim.VcontachimApplication
import com.example.vcontachim.models.ItemVideo
import com.example.vcontachim.models.PhotoCommentsUi
import com.example.vcontachim.models.ProfileVideoComment
import com.example.vcontachim.models.VideoCommentUi
import kotlinx.coroutines.launch

class VideoCommentViewModel : ViewModel() {

    val addCommentLiveData = MutableLiveData<String>()
    val videoCommentUiLiveData = MutableLiveData<List<VideoCommentUi>>()
    val errorLiveData = MutableLiveData<String>()

    fun loadVideoComment(itemVideo: ItemVideo) {
        viewModelScope.launch {
            try {

                val videoComment = VcontachimApplication.vcontachimService.getCommentsVideo(
                    videoId = itemVideo.id,
                    ownerId = itemVideo.ownerId
                )

                val videoCommentUi: List<VideoCommentUi> = videoComment.response.items.map {
                    val profileVideoComment: ProfileVideoComment =
                        videoComment.response.profiles.first { profileVideoComment -> profileVideoComment.id == it.fromId }
                    val videoCommentUi = VideoCommentUi(
                        fromId = it.fromId,
                        date = it.date,
                        text = it.text,
                        userLikes = it.likes.userLikes,
                        idProfile = profileVideoComment.id,
                        firstName = profileVideoComment.firstName,
                        lastName = profileVideoComment.lastName,
                        idComment = it.id
                    )
                    videoCommentUi
                }

                videoCommentUiLiveData.value = videoCommentUi
            } catch (e: Exception) {
                errorLiveData.value = e.message
            }
        }
    }

    fun createCommentVideo(itemVideo: ItemVideo, message: String) {
        viewModelScope.launch {
            try {

                VcontachimApplication.vcontachimService.createCommentVideo(
                    videoId = itemVideo.id,
                    ownerId = itemVideo.ownerId,
                    message = message
                )

                addCommentLiveData.value = ""
            } catch (e: Exception) {
                errorLiveData.value = e.message
            }
        }
    }

    fun addLikes(videoCommentUi: VideoCommentUi) {
        viewModelScope.launch {
            try {

                VcontachimApplication.vcontachimService.addLikesVideo(
                    itemId = videoCommentUi.idComment,
                    ownerId = videoCommentUi.fromId
                )
                val videoCommentList = videoCommentUiLiveData.value!!.toMutableList()
                // обновляем элемент коментария на котором был клик
                val newVideoCommentUi: VideoCommentUi = videoCommentUi.copy(
                    userLikes = if (videoCommentUi.userLikes == 1) 0 else 1
                )

                val saveIndexFromList = videoCommentList.indexOf(videoCommentUi)
                videoCommentList.set(index = saveIndexFromList, newVideoCommentUi)

                videoCommentUiLiveData.value = videoCommentList
            } catch (e: Exception) {
                errorLiveData.value = e.message
            }
        }
    }

    fun deleteLikes(videoCommentUi: VideoCommentUi) {
        viewModelScope.launch {
            try {

                VcontachimApplication.vcontachimService.deleteLikesVideo(
                    itemId = videoCommentUi.idComment,
                    ownerId = videoCommentUi.fromId
                )
                val videoCommentList = videoCommentUiLiveData.value!!.toMutableList()
                // обновляем элемент коментария на котором был клик
                val newVideoCommentUi: VideoCommentUi = videoCommentUi.copy(
                    userLikes = if (videoCommentUi.userLikes == 1) 0 else 1
                )

                val saveIndexFromList = videoCommentList.indexOf(videoCommentUi)
                videoCommentList.set(index = saveIndexFromList, newVideoCommentUi)

                videoCommentUiLiveData.value = videoCommentList
            } catch (e: Exception) {
                errorLiveData.value = e.message
            }
        }
    }
}