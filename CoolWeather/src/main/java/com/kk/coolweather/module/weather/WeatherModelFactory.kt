package com.kk.coolweather.module.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kk.coolweather.repository.WeatherRepository

/**
 * Created by kk on 2019/11/21  19:02
 */
class WeatherModelFactory(private val repository: WeatherRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return WeatherViewModel(repository) as T
    }
}