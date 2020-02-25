package com.kk.hub.module.main

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.kk.hub.R
import com.kk.hub.module.service.LocalService

/**
 * Created by kk on 2019/10/28  09:59
 * 程序入口
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //默认Splash停留一秒
        Handler().postDelayed({
            setContentView(R.layout.activity_startnavigation)
        }, 1000)
        //启动后台服务测试AIDL
        startService(Intent(this, LocalService::class.java))
    }

    override fun onBackPressed() {

        val fragment = supportFragmentManager.primaryNavigationFragment
        if (fragment is NavHostFragment) {
            if (fragment.navController.currentDestination?.id == R.id.loginFragment) {
                super.onBackPressed()
            }
        }
    }

}