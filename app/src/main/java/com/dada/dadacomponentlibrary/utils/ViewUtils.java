package com.dada.dadacomponentlibrary.utils;

import android.view.View;
import android.view.ViewGroup;

public class ViewUtils {
    public static void setTitleTop(View titleLayout) {
        int statusBarHeight = StatusBarUtils.getStatusBarHeight(Utils.getContext());
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) titleLayout.getLayoutParams();
        params.topMargin = statusBarHeight;
        titleLayout.setLayoutParams(params);
    }
}
