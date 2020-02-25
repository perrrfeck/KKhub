package com.kk.hub.common.net

import android.accounts.NetworkErrorException
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import retrofit2.Response
import java.net.ConnectException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException

/**
 * Created by kk on 2019/10/31  15:01
 */
abstract class ResultObserver<T> : Observer<Response<T>> {

    override fun onSubscribe(d: Disposable) {
        if (!d.isDisposed) {
            onRequestStart()
        }
    }

    override fun onNext(t: Response<T>) {
        onPageInfo(t)
        onRequestEnd()
        if (t.isSuccessful) {
            try {
                onSuccess(t.body())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            try {
                onInnerCodeError(t.code(), t.message())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onError(e: Throwable) {
        onRequestEnd()
        try {
            if (e is ConnectException
                || e is TimeoutException
                || e is NetworkErrorException
                || e is UnknownHostException
            ) {
                onFailure(e, true)
            } else {
                onFailure(e, false)
            }
        } catch (e1: Exception) {
            e1.printStackTrace()
        }
    }

    override fun onComplete() {}

    open fun onRequestStart() {

    }

    open fun onPageInfo(first: Int, current: Int, last: Int) {

    }

    open fun onRequestEnd() {

    }

    open fun onInnerCodeError(code: Int, message: String) {

    }

    @Throws(Exception::class)
    open fun onCodeError(code: Int, message: String) {

    }

    fun onPageInfo(response: Response<T>) {
        val pageString = response.headers().get("page_info")
        if (pageString != null) {
            val pageInfo = GsonUtils.parserJsonToBean(pageString, PageInfo::class.java)
            onPageInfo(pageInfo.first, pageInfo.next - 1, pageInfo.last)
        }
    }

    /**
     * 返回成功
     */
    @Throws(Exception::class)
    abstract fun onSuccess(result: T?)

    @Throws(Exception::class)
    abstract fun onFailure(e: Throwable, isNetWorkError: Boolean)

}