package com.kk.hub

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import com.alibaba.android.arouter.launcher.ARouter
import com.kk.hub.repository.dao.RealmFactory
import com.mikepenz.iconics.Iconics
import com.mikepenz.materialdrawer.util.AbstractDrawerImageLoader
import com.mikepenz.materialdrawer.util.DrawerImageLoader
import io.realm.Realm
import kotlin.properties.Delegates

/**
 *  Created by kk
 *
 *  common包下对应的一些配置、基础功能、util
 *  di包下对应的是一些依赖注入的管理类(可用可不用)
 *  model包下对应的是所有相关实体类 entity(本地持久层)、bean(网络层)、vo(渲染层)
 *  module对应的业务层包含所有的Activity、Fragment、ViewModel、service
 *  repository包对应的是所有数据实现层dao(数据库)、dto(网络层)
 *  ui包对应的所有控件
 *  webservice对应所有的服务器请求信息配置
 *
 */
class App : Application() {

    /**
     * by 关键字属于委托模式 将App 委托给Delegates
     * 可自定义委托对象
     * by关键字后的表达式就是委托属性的get以及set方法将被委托给这个Delegates对象的getValue和setValue方法
     * 属性委托不必实现任何接口
     * 但必须提供getValue、setValue方法 且参数 thisRef 为进行委托的类的对象，prop 为进行委托的属性的对象。
     * 委托对象
     */
    companion object {
        var instance: App by Delegates.notNull()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        if (BuildConfig.DEBUG) {
            ARouter.openLog()
            ARouter.openDebug()
        }
        ARouter.init(this)

        //Application级别注入
//        AppInjector.init(this)

        Iconics.init(applicationContext)
//        Iconics.registerFont() 字体库

        Realm.init(this)
        RealmFactory.instance


        DrawerImageLoader.init(object : AbstractDrawerImageLoader() {

            override fun placeholder(ctx: Context): Drawable {
                return super.placeholder(ctx)
            }

            override fun placeholder(ctx: Context, tag: String?): Drawable {
                return super.placeholder(ctx, tag)
            }

            override fun set(imageView: ImageView, uri: Uri, placeholder: Drawable, tag: String?) {
                super.set(imageView, uri, placeholder, tag)
            }
        })

    }
}