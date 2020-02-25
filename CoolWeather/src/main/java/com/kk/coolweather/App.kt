package com.kk.coolweather

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import org.litepal.LitePal

/**
 * Created by kk on 2019/11/21  15:39
 */
class App : Application() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context //context只能在onCreate()中赋值 所以只能懒加载
    }

    override fun onCreate() {
        super.onCreate()
        LitePal.initialize(this)
        context = this
    }

}