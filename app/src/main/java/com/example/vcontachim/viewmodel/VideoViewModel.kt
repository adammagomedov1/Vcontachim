package com.example.vcontachim.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vcontachim.VcontachimApplication
import com.example.vcontachim.models.ItemVideo
import com.example.vcontachim.models.Video
import com.example.vcontachim.models.VideoDelete
import kotlinx.coroutines.launch

class VideoViewModel : ViewModel() {

    val deleteVideoLiveData = MutableLiveData<VideoDelete>()
    val videoLiveData = MutableLiveData<Video>()
    val progressBarLiveData = MutableLiveData<Boolean>()
    val errorLiveData = MutableLiveData<String>()

    fun loadVideo() {
        viewModelScope.launch {
            try {
                progressBarLiveData.value = true

                val video: Video = VcontachimApplication.vcontachimService.getVideo()

                progressBarLiveData.value = false
                videoLiveData.value = video
            } catch (e: Exception) {
                progressBarLiveData.value = false
                errorLiveData.value = e.message
            }
        }
    }

    fun loadDeleteVideo(itemVideo: Long) {
        viewModelScope.launch {
            try {

                val deleteVideo =
                    VcontachimApplication.vcontachimService.deleteVideo(videoId = itemVideo)

                deleteVideoLiveData.value = deleteVideo
            } catch (e: Exception) {
                errorLiveData.value = e.message
            }
        }
    }
}