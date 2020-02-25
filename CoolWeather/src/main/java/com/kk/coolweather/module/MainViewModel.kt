package com.kk.coolweather.module

import androidx.lifecycle.ViewModel
import com.kk.coolweather.repository.WeatherRepository

/**
 * Created by kk on 2019/11/21  19:05
 * 主页的ViewModel
 * ViewModel不能直接进行new
 * 智能通过ViewModelProviders、ViewModelProvider.NewInstanceFactory来进行创建
 * 否则不能与Activity、Fragment的生命周期进行绑定
 */
class MainViewModel (private val repository: WeatherRepository):ViewModel(){

    fun isWeatherCached() = repository.isWeatherCached()
}