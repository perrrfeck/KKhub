package com.kk.hub.module.base

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Created by kk on 2019/10/28  14:42
 * fragment销毁时会自动清除的 懒执行属性
 * 在已销毁的fragment访问此属性时会抛空指针异常
 */
class AutoClearedValue<T : Any?>(val fragment: Fragment) : ReadWriteProperty<Fragment, T?> {

    private var _value: T? = null

    //执行构造时执行此逻辑
    init {
        fragment.lifecycle.addObserver(object : LifecycleObserver {
            @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            fun onDestroy() {
                _value = null
            }
        })
    }


    override fun getValue(thisRef: Fragment, property: KProperty<*>): T? {
        return _value
    }

    override fun setValue(thisRef: Fragment, property: KProperty<*>, value: T?) {
        _value = value
    }
}

/**
 * Fragment的扩展函数 只能被Fragment调用 返回一个与fragment关联的value
 * Creates an [AutoClearedValue] associated with this fragment.
 */
fun <T : Any?> Fragment.autoCleared() = AutoClearedValue<T?>(this)