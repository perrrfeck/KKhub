package com.kk.hub.common.net

/**
 * Created by kk on 2019/10/31  10:12
 */
interface ResultCallBack<T> {

    fun onPage(first:Int,current:Int,last:Int){}

    fun onSuccess(result:T?)

    fun onCacheSuccess(result:T?){}

    fun onFailure(){

    }
}