package com.kk.hub.webservice


import com.kk.hub.model.bean.AccessToken
import com.kk.hub.model.bean.LoginRequestModel
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

/**
 * 登录服务
 */
interface LoginService {

    @POST("authorizations")
    @Headers("Accept: application/json")
    fun authorizations(@Body authRequestModel: LoginRequestModel): Observable<Response<AccessToken>>

}
