package com.example.vcontachim.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vcontachim.VcontachimApplication
import com.example.vcontachim.models.Group
import com.example.vcontachim.models.NewsUi
import kotlinx.coroutines.launch

class NewsViewModel : ViewModel() {

    val newsLiveData = MutableLiveData<List<NewsUi>>()
    val errorLiveData = MutableLiveData<String>()

    fun loadMainScreen() {
        viewModelScope.launch {
            try {

                val news = VcontachimApplication.vcontachimService.getNewsfeed()

                val newsUi = news.response.items.map {
                    val group: Group =
                        news.response.groups.first { group -> group.id == it.sourceId }

                    val newsUi = NewsUi(
                        countComment = it.comments.count,
                        date = it.date,
                        url = it.attachments[0].photo.sizes[0].url,
                        sourceId = it.sourceId,
                        ownerId = it.attachments[0].photo.ownerId,
                        countLike = it.likes.count,
                        countReposts = it.reposts.count,
                        text = it.text,
                        countViews = it.views.count,
                        userLikes = it.likes.userLikes,
                        name = group.name,
                        photo200 = group.photo200,
                        groupId = group.id
                    )
                    newsUi
                }
                newsLiveData.value = newsUi
            } catch (e: Exception) {
                errorLiveData.value = e.message
            }
        }
    }
}