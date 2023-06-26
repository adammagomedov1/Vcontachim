package com.example.vcontachim.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vcontachim.VcontachimApplication
import com.example.vcontachim.models.*
import kotlinx.coroutines.launch
import kotlin.math.abs

class NewsViewModel : ViewModel() {
    val progressBarLiveData = MutableLiveData<Boolean>()
    val newsUiLiveData = MutableLiveData<List<NewsUi>>()
    val errorLiveData = MutableLiveData<String>()

    fun loadNews(enumNews: EnumNews) {
        viewModelScope.launch {
            try {
                progressBarLiveData.value = true
                val news = if (enumNews != EnumNews.RECOMMENDED) {
                    VcontachimApplication.vcontachimService.getNewsfeed()
                } else {
                    VcontachimApplication.vcontachimService.getRecommendedNewsfeed()
                }

                //Я здесь сохроняю в переменую отфильтрованные фильтрую посты
                val newsList = news.response.items.filter {
                    it.attachments.getOrNull(0)?.type == "photo"
                }

                val newsUi = newsList.map { it ->
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
                        id = it.id,
                        attachments = it.attachments.filter { attachment -> attachment.type == "photo" }
                    )
                    newsUi
                }

                progressBarLiveData.value = false
                newsUiLiveData.value = newsUi
            } catch (e: Exception) {
                progressBarLiveData.value = false
                errorLiveData.value = e.message
            }
        }
    }

    fun addLike(newsUi: NewsUi) {
        viewModelScope.launch {
            try {

                VcontachimApplication.vcontachimService.addLikePost(
                    itemId = newsUi.id,
                    ownerId = newsUi.sourceId
                )

                val newsUiList = newsUiLiveData.value!!.toMutableList()
                // обновляем элемент коментария на котором был клик
                val updatedNewsObject: NewsUi = newsUi.copy(
                    userLikes = if (newsUi.userLikes == 1L) 0 else 1,
                    countLike = newsUi.countLike + 1
                )

                val saveIndexFromList = newsUiList.indexOf(newsUi)
                newsUiList.set(index = saveIndexFromList, updatedNewsObject)

                newsUiLiveData.value = newsUiList
            } catch (e: Exception) {
                errorLiveData.value = e.message
            }
        }
    }

    fun deleteLike(newsUi: NewsUi) {
        viewModelScope.launch {
            try {

                VcontachimApplication.vcontachimService.deleteLikePost(
                    itemId = newsUi.id,
                    ownerId = newsUi.sourceId
                )

                val newsUiList = newsUiLiveData.value!!.toMutableList()
                // обновляем элемент коментария на котором был клик
                val updatedNewsObject: NewsUi = newsUi.copy(
                    userLikes = if (newsUi.userLikes == 1L) 0 else 1,
                    countLike = newsUi.countLike - 1
                )

                val saveIndexFromList = newsUiList.indexOf(newsUi)
                newsUiList.set(index = saveIndexFromList, updatedNewsObject)

                newsUiLiveData.value = newsUiList
            } catch (e: Exception) {
                errorLiveData.value = e.message
            }
        }
    }
}