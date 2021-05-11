package com.example.meteoapplication.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import timber.log.Timber

class MainViewModel: ViewModel() {

    enum class Navigation {
        CLICK_WEATHER,
        WAITING
    }

    private var _navigation = MutableLiveData<Navigation>()
    val navigation: LiveData<Navigation>
        get() = this._navigation

    init {
        Timber.i("test")
    }

    fun onClickButtonWeather(){
        _navigation.value = Navigation.CLICK_WEATHER
    }

    fun onButtonClicked(){
        _navigation.value = Navigation.WAITING
    }
}