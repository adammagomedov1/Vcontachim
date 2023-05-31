package com.example.vcontachim.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vcontachim.VcontachimApplication
import com.example.vcontachim.models.ItemVideo
import com.example.vcontachim.models.ProfileVideoComment
import com.example.vcontachim.models.VideoComment
import com.example.vcontachim.models.VideoCommentUi
import kotlinx.coroutines.launch

class VideoCommentViewModel : ViewModel() {

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
                        lastName = profileVideoComment.lastName
                    )
                    videoCommentUi
                }


                videoCommentUiLiveData.value = videoCommentUi
            } catch (e: Exception) {
                errorLiveData.value = e.message
            }
        }
    }
}