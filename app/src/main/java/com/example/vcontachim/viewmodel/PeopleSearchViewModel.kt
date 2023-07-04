package com.example.vcontachim.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vcontachim.VcontachimApplication
import com.example.vcontachim.models.PeopleSearchUi
import kotlinx.coroutines.launch

class PeopleSearchViewModel : ViewModel() {

    val searchPeopleSearch = MutableLiveData<List<PeopleSearchUi>>()
    val processBarLiveData = MutableLiveData<Boolean>()
    val errorLiveData = MutableLiveData<String>()

    fun loadPeopleSearch(text: String) {
        viewModelScope.launch {
            try {
                processBarLiveData.value = text.isEmpty()

                val searchScreen = VcontachimApplication.vcontachimService.getHintsSearch(text)

                val searchScreenFilter = searchScreen.response.items.filter {
                    it.type == "profile"
                }

                val peopleSearchUi = searchScreenFilter.map {
                    val peopleSearchUi = PeopleSearchUi(
                        maxPhoto = it.profile!!.photoMax,
                        id = it.profile.id,
                        firstName = "${it.profile.firstName} ${it.profile.lastName}",
                        description = it.description,
                        isFriend = it.profile.isFriend,
                        bdate = it.profile.bdate,
                        cityTitle = it.profile.city?.title,
                        online = it.profile.online
                    )
                    peopleSearchUi
                }

                searchPeopleSearch.value = peopleSearchUi
            } catch (e: Exception) {
                errorLiveData.value = e.message
            } finally {
                processBarLiveData.value = false
            }
        }
    }

    fun deleteSearchList() {
        val emptyList = emptyList<PeopleSearchUi>()
        searchPeopleSearch.value = emptyList
    }

    fun addFriends(peopleSearchUi: PeopleSearchUi) {
        viewModelScope.launch {
            try {

                VcontachimApplication.vcontachimService.addFriend(peopleSearchUi.id)

                val listPeopleSearchUi = searchPeopleSearch.value!!.toMutableList()
                // обновляем элемент коментария на котором был клик
                val updatedPeopleSearchUi: PeopleSearchUi = peopleSearchUi.copy(
                    isFriend = if (peopleSearchUi.isFriend == 1) 0 else 1
                )

                val saveIndexFromList = listPeopleSearchUi.indexOf(peopleSearchUi)
                listPeopleSearchUi.set(index = saveIndexFromList, updatedPeopleSearchUi)

                searchPeopleSearch.value = listPeopleSearchUi
            } catch (e: Exception) {
                errorLiveData.value = e.message
            }
        }
    }

    fun deleteFriends(peopleSearchUi: PeopleSearchUi) {
        viewModelScope.launch {
            try {

                VcontachimApplication.vcontachimService.deleteFriend(peopleSearchUi.id)

                val listPeopleSearchUi = searchPeopleSearch.value!!.toMutableList()
                // обновляем элемент коментария на котором был клик
                val updatedPeopleSearchUi: PeopleSearchUi = peopleSearchUi.copy(
                    isFriend = if (peopleSearchUi.isFriend == 1) 0 else 1
                )

                val saveIndexFromList = listPeopleSearchUi.indexOf(peopleSearchUi)
                listPeopleSearchUi.set(index = saveIndexFromList, updatedPeopleSearchUi)

                searchPeopleSearch.value = listPeopleSearchUi
            } catch (e: Exception) {
                errorLiveData.value = e.message
            }
        }
    }
}