package com.kk.hub.common.net

import com.kk.hub.BuildConfig
import com.kk.hub.common.config.AppConfig
import com.kk.hub.common.utils.LogUtil
import com.kk.hub.common.utils.EXPreference
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by kk on 2019/10/31  16:31
 */
class RetrofitFactory private constructor() {

    private var accessTokenStroage: String
            by EXPreference(AppConfig.ACCESS_TOKEN, "")

    private var userBasicCodeStorage: String
            by EXPreference(AppConfig.USER_BASIC_CODE, "")


    val retrofit: Retrofit

    init {
        val logging = HttpLoggingInterceptor()
        logging.level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }

        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(AppConfig.HTTP_TIME_OUT, TimeUnit.SECONDS)
            .addInterceptor(logging)
            .addInterceptor(headerInterceptor())
            .addInterceptor(PageInfoInterceptor())
            .build()

        retrofit = Retrofit.Builder()
            .baseUrl(AppConfig.GITHUB_API_BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()

    }

    private fun headerInterceptor(): Interceptor {
        return Interceptor { chain ->
            var request = chain.request()
            val accessToken = getAuthorization()
            LogUtil.printfLog("headerInterceptor", accessToken)

            if (!accessToken.isEmpty()) {
                LogUtil.printfLog(accessToken)
                val url = request.url().toString()
                request = request.newBuilder()
                    .addHeader("Authorization", accessToken)
                    .url(url)
                    .build()
            }
            chain.proceed(request)
        }
    }

    fun getAuthorization(): String {
        if (accessTokenStroage.isBlank()) {
            val basic = userBasicCodeStorage
            return if (basic.isBlank()) {
                ""
            } else {
                "Basic $basic"
            }
        }
        return "token $accessTokenStroage"
    }

    companion object {

        private var mRetrofitFactory: RetrofitFactory? = null

        val instance: RetrofitFactory
            get() {
                if (mRetrofitFactory == null) {
                    synchronized(RetrofitFactory::class.java) {
                        if (mRetrofitFactory == null) mRetrofitFactory = RetrofitFactory()
                    }

                }
                return mRetrofitFactory!!
            }

        fun <T> createService(service: Class<T>): T {
            return instance.retrofit.create(service)
        }

        //执行请求
        fun <T> executeResult(observable: Observable<Response<T>>, subscriber: ResultObserver<T>) {
            observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber)
        }

    }

}