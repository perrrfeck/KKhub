package com.kk.coolweather.repository.db

/**
 * Created by kk on 2019/11/21  16:21
 */
object CoolWeatherDatabase {

    private var placeDao: PlaceDao? = null

    private var weatherDao: WeatherDao? = null

    fun getPlaceDao(): PlaceDao {
        if (placeDao == null) {
            placeDao = PlaceDao()
        }
        return placeDao!!
    }

    fun getWeatherDao(): WeatherDao {
        if (weatherDao == null) {
            weatherDao = WeatherDao()
        }
        return weatherDao!!
    }


}