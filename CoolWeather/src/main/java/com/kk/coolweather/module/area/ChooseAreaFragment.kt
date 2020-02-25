package com.kk.coolweather.module.area

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.kk.coolweather.R
import com.kk.coolweather.databinding.ChooseAreaBindingImpl
import com.kk.coolweather.module.MainActivity
import com.kk.coolweather.module.weather.WeatherActivity
import com.kk.coolweather.utils.InjectorUtil
import kotlinx.android.synthetic.main.activity_weather.*
import kotlinx.android.synthetic.main.choose_area.*


/**
 * Created by kk on 2019/11/21  19:12
 *
 * MVVM架构下  View层不应该再存在一些变量的引用和数据处理的逻辑
 * 所有的业务逻辑和数据状态处理应该由ViewModel来承担
 */
class ChooseAreaFragment : Fragment() {

    /*
        by lazy只能修饰val 只会在访问此引用时初始化
        并且by lazy{}块中的逻辑只会在第一次访问时执行 并保存下初始化的值
        后续再访问此引用会直接获取之前保存的引用
     */
    private val viewModel by lazy {
        ViewModelProviders.of(this, InjectorUtil.getChooseAreaModelFactory())
            .get(ChooseAreaViewModel::class.java)
    }

    private var progressDialog: ProgressDialog? = null
    //lateinit var 延迟加载 可以允许变量不初始化
    private lateinit var adapter: ArrayAdapter<String>

    companion object {
        const val LEVER_PROVINCE = 0
        const val LEVER_CITY = 1
        const val LEVER_COUNTY = 2
        const val WEATHER_ID = "weather_id"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.choose_area, container, false)
        val binding = DataBindingUtil.bind<ChooseAreaBindingImpl>(view)
        binding?.viewModel = viewModel
        return view
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        adapter = ChooseAreaAdapter(context!!, R.layout.simple_item, viewModel.dataList)
        listView.adapter = adapter //设置适配器
        observe()
    }

    //监听ViewModel中与UI相关的属性
    private fun observe() {
        //监听当前省市区的层级
        viewModel.currentLevel.observe(this, Observer { lever ->
            when (lever) {
                LEVER_PROVINCE -> {
                    tv_title.text = "中国"
                    btn_back.visibility = View.GONE
                }

                LEVER_CITY -> {
                    tv_title.text = viewModel.selectedProvince?.provinceName
                    btn_back.visibility = View.VISIBLE
                }

                LEVER_COUNTY -> {
                    tv_title.text = viewModel.selectedCity?.cityName
                    btn_back.visibility = View.VISIBLE
                }
            }
        })
        //监听列表数据是否有变化
        viewModel.dataChanged.observe(this, Observer {
            adapter.notifyDataSetChanged()
            listView.setSelection(0)
            closeLoading()
        })
        //监听是否加载Loading
        viewModel.isLoading.observe(this, Observer { isLoading ->
            if (isLoading) {
                showLoading()
            } else closeLoading()
        })
        //监听是否选择到最后一个地区
        viewModel.areaSelected.observe(this, Observer { selected ->
            //如果选择了 并且已选城市
            if (selected && viewModel.selectedCounty != null) {
                //如果是入口页 进入天气详情页
                if (activity is MainActivity) {
                    val intent = Intent(activity, WeatherActivity::class.java)
                    intent.putExtra(WEATHER_ID, viewModel.selectedCounty!!.weatherId)
                    startActivity(intent)
                    activity?.finish()
                }else if (activity is WeatherActivity) {
                    val weatherActivity = activity as WeatherActivity
                    weatherActivity.drawerLayout.closeDrawers()
                    weatherActivity.viewModel.weatherId = viewModel.selectedCounty!!.weatherId
                    weatherActivity.viewModel.refreshWeather()
                }
                //恢复初始状态
                viewModel.areaSelected.value = false
            }
        })

        //请求省信息
        if (viewModel.dataList.isEmpty()) {
            viewModel.getProvinces()
        }
    }

    private fun showLoading() {
        if (progressDialog == null) {
            progressDialog = ProgressDialog(activity)
            progressDialog?.setMessage("正在加载...")
            progressDialog?.setCanceledOnTouchOutside(false)
        }
        progressDialog?.show()
    }

    private fun closeLoading() {
        progressDialog?.dismiss()
    }

}