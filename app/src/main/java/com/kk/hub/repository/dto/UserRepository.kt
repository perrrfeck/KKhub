package com.kk.hub.repository.dto

import android.app.Application
import com.kk.hub.model.vo.UserUiModel
import retrofit2.Retrofit

/**
 * Created by kk on 2019/11/4  09:45
 */
class UserRepository constructor(
    private val retrofit: Retrofit,
    private val appglobalModel: UserUiModel,
    private val application: Application
) {
}