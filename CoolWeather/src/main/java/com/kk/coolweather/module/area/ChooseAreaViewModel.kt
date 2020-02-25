package com.kk.coolweather.module.area

import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kk.coolweather.App
import com.kk.coolweather.repository.PlaceRepository
import com.kk.coolweather.model.place.City
import com.kk.coolweather.model.place.County
import com.kk.coolweather.model.place.Province
import com.kk.coolweather.module.area.ChooseAreaFragment.Companion.LEVER_CITY
import com.kk.coolweather.module.area.ChooseAreaFragment.Companion.LEVER_COUNTY
import com.kk.coolweather.module.area.ChooseAreaFragment.Companion.LEVER_PROVINCE
import kotlinx.coroutines.launch

/**
 * Created by kk on 2019/11/25  15:04
 * ChooseAreaFragment对应的ViewModel
 * 需要构造中传PlaceRepository
 */
class ChooseAreaViewModel(private val repository: PlaceRepository) : ViewModel() {

    //当前省市区层级
    var currentLevel = MutableLiveData<Int>()

    var dataChanged = MutableLiveData<Int>()
    //是否在请求
    var isLoading = MutableLiveData<Boolean>()
    //地区是否选择
    var areaSelected = MutableLiveData<Boolean>()

    //当前选择的省份
    var selectedProvince: Province? = null
    //当前选择的城市
    var selectedCity: City? = null
    //当前选择的地区
    var selectedCounty: County? = null

    //所有的省
    lateinit var provinces: MutableList<Province>
    //所有的城市
    lateinit var cities: MutableList<City>
    //所有的地区
    lateinit var counties: MutableList<County>

    val dataList = ArrayList<String>() //列表展示名称的list

    // 获取所有省信息
    fun getProvinces() {
        currentLevel.value = LEVER_PROVINCE
        launch {
            provinces = repository.getProvinceList()
            dataList.addAll(provinces.map { it.provinceName })
            //Iterable.map{} 根据当前集合的元素中某一个属性单独再创建一个新的集合
        }
    }

    //获取当前省所有城市信息
    fun getCities() = selectedProvince?.let {
        currentLevel.value = LEVER_CITY
        launch {
            cities = repository.getCityList(it.provinceCode)
            dataList.addAll(cities.map { it.cityName })
        }
    }

    //获取当前城市所有地区信息
    private fun getCounties() = selectedCity?.let {
        currentLevel.value = LEVER_COUNTY
        launch {
            counties = repository.getCountyList(it.provinceId, it.cityCode)
            dataList.addAll(counties.map { it.countyName })
        }
    }

    /*
      <*>指不确定当前泛型类型 *只能出现在泛型形参的位置

      泛型中什么时候用<? extends T> 什么时候用 <? super T>
      当使用泛型类作为生产者 需要从泛型中取数据时 使用extends 此时泛型类是协变的
      当使用泛型类作为消费者 需要从泛型类中写数据时 使用super 此时泛型类是逆变的

      kotlin中泛型与java仅仅只是写法和关键字的区别
      可参考 Collections中的copy源码
     */
    fun onListViewItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {

        when {
            currentLevel.value == LEVER_PROVINCE -> {
                selectedProvince = provinces[position]
                getCities()
            }

            currentLevel.value == LEVER_CITY -> {
                selectedCity = cities[position]
                getCounties()
            }

            currentLevel.value == LEVER_COUNTY -> {
                selectedCounty = counties[position]
                areaSelected.value = true
            }
        }

    }

    fun onBack() {
        if (currentLevel.value == LEVER_COUNTY) {
            getCities()
        } else if (currentLevel.value == LEVER_CITY) {
            getProvinces()
        }
    }

    /*
        在不阻塞当前线程的情况下启动一个新携程  并以Job返回对携程的引用
        ViewModel的方法 viewModelScope.launch {
        }
        此段逻辑为
        网络请求前 将刷新状态初始化
        获取数据(异步执行 并挂起)
        dataChanged.value 发生改变会通知adapter刷新数据
        刷新UI
     */
    private fun launch(block: suspend () -> Unit) = viewModelScope.launch {

        try {
            isLoading.value = true
            dataList.clear()
            block()
            /*
                plus  相当于运算符重载 = x+x
                +:  plus
                -:  minus
                *:  times
                /:  div
             */
            dataChanged.value = dataChanged.value?.plus(1)
            isLoading.value = false
        } catch (t: Throwable) {
            t.printStackTrace()
            Toast.makeText(App.context, t.message, Toast.LENGTH_SHORT).show()
            dataChanged.value = dataChanged.value?.plus(1)
            isLoading.value = false
        }
    }


}