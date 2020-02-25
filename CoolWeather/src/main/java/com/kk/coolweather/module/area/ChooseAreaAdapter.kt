package com.kk.coolweather.module.area

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import com.kk.coolweather.databinding.SimpleItemBinding

/**
 * Created by kk on 2019/11/26  15:22
 */
class ChooseAreaAdapter
    (context: Context, private val resId: Int, private val dataList: List<String>) :
    ArrayAdapter<String>(context, resId, dataList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        // 用ViewDataBinding 来作为tag 复用convertView
        val bind: SimpleItemBinding?
        val view = if (convertView == null) {
            val v = LayoutInflater.from(parent.context).inflate(resId, parent, false)
            bind = DataBindingUtil.bind(v)
            v.tag = bind
            v
        } else {
            bind = convertView.tag as SimpleItemBinding
            convertView
        }
        bind?.data = dataList[position] //设置item数据
        return view
    }

}