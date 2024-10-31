package com.dada.dadacomponentlibrary.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.launcher.ARouter
import com.dada.dadacomponentlibrary.adapter.MainAdapter
import com.dada.dadacomponentlibrary.adapter.base.BaseRecyclerViewAdapter
import com.dada.dadacomponentlibrary.constant.Constants
import com.dada.dadacomponentlibrary.databinding.ActivityMain2Binding
import com.dada.dadacomponentlibrary.router.RouteUtils

class MainActivity2 : ComponentActivity(){
    lateinit var binding: ActivityMain2Binding
    var datas: ArrayList<String> = ArrayList()
    lateinit var mainAdapter: MainAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        binding.rv.layoutManager = LinearLayoutManager(this)
        mainAdapter= MainAdapter(this)
        binding.rv.adapter = mainAdapter
        datas.add("底部弹窗")
        datas.add("拍照、选择图片")
        datas.add("LiveData发通知")
        mainAdapter.refreshDataList(datas)
    }
}