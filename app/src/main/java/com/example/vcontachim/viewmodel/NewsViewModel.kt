package com.example.vcontachim.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vcontachim.VcontachimApplication
import com.example.vcontachim.models.Group
import com.example.vcontachim.models.NewsUi
import com.example.vcontachim.models.ProfileNews
import kotlinx.coroutines.launch
import kotlin.math.abs

class NewsViewModel : ViewModel() {

    val newsLiveData = MutableLiveData<List<NewsUi>>()
    val errorLiveData = MutableLiveData<String>()

    fun loadMainScreen() {
        viewModelScope.launch {
            try {

                val news = VcontachimApplication.vcontachimService.getNewsfeed()

                val newsList = news.response.items.filter {
                    it.attachments.getOrNull(0)?.type == "photo"
                }

                val newsUi = newsList.map {
                    val group: Group? =
                        news.response.groups.firstOrNull { group -> group.id == abs(it.sourceId) }

                    val profile: ProfileNews? =
                        news.response.profiles.firstOrNull { profile -> profile.id == abs(it.sourceId) }

                    val newsUi = NewsUi(
                        countComment = it.comments.count,
                        date = it.date,
                        sourceId = it.sourceId,
                        countLike = it.likes.count,
                        countReposts = it.reposts.count,
                        text = it.text,
                        countViews = it.views.count,
                        userLikes = it.likes.userLikes,
                        name = group?.name ?: "${profile?.firstName} ${profile?.firstName}",
                        photo200 = group?.photo200 ?: profile!!.photo100,
                        groupId = group?.id ?: profile!!.id,
                        url = it.attachments.getOrNull(0)?.photo?.sizes?.getOrNull(5)?.url,
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