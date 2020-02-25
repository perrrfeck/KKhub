package com.kk.hub.common.net

import android.content.Context
import org.jetbrains.anko.toast

/**
 * Created by kk on 2019/10/31  15:26
 */
abstract class ResultTipObserver<T>(private val context: Context) : ResultObserver<T>() {

    override fun onInnerCodeError(code: Int, message: String) {
        super.onInnerCodeError(code, message)
    }

    override fun onError(e: Throwable) {
        super.onError(e)
        if (isNumeric(e.message)) {
            //第二个参数表达式当e.cause为null时 整个表达式返回值为null 并不会抛异常
            codeError(e.message!!.toInt(), e.cause?.message ?: "")
        }
    }

    private fun codeError(code: Int, message: String) {
        when (code) {
            401 -> context.toast("401错误可能:未授权|授权登录失败|登陆过期")
            402 -> context.toast("402请求参数异常")
            403 -> context.toast("403没权限")
            404 -> context.toast("404没找到请求")
            422 -> context.toast("422异常 提交数据格式不对")
            else -> context.toast(code.toString() + " : " + message)
        }
    }

    private fun isNumeric(str: String?): Boolean {
        if (str == null) {
            return false
        }
        var i = str.length
        while (--i >= 0) {
            if (!Character.isDigit(str[i])) {
                return false
            }
        }
        return true
    }
}