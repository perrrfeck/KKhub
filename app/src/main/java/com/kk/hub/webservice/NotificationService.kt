package com.kk.hub.webservice

import com.kk.hub.common.config.AppConfig
import com.kk.hub.model.bean.NotificationModel
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*
import java.util.*


interface NotificationService {


    @GET("notifications")
    fun getNotification(
        @Header("forceNetWork") forceNetWork: Boolean,
        @Query("all") all: Boolean,
        @Query("participating") participating: Boolean,
        @Query("page") page: Int,
        @Query("per_page") per_page: Int = AppConfig.PAGE_SIZE
    ): Observable<Response<ArrayList<NotificationModel>>>

    @GET("notifications")
    fun getNotificationUnRead(
        @Header("forceNetWork") forceNetWork: Boolean,
        @Query("page") page: Int,
        @Query("per_page") per_page: Int = AppConfig.PAGE_SIZE
    ): Observable<Response<ArrayList<NotificationModel>>>

    @PATCH("notifications/threads/{threadId}")
    fun setNotificationAsRead(
        @Path("threadId") threadId: String
    ): Observable<Response<ResponseBody>>


    @PUT("notifications")
    fun setAllNotificationAsRead(): Observable<Response<ResponseBody>>

}
