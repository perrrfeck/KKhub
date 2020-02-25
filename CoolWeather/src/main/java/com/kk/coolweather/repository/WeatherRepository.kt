package com.kk.coolweather.repository

import com.kk.coolweather.repository.db.WeatherDao
import com.kk.coolweather.model.weather.Weather
import com.kk.coolweather.repository.network.CoolWeatherNetWork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Created by kk on 2019/11/21  18:36
 *
 * 天气数据处理的抽象层Repository
 * 包含网络请求 本地数据库持久化操作
 */
class WeatherRepository
private constructor(
    private val weatherDao: WeatherDao,
    private val network: CoolWeatherNetWork
) {

    companion object {
        private lateinit var instance: WeatherRepository

        fun getInstance(weatherDao: WeatherDao, network: CoolWeatherNetWork): WeatherRepository {
            if (!::instance.isInitialized) {
                synchronized(WeatherRepository::class.java) {
                    if (!::instance.isInitialized) {
                        instance = WeatherRepository(weatherDao, network)
                    }
                }
            }
            return instance
        }

    }

    //从数据库获取天气 如果没有从网络请求
    suspend fun getWeather(weatherId: String): Weather {
        var weather = weatherDao.getCachedWeatherInfo()
        if (weather == null) weather = requestWeather(weatherId)
        return weather
    }

    //刷新天气信息 并存入数据库
    suspend fun refreshWeather(weatherId: String) = requestWeather(weatherId)

    //获取图片
    suspend fun getBingPic(): String {
        var url = weatherDao.getCachedBingPic()
        if (url == null) url = requestBingPic()
        return url
    }

    //刷新图片
    suspend fun refreshBingPic() = requestBingPic()

    //是否有缓存
    fun isWeatherCached() = weatherDao.getCachedWeatherInfo() != null

    //获取天气缓存信息
    fun getCachedWeather() = weatherDao.getCachedWeatherInfo()!!

    /*
        网络请求天气信息 存入数据库并返回
     */
    private suspend fun requestWeather(weatherId: String) = withContext(Dispatchers.IO) {
        val heWeather = network.fetchWeather(weatherId)
        val weather = heWeather.weather!![0]
        weatherDao.cacheWeatherInfo(weather)
        weather
    }

    /*
        网络请求图片 缓存数据库 并返回url
     */
    private suspend fun requestBingPic() = withContext(Dispatchers.IO) {
        val url = network.fetchBingPic()
        weatherDao.cacheBingPic(url)
        url
    }

}

