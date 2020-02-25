package com.kk.coolweather.utils

import com.kk.coolweather.repository.PlaceRepository
import com.kk.coolweather.repository.WeatherRepository
import com.kk.coolweather.repository.db.CoolWeatherDatabase
import com.kk.coolweather.repository.network.CoolWeatherNetWork
import com.kk.coolweather.module.area.ChooseAreaModelFactory
import com.kk.coolweather.module.MainModelFactory
import com.kk.coolweather.module.weather.WeatherModelFactory

/**
 * Created by kk on 2019/11/21  18:59
 *
 * 依赖注入工具类  提供全局对象创建
 * 依赖注入有很多种形式: get、set 方式 通过构造传递 (强耦合)
 *
 * 更高级的用法使用Dagger2
 * 弱耦合,使用编译期注解 apt 自动生产代码 通过接口的方式来隔离 小型项目慎用
 * 除非在构造某些对象时需要做非常大量的操作 需要用到(这种方式也可以通过工厂模式实现 不一定非要用dagger2)
 *
 * 这一层的抽取是为了隔离创建对象的过程 实际上代码并没有改变耦合度 因为创建对象过程较单一
 */
object InjectorUtil {

    private fun getPlaceRepository() = PlaceRepository.getInstance(
        CoolWeatherDatabase.getPlaceDao(), CoolWeatherNetWork.getInstance()
    )

    private fun getWeatherRepository() = WeatherRepository.getInstance(
        CoolWeatherDatabase.getWeatherDao(), CoolWeatherNetWork.getInstance()
    )

    fun getWeatherModelFactory() = WeatherModelFactory(getWeatherRepository())

    fun getChooseAreaModelFactory() = ChooseAreaModelFactory(getPlaceRepository())

    fun getMainModelFactory() = MainModelFactory(getWeatherRepository())


}