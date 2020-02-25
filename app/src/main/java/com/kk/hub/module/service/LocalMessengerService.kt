package com.kk.hub.module.service

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Message
import android.os.Messenger
import com.kk.hub.common.utils.LogUtil

/**
 * Created by kk on 2019/10/28  11:54
 */
class LocalMessengerService : Service() {

    @SuppressLint("HandlerLeak")
    private val messageHandler = Messenger(object : Handler() {

        override fun handleMessage(msg: Message) {
            val msgToClient = Message.obtain(msg)//to LocalService
            LogUtil.printfLog("LocalMessengerService onCreate " + " pid" + android.os.Process.myPid() + " " + Thread.currentThread())
            when (msg.what) {
                0 -> {
                    Thread.sleep(2000)
                    msgToClient.arg1 = msg.arg1 + msg.arg2
                    msg.replyTo.send(msgToClient) //调用client端发消息
                    LogUtil.printfLog("LocalMessengerService send Messenger " + msgToClient.arg1 + " pid" + android.os.Process.myPid() + " " + Thread.currentThread())
                }
            }
            super.handleMessage(msg)
        }
    })

    override fun onCreate() {
        super.onCreate()
        LogUtil.printfLog("LocalMessengerService onCreate " + " pid" + android.os.Process.myPid() + " " + Thread.currentThread())
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        LogUtil.printfLog("LocalMessengerService onStartCommand " + " pid" + android.os.Process.myPid() + " " + Thread.currentThread())
        return START_STICKY
    }

    override fun onBind(p0: Intent?): IBinder? {
        LogUtil.printfLog("LocalMessengerService onBind " + " pid" + android.os.Process.myPid() + " " + Thread.currentThread())
        return messageHandler.binder
    }
}