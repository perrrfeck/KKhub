package com.kk.hub.module.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import com.kk.hub.di.Injectable
import com.kk.hub.common.utils.KKDataBindingComponent

/**
 * Created by kk on 2019/10/28  14:40
 */
abstract class BaseFragment<T : ViewDataBinding> : Fragment(), Injectable {

    /**
     * 根据Fragment动态清理和获取binding对象
     */
    var binding by autoCleared<T>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {//kotlin中被用?修饰的意味着该变量可以为null 在调用该变量的方法时编译期会提示对null进行处理

        binding = DataBindingUtil.inflate(
            inflater, getLayoutId(),
            container, false, KKDataBindingComponent())
        onCreateView(binding?.root)
        return binding?.root
    }

    abstract fun onCreateView(mainView: View?)

    abstract fun getLayoutId(): Int

    open fun actionOpenByBrowser() {

    }

    open fun actionCopy() {

    }

    open fun actionShare() {

    }

    /**
     * Navigation 的页面跳转
     */
    fun navigationPopUpTo(view: View, args: Bundle?, actionId: Int, finishStack: Boolean) {
        val controller = Navigation.findNavController(view)
        controller.navigate(actionId,
            args, NavOptions.Builder().setPopUpTo(controller.graph.id, true).build())
        if (finishStack) {
            activity?.finish()
        }
    }

    fun enterFull() {
        activity?.window?.setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN, WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN)
    }

    fun exitFull() {
        activity?.window?.setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN, WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN)
    }

}

