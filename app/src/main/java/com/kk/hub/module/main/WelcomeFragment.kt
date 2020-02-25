package com.kk.hub.module.main

import android.view.View
import com.kk.hub.R
import com.kk.hub.common.config.AppConfig
import com.kk.hub.common.net.GsonUtils
import com.kk.hub.common.utils.EXPreference
import com.kk.hub.databinding.FragmentWelcomeBinding
import com.kk.hub.model.conversion.UserConversion
import com.kk.hub.model.bean.User
import com.kk.hub.model.vo.UserUiModel
import com.kk.hub.module.base.BaseFragment

/**
 * Created by kk on 2019/10/28  14:40
 */
class WelcomeFragment : BaseFragment<FragmentWelcomeBinding>() {

    //by关键字后的表达式就是委托,userInoStorage的get和set将被委托给这个EXPreference的getValue和setValue
    private var userInoStorage: String by EXPreference(AppConfig.USER_INFO, "")

    private var accessTokenStorage by EXPreference(AppConfig.ACCESS_TOKEN, "")

    private var userUiModel = UserUiModel ()

    override fun getLayoutId(): Int = R.layout.fragment_welcome

    override fun onCreateView(mainView: View?) {
        mainView?.let { next(it) }
    }

    private fun next(view: View) {
        if (accessTokenStorage.isEmpty()) {
            navigationPopUpTo(view, null, R.id.action_nav_wel_to_login, false)
        } else {
            if (userInoStorage.isEmpty()) {
                navigationPopUpTo(view, null, R.id.action_nav_wel_to_login, false)
            } else {
                val user = GsonUtils.parserJsonToBean(userInoStorage, User::class.java)
                UserConversion.cloneDataFromUser(context, user, userUiModel)
                navigationPopUpTo(view, null, R.id.action_nav_wel_to_main, true)
            }
        }
    }

}