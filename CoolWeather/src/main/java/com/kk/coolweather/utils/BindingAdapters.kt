package com.kk.coolweather.utils

import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.kk.coolweather.R
import com.kk.coolweather.databinding.ForecastItemBinding
import com.kk.coolweather.model.weather.Weather

/**
 * Created by kk on 2019/11/26  17:38
 * 一些自定义DataBinding
 */

//使用glide加载图片
@BindingAdapter("bind:loadBingPic")
fun ImageView.loadBingPic(url: String?) {
    if (url != null) Glide.with(context).load(url).into(this)
}

//设置swipeRefreshLayout 颜色
@BindingAdapter("bind:colorSchemeResources")
fun SwipeRefreshLayout.colorSchemeResources(resId: Int) {
    setColorSchemeResources(resId)
}

//
@BindingAdapter("bind:showForecast")
fun LinearLayout.showForecast(weather: Weather?) = weather?.let {
    removeAllViews()
    //遍历Weather中 forecastList 集合 类型为Forecast
    for (forecast in it.forecastList) {
        val view: View = LayoutInflater.from(context).inflate(R.layout.forecast_item, this, false)
        DataBindingUtil.bind<ForecastItemBinding>(view)?.forecast = forecast
        addView(view)
    }
}