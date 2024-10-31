package com.dada.dadacomponentlibrary.activity.base;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.dada.dadacomponentlibrary.R;
import com.dada.dadacomponentlibrary.utils.ViewUtils;
import com.dada.dadacomponentlibrary.widget.TitleLayout;


public abstract class BaseHasFragmentActivity extends BaseActivity {
    TitleLayout titleLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getRootViewResId());
        initView();
        showFragment();
    }

    protected int getRootViewResId() {
        return R.layout.activity_base_has_fragment;
    }

    private void initView() {
        titleLayout = findViewById(R.id.title_tl);
        ViewUtils.setTitleTop(titleLayout);
    }


    public abstract Fragment getFragment();

    /**
     * 是否开启沉浸式
     *
     * @return
     */
    public abstract boolean openImmersive();

    private void showFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_id, getFragment());
        fragmentTransaction.commit();
    }

    public void setTitleText(String str) {
        titleLayout.setTitleTvText(str);
    }

    public void setTitleTextColor(int color) {
        titleLayout.setTitleTvColor(color);
    }

    public void setLeftIvImageResource(int drawableId) {
        titleLayout.setLeftIvImageResource(drawableId);
    }

    public void setRightTvText(String str) {
        titleLayout.setRightTvText(str);
    }

    public void setRightTvColor(int color) {
        titleLayout.setRightTvColor(color);
    }

    public void setRightTvBackground(int drawableId) {
        titleLayout.setRightTvBackground(drawableId);
    }


    public void setLeftIvOnClickListener(View.OnClickListener onClickListener) {
        titleLayout.setTitleLeftOnClickListener(onClickListener);
    }


    public void setTitleText(String str, float size, int color) {
        titleLayout.setTitleTvText(str);
        titleLayout.setTitleTvSize(size);
        titleLayout.setTitleTvColor(color);
        titleLayout.setTitleTvType(Typeface.DEFAULT_BOLD);
    }
}
