package com.example.vcontachim.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vcontachim.fragment.VcontachimApplication
import com.example.vcontachim.models.Video
import kotlinx.coroutines.launch

class VideoViewModel : ViewModel() {

    val videoLiveData = MutableLiveData<Video>()
    val progressBarLiveData = MutableLiveData<Boolean>()
    val errorLiveData = MutableLiveData<String>()

    fun loadVideo() {
        viewModelScope.launch {
            try {
                progressBarLiveData.value = true

                val sharedPreferences = VcontachimApplication.context.getSharedPreferences(
                    "vcontachim",
                    Context.MODE_PRIVATE
                )

                val tookToken: String? = sharedPreferences.getString("auth", null)

                val video: Video = VcontachimApplication.vcontachimService.getVideo(
                    token = "Bearer ${tookToken!!}"
                )

                progressBarLiveData.value = false
                videoLiveData.value = video
            } catch (e: Exception) {
                progressBarLiveData.value = false
                errorLiveData.value = e.message
            }
        }
    }
}