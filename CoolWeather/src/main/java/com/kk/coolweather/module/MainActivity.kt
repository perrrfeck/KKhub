package com.kk.coolweather.module

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.kk.coolweather.R
import com.kk.coolweather.module.area.ChooseAreaFragment
import com.kk.coolweather.module.weather.WeatherActivity
import com.kk.coolweather.utils.InjectorUtil


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //获取mainViewModel mainViewModel生命周期会随MainActivity一致
        val viewModel = ViewModelProviders.of(this, InjectorUtil.getMainModelFactory())
            .get(MainViewModel::class.java)
        /*
            是否有缓存天气 如果有缓存进入天气详情页
            如果没有进入地区选择页
         */
        if (viewModel.isWeatherCached()) {
            val intent = Intent(this, WeatherActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            supportFragmentManager.beginTransaction().replace(R.id.container, ChooseAreaFragment())
                .commit()
        }
    }

}
