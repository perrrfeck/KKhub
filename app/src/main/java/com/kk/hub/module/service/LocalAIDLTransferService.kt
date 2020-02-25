package com.kk.hub.module.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.kk.hub.ILocalMessage
import com.kk.hub.ILocalMessageCallBack
import com.kk.hub.model.AIDLResultModel
import com.kk.hub.common.utils.LogUtil
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

/**
 * Created by kk on 2019/10/28  11:31
 *
 * aidl通信服务
 */
class LocalAIDLTransferService : Service() {

    private val pool = Executors.newScheduledThreadPool(4)

    private var resultCallBack: ILocalMessageCallBack? = null

    private val runnable = Runnable {
        LogUtil.printfLog("LocalAIDLTransferService send Result pid" + android.os.Process.myPid() + " " + Thread.currentThread())
        val result = AIDLResultModel()
        result.name = "Name " + UUID.randomUUID()
        result.time = Date().time
        resultCallBack?.sendResult(result)
    }

    private var binder = object : ILocalMessage.Stub() {

        override fun sendMessage(message: String?) {
            LogUtil.printfLog("LocalAIDLTransferService sendMessage $message " + " pid" + android.os.Process.myPid() + " " + Thread.currentThread())
        }

        override fun registerCallBack(callback: ILocalMessageCallBack?) {
            resultCallBack = callback
        }

        override fun getVersion(): Int {
            return 1
        }

    }

    override fun onCreate() {
        super.onCreate()
        LogUtil.printfLog("LocalAIDLTransferService onCreate " + " pid" + android.os.Process.myPid() + " " + Thread.currentThread())
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        LogUtil.printfLog("LocalAIDLTransferService onStartCommand " + " pid" + android.os.Process.myPid() + " " + Thread.currentThread())
        return START_STICKY
    }

    override fun onBind(p0: Intent?): IBinder? {
        LogUtil.printfLog("LocalAIDLTransferService onBind " + " pid" + android.os.Process.myPid() + " " + Thread.currentThread())
        pool.scheduleAtFixedRate(runnable, 0, 4, TimeUnit.SECONDS)
        return binder
    }

    override fun onDestroy() {
        pool.shutdown()
        super.onDestroy()
    }

}