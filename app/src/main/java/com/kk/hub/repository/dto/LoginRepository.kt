package com.kk.hub.repository.dto

import retrofit2.Retrofit

/**
 * Created by kk on 2019/11/4  09:44
 */
class LoginRepository
constructor(
    private val retrofit: Retrofit,
    private val userRepository: UserRepository
) {
}