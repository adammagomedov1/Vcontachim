package com.example.vcontachim.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vcontachim.VcontachimApplication
import com.example.vcontachim.models.ItemVideo
import com.example.vcontachim.models.VideoLike
import kotlinx.coroutines.launch

class VideoPlaybackViewModel : ViewModel() {

    val videoLikesViewData = MutableLiveData<VideoLike>()
    val errorLiveData = MutableLiveData<String>()

    fun loadVideoLike(itemVideo: ItemVideo) {
        viewModelScope.launch {
            try {

                val likeVideo: VideoLike = VcontachimApplication.vcontachimService.addLikeVideo(
                    itemId = itemVideo.id,
                    ownerId = itemVideo.ownerId
                )

                videoLikesViewData.value = likeVideo
            } catch (e: Exception) {
                errorLiveData.value = e.message
            }
        }
    }

    fun loadDeleteLike(itemVideo: ItemVideo) {
        viewModelScope.launch {
            try {

                val deleteLike: VideoLike =
                    VcontachimApplication.vcontachimService.deleteLikeVideo(
                        itemId = itemVideo.id,
                        ownerId = itemVideo.ownerId
                    )

                videoLikesViewData.value = deleteLike
            } catch (e: Exception) {
                errorLiveData.value = e.message

            }
        }
    }
}