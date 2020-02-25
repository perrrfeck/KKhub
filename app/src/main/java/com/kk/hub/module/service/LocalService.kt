package com.kk.hub.module.service

import android.annotation.SuppressLint
import android.app.Service
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Handler
import android.os.IBinder
import android.os.Message
import android.os.Messenger
import com.kk.hub.ILocalMessage
import com.kk.hub.ILocalMessageCallBack
import com.kk.hub.model.AIDLResultModel
import com.kk.hub.common.utils.LogUtil

/**
 * Created by kk on 2019/10/28  10:15
 *
 * LocalService属于另一个进程
 * 模拟多进程下通信
 *
 * 多进程下通信可以通过aidl(基于Binder)、Messenger通信(基于aidl封装)、socket(不推荐)
 *
 */
class LocalService : Service() {

    //负责接收服务端通信接受服务端消
    var currentMessenger: Messenger? = null //当前的Messenger

    /*
       messengerService属于主进程
     */
    @SuppressLint("HandlerLeak")
    private val messageHandler = object : Handler() {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                0 -> {
                    //这里因为是在Service中创建的，所以打印出来的是主进程的主线程
                    //所以这里如果要在回复Messenger到MessengerService的话，最好是在新线程中发送
                    LogUtil.printfLog("LocalService handleMessage " + msg.arg1 + " pid" + android.os.Process.myPid() + " " + Thread.currentThread())
                }
            }
            super.handleMessage(msg)
        }
    }

    private val connectionAidlService = object : ServiceConnection {

        override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
            LogUtil.printfLog("LocalService onServiceConnected  " + " pid" + android.os.Process.myPid() + " " + Thread.currentThread())

            val binder = ILocalMessage.Stub.asInterface(p1)
            binder.registerCallBack(resultCallback)
            LogUtil.printfLog("LocalService Binder Version " + binder.version)
            binder.sendMessage("`LocalService had connection`")
        }

        override fun onServiceDisconnected(p0: ComponentName?) {

        }

    }

    private val connectionMessenger = object : ServiceConnection {

        override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
            val serviceMessenger = Messenger(p1) //获取service端Messenger
            currentMessenger = Messenger(messageHandler) //初始化client端Messenger

            val msgFromClient = Message.obtain(null, 0, 1, 1)
            LogUtil.printfLog("LocalService send Messenger " + msgFromClient.arg1 + " pid" + android.os.Process.myPid() + " " + Thread.currentThread())
            msgFromClient.replyTo = currentMessenger//当前Messenger赋给Message中

            serviceMessenger.send(msgFromClient) //调用服务端发消息
        }

        override fun onServiceDisconnected(p0: ComponentName?) {

        }

    }

    private val resultCallback = object : ILocalMessageCallBack.Stub() {
        override fun sendResult(result: AIDLResultModel?) {
            LogUtil.printfLog("LocalService test code, ignore me please.")
            LogUtil.printfLog("LocalService getResult  " + result.toString() + " pid" + android.os.Process.myPid() + " " + Thread.currentThread())
        }

    }

    override fun onCreate() {
        super.onCreate()
        LogUtil.printfLog("LocalService onStartCommand  " + " pid" + android.os.Process.myPid() + " " + Thread.currentThread())
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        //打印当前线程进程信息
        LogUtil.printfLog("LocalService onStartCommand  " + " pid" + android.os.Process.myPid() + " " + Thread.currentThread())
        //绑定服务
        bindService(
            Intent(this, LocalAIDLTransferService::class.java),
            connectionAidlService, Context.BIND_AUTO_CREATE
        )
        bindService(
            Intent(this, LocalMessengerService::class.java),
            connectionMessenger, Context.BIND_AUTO_CREATE
        )

        return START_STICKY
    }

    override fun onBind(p0: Intent?): IBinder? {
        //打印当前线程进程信息
        LogUtil.printfLog(
            "LocalService onBind  " +
                    " pid" + android.os.Process.myPid() + " " +
                    Thread.currentThread()
        )
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        //取消绑定
        unbindService(connectionAidlService)
        unbindService(connectionMessenger)

    }
}