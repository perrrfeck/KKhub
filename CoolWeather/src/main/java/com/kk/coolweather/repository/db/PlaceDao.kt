package com.kk.coolweather.repository.db

import com.kk.coolweather.model.place.City
import com.kk.coolweather.model.place.County
import com.kk.coolweather.model.place.Province
import org.litepal.LitePal

/**
 * Created by kk on 2019/11/21  16:21
 *
 * 地区信息数据库实现 应该在异步调用
 */
class PlaceDao {

    fun getProvinceList(): MutableList<Province> =
        LitePal.findAll(Province::class.java)

    fun getCityList(provinceId: Int): MutableList<City> =
        LitePal.where("provinceId = ?", provinceId.toString()).find(City::class.java)

    fun getCountyList(cityId:Int):MutableList<County> =
        LitePal.where("cityId = ?",cityId.toString()).find(County::class.java)

    fun saveProvinceList(provinceList: List<Province>?) {
        if (provinceList != null && provinceList.isNotEmpty()) {
            LitePal.saveAll(provinceList)
        }
    }

    fun saveCityList(cityList: List<City>?) {
        if (cityList != null && cityList.isNotEmpty()) {
            LitePal.saveAll(cityList)
        }
    }

    fun saveCountyList(countyList: List<County>?) {
        if (countyList != null && countyList.isNotEmpty()) {
            LitePal.saveAll(countyList)
        }
    }

}