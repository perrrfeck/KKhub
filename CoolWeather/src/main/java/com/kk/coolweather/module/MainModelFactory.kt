package com.kk.coolweather.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kk.coolweather.repository.WeatherRepository

/**
 * Created by kk on 2019/11/21  19:07
 * 该类实现了ViewModelProvider.NewInstanceFactory提供创建MainViewModel的方法
 */
class MainModelFactory(private val repository: WeatherRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(repository) as T
    }

}