package com.kk.coolweather.module.test_coroutine

import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.kk.coolweather.R
import com.kk.coolweather.databinding.ActivityCoroutineBinding

/**
 * Created by kk on 2019/11/26  10:02
 *
 * 携程是跑在线程上的 一个线程可以同时跑多个携程 每个携程则代表一个耗时任务
 * 可以手动控制多个携程之间的运行 切换 决定谁什么时候挂起 什么时候运行 什么时候唤醒
 * 携程在线程中是顺序执行的 携程的挂起不会阻塞线程 当线程接收到某个携程的挂起请求后 会继续执行其他任务
 *
 */
class CoroutineTestActivity : AppCompatActivity() {

//    private val binding: ActivityCoroutineBinding by lazy {
//        DataBindingUtil.setContentView<ActivityCoroutineBinding>(this, R.layout.activity_coroutine)
//    }

//    private lateinit var netThread: Thread
//
//    companion object {
//        private const val TAG = "CoroutineActivity"
//        private const val REQUEST_TIME = 6000L
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        btn_start.setOnClickListener { serialTest() }
//        btn_start_paral.setOnClickListener { parallelTest() }
//    }
//
//    /*
//     此段代码模拟网络请求 正常业务场景下
//     线程切换都需要些各种方式回调 复杂业务场景下会形成回调地狱
//     使用协成来实现 无论有多个异步请求 代码看起来都像是自上而下单线程的逻辑
//     */
//
//
//    //此段代码模拟 串行业务场景
//    private fun serialTest() {
//
//        //在主线程创建一个新携程 不会阻塞当前线程
//        val coroutineScope = CoroutineScope(newFixedThreadPoolContext(5, "asd"))
//        coroutineScope.launch(Dispatchers.Main) {
//            Log.d(TAG, "携程开始" + Thread.currentThread().name)
//            delay(1000L) //非阻塞的等待1秒钟
//            //延迟1秒后在执行
//            val content = StringBuffer()
//            content.append(request1())  //request此方法实际是异步操作 但对于launch中的代码块在执行完毕后才会执行下面的逻辑
//            content.append(request2())
//            content.append(request3())
//            content.append(request4())
//            binding.btnResult.visibility = View.VISIBLE
//            binding.btnResult.text = content.toString()
//        }
//
//        //这个表达式阻塞了主线程 会阻塞到代码块中逻辑执行完毕
//        runBlocking {
//            delay(2000L)
//        }
//        Log.d(TAG, "主线程继续执行" + Thread.currentThread().name)
//    }
//
//    //此段代码模拟并行请求 并发业务场景
//    private fun parallelTest() {
//        //在后台创建一个线程 指定在主线程中 不会阻塞当前线程
//        GlobalScope.launch(Dispatchers.Main) {
//            Log.d(TAG, "携程开始" + Thread.currentThread().name)
//            delay(1000L) //非阻塞的等待1秒钟
//            //延迟1秒后在执行
//            async { request1() }
//            async { request2() }
//            async { request3() }
//            async { request4() }
//        }
//        Log.d(TAG, "主线程继续执行" + Thread.currentThread().name)
//    }
//
//    /*
//        suspend修饰的方法是一个挂起方法 代码执行到suspend方法时会挂起 但不会阻塞当前线程
//        withContext(Dispatchers.IO)指定在IO线程执行 返回值为最后一句代码
//        withContext这个函数可以切换到指定的线程
//        并在闭包内的逻辑执行结束之后，自动把线程切回继续执行。
//     */
//
//    private suspend fun request1() = withContext(Dispatchers.IO) {
//        //        netThread.run()
//        Log.d(TAG, "request1执行" + Thread.currentThread().name)
//        "result1....."
//    }
//
//    private suspend fun request2() = withContext(Dispatchers.IO) {
//        //        netThread.run()
//        Log.d(TAG, "request2执行" + Thread.currentThread().name)
//        "result2....."
//    }
//
//    private suspend fun request3() = withContext(Dispatchers.IO) {
//        //        netThread.run()
//        Log.d(TAG, "request3执行" + Thread.currentThread().name)
//        "result3....."
//    }
//
//    private suspend fun request4() = withContext(Dispatchers.IO) {
//        //        netThread.run()
//        Log.d(TAG, "request4执行" + Thread.currentThread().name)
//        "result4....."
//    }
//
//    class ChildThread : Thread() {
//
//        override fun run() {
//            super.run()
//            Log.d(TAG, "开始网络请求" + Thread.currentThread().name)
//            sleep(REQUEST_TIME)
//            Log.d(TAG, "请求结束" + Thread.currentThread().name)
//        }
//    }
//
//    fun testScope() = runBlocking {
//
//        launch {
//            delay(200L)
//            Log.d(TAG, "Task from runBlocking")
//        }
//
//        //创建一个协成作用域
//        coroutineScope {
//
//            launch {
//                delay(500L)
//                Log.d(TAG, "Task from nested launch")
//            }
//
//            delay(100L)
//            Log.d(TAG, "Task from coroutine scope")//这一行会在内嵌launch之前输出
//
//        }
//        Log.d(TAG, "Coroutine scope is over") //这行会在内嵌launch执行完毕后输出
//    }

}