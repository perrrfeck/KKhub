package com.kk.coolweather.repository

import com.kk.coolweather.repository.db.PlaceDao
import com.kk.coolweather.repository.network.CoolWeatherNetWork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Created by kk on 2019/11/21  17:47
 */
class PlaceRepository
private constructor(
    private val placeDao: PlaceDao,
    private val network: CoolWeatherNetWork
) {

    companion object {
        private var instance: PlaceRepository? = null

        fun getInstance(placeDao: PlaceDao, network: CoolWeatherNetWork): PlaceRepository {
            if (instance == null) {
                synchronized(PlaceRepository::class.java) {
                    if (instance == null) instance =
                        PlaceRepository(placeDao, network)
                }
            }
            return instance!!
        }
    }

    /*
        被suspend修饰的方法只能通过携程调用
        withContext指定协成在IO线程
         从数据库获取 如果没有从网络请求
     */
    suspend fun getProvinceList() = withContext(Dispatchers.IO) {
        var list = placeDao.getProvinceList()
        if (list.isEmpty()) {
            list = network.fetchProvinceList()
            placeDao.saveProvinceList(list)
        }
        list
    }

    suspend fun getCityList(provinceId: Int) = withContext(Dispatchers.IO) {
        var list = placeDao.getCityList(provinceId)
        if (list.isEmpty()) {
            list = network.fetchCityList(provinceId)
            list.forEach { it.provinceId = provinceId }
            placeDao.saveCityList(list)
        }
        list
    }

    suspend fun getCountyList(provinceId: Int, cityId: Int) = withContext(Dispatchers.IO) {
        var list = placeDao.getCountyList(cityId)
        if (list.isEmpty()) {
            list = network.fetchCountyList(provinceId, cityId)
            list.forEach {
                it.cityId = cityId
                placeDao.saveCountyList(list)
            }
        }
        list
    }

}