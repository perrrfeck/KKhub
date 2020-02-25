package com.kk.hub.module.main

import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

/**
 * Created by kk on 2019/10/28  16:47
 */
class HomeActivity : AppCompatActivity(), HasSupportFragmentInjector,
    Toolbar.OnMenuItemClickListener {

    companion object {
        init {
//            System.loadLibrary("native-gsy")
        }
    }

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>


    override fun supportFragmentInjector(): AndroidInjector<Fragment> = dispatchingAndroidInjector

    override fun onMenuItemClick(item: MenuItem?): Boolean {

        return true
    }
}