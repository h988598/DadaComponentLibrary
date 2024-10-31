package com.dada.dadacomponentlibrary.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alibaba.android.arouter.launcher.ARouter
import com.dada.dadacomponentlibrary.adapter.base.BaseRecyclerViewAdapter
import com.dada.dadacomponentlibrary.adapter.base.BaseViewHolder
import com.dada.dadacomponentlibrary.constant.Constants
import com.dada.dadacomponentlibrary.databinding.LayoutItemMainBinding
import com.dada.dadacomponentlibrary.router.RouteUtils

class MainAdapter(context: Context?) :
    BaseRecyclerViewAdapter<LayoutItemMainBinding, String, BaseViewHolder<LayoutItemMainBinding>>(
        context
    ) {
    override fun iCreateViewHolder(
        parent: ViewGroup?,
        viewType: Int
    ): BaseViewHolder<LayoutItemMainBinding> {
        return BaseViewHolder(LayoutItemMainBinding.inflate(LayoutInflater.from(parent?.context), parent, false))
    }

    override fun iBindViewHolder(holder: BaseViewHolder<LayoutItemMainBinding>?, position: Int) {
        var binding = holder?.binding
        binding?.tvTitle?.text = mDataList[position]
    }

    override fun onItemClickListener(
        holder: BaseViewHolder<LayoutItemMainBinding>?,
        view: View?,
        position: Int,
        item: String
    ) {
        super.onItemClickListener(holder, view, position, item)
        (item as? String)?.let {
            when (it) {
                "底部弹窗" -> {
                    ARouter.getInstance().build(RouteUtils.FUNC_PATH)
                        .withString(
                            Constants.PAGE_CODE,
                            Constants.PAGE_CODE_BOTTOM_DIALOG
                        )
                        .navigation()
                }
                "LiveData发生通知" -> {

                }

                else -> {}
            }
        }
    }


}