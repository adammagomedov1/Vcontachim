package com.example.vcontachim.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vcontachim.VcontachimApplication
import com.example.vcontachim.models.SearchScreen
import kotlinx.coroutines.launch

class PeopleSearchViewModel : ViewModel() {

    val searchScreenLiveData = MutableLiveData<SearchScreen>()
    val processBarLiveData = MutableLiveData<Boolean>()
    val errorLiveData = MutableLiveData<String>()

    fun loadSearchScreen(text: String) {
        viewModelScope.launch {
            try {
                processBarLiveData.value = true

                val searchScreen = VcontachimApplication.vcontachimService.getHintsSearch(text)

                processBarLiveData.value = false
                searchScreenLiveData.value = searchScreen
            } catch (e: Exception) {
                processBarLiveData.value = false
                errorLiveData.value = e.message
            }
        }
    }
}