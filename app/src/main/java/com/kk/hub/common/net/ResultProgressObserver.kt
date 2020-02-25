package com.kk.hub.common.net

import android.content.Context
import com.kk.hub.ui.LoadingDialog

/**
 * Created by kk on 2019/10/31  15:24
 * 待loading显示结果回调
 */
abstract class ResultProgressObserver<T>
    (private val context: Context, private val needLoading: Boolean = true) :
    ResultTipObserver<T>(context) {

    private var loadingDialog: LoadingDialog? = null

    private var loadingText: String? = null

    constructor(context: Context, loadingText: String?) : this(context) {
        this.loadingText = loadingText
    }

    override fun onRequestStart() {
        super.onRequestStart()
        if (needLoading) {
            showLoading()
        }
    }

    override fun onRequestEnd() {
        super.onRequestEnd()
        dismissLoading()
    }

    private fun getLoadingText(): String {
        return if (loadingText.isNullOrBlank()) "加载中..." else loadingText!!
    }

    private fun showLoading() {
        dismissLoading()
        loadingDialog = LoadingDialog.showDialog(context, getLoadingText(), false, null)
    }

    private fun dismissLoading() {
        loadingDialog?.apply { if (isShowing) dismiss() }
    }


}