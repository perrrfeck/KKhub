package com.kk.coolweather.repository.db

import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.google.gson.Gson
import com.kk.coolweather.App
import com.kk.coolweather.model.weather.Weather

/**
 * Created by kk on 2019/11/21  16:28
 * 本地持久化天气数据具体实现层SharedPreferences
 */
class WeatherDao {

    companion object {
        const val SP_WEATHER = "weather"
        const val SP_PIC_BING = "bing_pic"
    }

    fun cacheWeatherInfo(weather: Weather?) {
        if (weather == null) return
        PreferenceManager.getDefaultSharedPreferences(App.context).edit {
            val weatherInfo = Gson().toJson(weather)
            putString(SP_WEATHER, weatherInfo)
        }
    }

    fun getCachedWeatherInfo(): Weather? {
        val weatherInfo = PreferenceManager.getDefaultSharedPreferences(App.context)
            .getString(SP_WEATHER, null)
        if (weatherInfo != null) {
            return Gson().fromJson(weatherInfo, Weather::class.java)
        }
        return null
    }

    fun cacheBingPic(bingPic: String?) {
        if (bingPic == null) return
        PreferenceManager.getDefaultSharedPreferences(App.context).edit {
            putString("bing_pic", bingPic)
        }
    }

    fun getCachedBingPic(): String? =
        PreferenceManager.getDefaultSharedPreferences(App.context).getString(SP_PIC_BING, null)


    private fun SharedPreferences.edit(action: SharedPreferences.Editor.() -> Unit) {
        val edit = edit()
        action(edit)
        edit.apply()
    }
}