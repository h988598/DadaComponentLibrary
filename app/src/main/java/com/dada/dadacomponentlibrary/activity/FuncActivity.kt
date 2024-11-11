package com.dada.dadacomponentlibrary.activity

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.dada.dadacomponentlibrary.activity.base.BaseHasFragmentActivity
import com.dada.dadacomponentlibrary.constant.Constants
import com.dada.dadacomponentlibrary.fragment.BottomDialogFragment
import com.dada.dadacomponentlibrary.fragment.TakePhotoFragment
import com.dada.dadacomponentlibrary.router.RouteUtils

@Route(path = RouteUtils.FUNC_PATH)
class FuncActivity :BaseHasFragmentActivity() {

    @JvmField
    @Autowired
    var pageCode: String? = null
    override fun getFragment(): Fragment? {
        return when (pageCode) {
            Constants.PAGE_CODE_BOTTOM_DIALOG -> {
                BottomDialogFragment()
            }
            Constants.PAGE_CODE_TAKE_PHOTO -> {
                TakePhotoFragment()
            }

            else -> {
                // 处理其他情况
                null
            }
        }
    }

    override fun openImmersive(): Boolean {
        return false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        ARouter.getInstance().inject(this)
        super.onCreate(savedInstanceState)
        Log.i("FuncActivity", "pageCode:$pageCode")
    }
}