package com.kk.coolweather.module.area

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kk.coolweather.repository.PlaceRepository

/**
 * Created by kk on 2019/11/25  15:02
 */
class ChooseAreaModelFactory  (private val repository:PlaceRepository)
    :ViewModelProvider.NewInstanceFactory(){

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ChooseAreaViewModel(repository) as T
    }
}