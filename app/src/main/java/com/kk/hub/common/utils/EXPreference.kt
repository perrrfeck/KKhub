package com.kk.hub.common.utils

import android.content.Context
import android.content.SharedPreferences
import com.kk.hub.App
import kotlin.reflect.KProperty

/**
 * Created by kk on 2019/10/29  16:48
 */
class EXPreference<T>
    (private val keyName: String, private val default: T) {

    /**
     * lazy属于一个函数,接收一个lambda函数式作为参数,返回一个Lazy<T>实例的函数，返回的实例可以作为实现延迟属性的委托
     * 第一次调用get()会执行已传递lambda表达式并记录结果,后续调用get()仅仅返回记录的结果
     *
     * 以下代码实现效果为第一次调用会执行lambda函数式  第二次调用以后会直接返回结果
     *
     * lazy操作是线程安全的
     */
    private val prefs: SharedPreferences  by lazy {
        App.instance.getSharedPreferences(keyName, Context.MODE_PRIVATE)
    }

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return getSharePreferences(keyName, default)
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        putSharePreferences(keyName, value)
    }

    private fun putSharePreferences(name: String, value: T) =
        /*
             内联函数with 接收两个参数,分别为T类型对象和一个lambda的函数快,由于with参数最后一个参数是一个函数,
             可以把函数提到圆括号的外部 是用于调用同一个类多个方法时,可以省去类名重复,直接调用类方法即可
         */
        with(prefs.edit()) {
            when (value) {
                is Long -> putLong(name, value)
                is String -> putString(name, value)
                is Int -> putInt(name, value)
                is Boolean -> putBoolean(name, value)
                is Float -> putFloat(name, value)
                else -> throw IllegalArgumentException("Type Error, cannot be saved!")
            }.apply()
        }

//    @Suppress("UNCHECKED_CAST")
//    private fun getSharePreferences(name: String, default: T): T =
//        with(prefs) {
//            val res: Any = when (default) {
//                is Long -> getLong(name, default)
//                is Int -> getInt(name, default)
//                is String -> getString(name, default)
//                is Boolean -> getBoolean(name, default)
//                is Float -> getFloat(name, default)
//                else -> throw IllegalArgumentException("!!!")
//            }
//            return res as T
//        }


    @Suppress("UNCHECKED_CAST")
    private fun getSharePreferences(name: String, default: T): T {
        with(prefs) {
            val res: Any = when (default) {
                is Long -> getLong(name, default)
                is Int -> getInt(name, default)
                is String -> getString(name, default)
                is Boolean -> getBoolean(name, default)
                is Float -> getFloat(name, default)
                else -> throw IllegalArgumentException("!!!")
            }
            return res as T
        }
    }

}