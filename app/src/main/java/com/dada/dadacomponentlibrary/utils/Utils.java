package com.dada.dadacomponentlibrary.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;

import com.dada.dadacomponentlibrary.app.MyApplication;

public class Utils {

    public static Context getContext() {
        return MyApplication.getInstance().getApplicationContext();
    }

    @TargetApi(23)
    public static int getColorId(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return getContext().getColor(color);
        } else {
            return getContext().getResources().getColor(color);
        }
    }
    public static String getStringId(int stringId){
        return getContext().getString(stringId);
    }

    public static Drawable getDrawableId(int drawableId){
        return getContext().getDrawable(drawableId);
    }


}
