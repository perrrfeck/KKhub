package com.kk.coolweather.module.weather

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kk.coolweather.App
import com.kk.coolweather.repository.WeatherRepository
import com.kk.coolweather.model.weather.Weather
import kotlinx.coroutines.launch

/**
 * Created by kk on 2019/11/26  16:34
 */
class WeatherViewModel(private val repository: WeatherRepository) : ViewModel() {
    //天气数据
    var weather = MutableLiveData<Weather>()
    //bing图片
    var bingPicUrl = MutableLiveData<String>()
    //刷新状态
    var refreshing = MutableLiveData<Boolean>()
    //初始化完毕标识
    var weatherInitialized = MutableLiveData<Boolean>()
    //天气code
    var weatherId = ""

    /*
        launch()方法不是阻塞的
        会继续执行getBindPic()

        获取天气信息和图片信息
     */
    fun getWeather() {
        launch({
            weather.value = repository.getWeather(weatherId)
            weatherInitialized.value = true
        }, {
            Toast.makeText(App.context, it.message, Toast.LENGTH_SHORT).show()
        })
        getBindPic(false)
    }

    fun refreshWeather() {
        refreshing.value = true
        launch({
            weather.value = repository.refreshWeather(weatherId)
            refreshing.value = false
            weatherInitialized.value = true
        }, {
            Toast.makeText(App.context, it.message, Toast.LENGTH_SHORT).show()
            refreshing.value = false
        })
        getBindPic(true)
    }

    fun isWeatherCached() = repository.isWeatherCached()

    fun getCachedWeather() = repository.getCachedWeather()

    //xml中SwipeRefreshLayout 的OnRefreshListener指向了viewModel此方法
    fun onRefresh() {
        refreshWeather()
    }

    /**
     * 获取图片信息
     * 如果状态为刷新 刷新图片
     * 如果不是刷新 获取缓存图片
     */
    private fun getBindPic(refresh: Boolean) {
        launch({
            bingPicUrl.value = if (refresh) repository.refreshBingPic()
            else repository.getBingPic()
        },
            { Toast.makeText(App.context, it.message, Toast.LENGTH_SHORT).show() })
    }

    /**
     * 启动一个请求
     * 执行block()方法
     * 发生异常时执行error()方法
     */
    private fun launch(block: suspend () -> Unit, error: suspend (Throwable) -> Unit) =
        viewModelScope.launch {
            try {
                block()
            } catch (e: Throwable) {
                error(e)
            }
        }
}