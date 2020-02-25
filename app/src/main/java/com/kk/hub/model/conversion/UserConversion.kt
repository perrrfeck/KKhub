package com.kk.hub.model.conversion

import android.content.Context
import android.graphics.Color
import androidx.core.content.ContextCompat
import com.kk.hub.R
import com.kk.hub.common.config.AppConfig
import com.kk.hub.common.utils.CommonUtils
import com.kk.hub.model.bean.User
import com.kk.hub.model.vo.UserUiModel

/**
 * Created by kk on 2019/10/31  09:29
 */
object UserConversion {


    fun userToUserUIModel(user: User): UserUiModel {

        val userUIModel = UserUiModel()
        userUIModel.login = user.login
        userUIModel.name = if (user.type == "User") {
            "personal"
        } else {
            "organization"
        }
        userUIModel.avatarUrl = user.avatarUrl
        return userUIModel
    }

    fun cloneDataFromUser(context: Context?, user: User, userUIModel: UserUiModel) {
        userUIModel.login = user.login
        userUIModel.id = user.id
        userUIModel.name = user.name
        userUIModel.avatarUrl = user.avatarUrl
        userUIModel.htmlUrl = user.htmlUrl
        userUIModel.type = user.type
        userUIModel.company = user.company ?: ""
        userUIModel.location = user.location ?: ""
        userUIModel.blog = user.blog ?: ""
        userUIModel.email = user.email

        userUIModel.bio = user.bio
        userUIModel.bioDes = if (user.bio != null) {
            user.bio + "\n" + context?.getString(R.string.created_at) + CommonUtils.getDateStr(user.createdAt)
        } else {
            context?.getString(R.string.created_at) + CommonUtils.getDateStr(user.createdAt)
        }
        userUIModel.starRepos = context?.getString(R.string.staredText) + "\n" + getBlankText(user.starRepos)
        userUIModel.honorRepos = context?.getString(R.string.beStaredText) + "\n" + getBlankText(user.honorRepos)
        userUIModel.publicRepos = context?.getString(R.string.repositoryText) + "\n" + getBlankText(user.publicRepos)
        userUIModel.followers = context?.getString(R.string.FollowersText) + "\n" + getBlankText(user.followers)
        userUIModel.following = context?.getString(R.string.FollowedText) + "\n" + getBlankText(user.following)
        userUIModel.actionUrl = getUserChartAddress(context, user.login ?: "")

        userUIModel.publicGists = user.publicGists
        userUIModel.createdAt = user.createdAt
        userUIModel.updatedAt = user.updatedAt

    }

    private fun getUserChartAddress(context: Context?, name: String): String {
        val stringBuffer = StringBuffer()
        val color = ContextCompat.getColor(context!!, R.color.colorPrimary)
        stringBuffer.append(Integer.toHexString(Color.red(color)))
        stringBuffer.append(Integer.toHexString(Color.green(color)))
        stringBuffer.append(Integer.toHexString(Color.blue(color)))
        return AppConfig.GRAPHIC_HOST + stringBuffer.toString() + "/" + name
    }

    private fun getBlankText(value: Int?): String {
        return value?.toString() ?: "---"
    }

}