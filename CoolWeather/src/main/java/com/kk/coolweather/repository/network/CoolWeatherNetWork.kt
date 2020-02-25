package com.kk.coolweather.repository.network

import com.kk.coolweather.repository.network.api.PlaceService
import com.kk.coolweather.repository.network.api.WeatherService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * Created by kk on 2019/11/21  16:57
 * 天气请求的网络层管理类 负责调度
 */
class CoolWeatherNetWork {

    companion object {
        //单例
        private var network: CoolWeatherNetWork? = null

        fun getInstance(): CoolWeatherNetWork {
            if (network == null) {
                synchronized(CoolWeatherNetWork::class.java) {
                    if (network == null) network = CoolWeatherNetWork()
                }
            }
            return network!!
        }
    }

    private val placeService = ServiceCreator.create(PlaceService::class.java)

    private val weatherService = ServiceCreator.create(WeatherService::class.java)

    suspend fun fetchProvinceList() = placeService.getProvinces().await()

    suspend fun fetchCityList(provinceId: Int) =
        placeService.getCities(provinceId).await()

    suspend fun fetchCountyList(provinceId: Int, cityId: Int) =
        placeService.getCounties(provinceId, cityId).await()

    suspend fun fetchWeather(weatherId:String) = weatherService.getWeather(weatherId).await()

    suspend fun fetchBingPic() = weatherService.getBingPic().await()

    /*
        这是一个Call的扩展函数
        以特殊修饰符suspend修饰的函数被称为挂起函数。
        挂起函数只能在协程中和其他挂起函数中调用，不能在其他部分使用
        所以要启动一个协程，挂起函数是必须的

        suspend函数本质是异步返回 而不是通过回调的方式对数据进行操作
        实际获取异步数据的代码写法看上去跟同步代码一样
     */
    private suspend fun <T> Call<T>.await(): T {
        return suspendCoroutine { continuation ->
            enqueue(object : Callback<T> {
                override fun onFailure(call: Call<T>, t: Throwable) {
                    continuation.resumeWithException(t)
                }

                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
                    if (body != null) continuation.resume(body)
                    else continuation.resumeWithException(RuntimeException("response body is null"))
                }
            })
        }
    }


}