package com.kk.coolweather.module.weather

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.kk.coolweather.R
import com.kk.coolweather.databinding.ActivityWeatherBinding
import com.kk.coolweather.module.area.ChooseAreaFragment.Companion.WEATHER_ID
import com.kk.coolweather.utils.InjectorUtil
import kotlinx.android.synthetic.main.activity_weather.*
import kotlinx.android.synthetic.main.title.*

/**
 * Created by kk on 2019/11/21  19:10
 */
class WeatherActivity : AppCompatActivity() {

    val viewModel by lazy {
        ViewModelProviders.of(this, InjectorUtil.getWeatherModelFactory())
            .get(WeatherViewModel::class.java)
    }

    private val binding by lazy {
        DataBindingUtil.setContentView<ActivityWeatherBinding>(this, R.layout.activity_weather)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        if (Build.VERSION.SDK_INT >= 21) {//申请全屏 状态栏透明
            val decorView: View = window.decorView
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            window.statusBarColor = Color.TRANSPARENT
        }
        //将viewModel实例注入到 dataBinding中
        binding.viewModel = viewModel
        binding.resId = R.color.colorPrimary
        //需要调用binding.setLifecycleOwner()方法 xmk中的控件才能根于绑定的数据源发生变化
        binding.setLifecycleOwner(this)
        //如果有缓存 从缓存中获取weatherId 没有缓存从intent中获取
        viewModel.weatherId = if (viewModel.isWeatherCached()) {
            viewModel.getCachedWeather().basic.weatherId
        } else {
            intent.getStringExtra(WEATHER_ID)
        }
        navButton.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }
        viewModel.getWeather()

    }
}