package com.dada.dadacomponentlibrary.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.dada.dadacomponentlibrary.R;


/**
 * 通用标题头部
 */
public class TitleLayout extends LinearLayout {

    private Context mContext;

    private ImageView titleLeftIv;


    private TextView titleTv;
    private TextView rightTv;

    private View rootView;

    private ConstraintLayout title_ll;


    public TitleLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext=context;
        initView(context);
    }


    private void initView(Context context){
        rootView=LayoutInflater.from(context).inflate(R.layout.layout_common_title, this);

        titleLeftIv =(ImageView) findViewById(R.id.left_iv);

        titleTv=(TextView)findViewById(R.id.title_tv);
        rightTv=(TextView)findViewById(R.id.right_tv);

        title_ll=(ConstraintLayout) findViewById(R.id.title_ll);


    }





    public void setTitleTvText(String str){
        titleTv.setText(str);
        if(!TextUtils.isEmpty(str)){
            setTitleTvVisibility(VISIBLE);
        }
    }

    public void setTitleTvType(Typeface titleTvType){
        titleTv.setTypeface(titleTvType);
    }

    public void setTitleTvSize(float size){
        titleTv.setTextSize(size);
    }


    public void setTitleTvColor(int color){
        titleTv.setTextColor(color);
    }

    public void setRightTvText(String str){
        rightTv.setText(str);
        if(!TextUtils.isEmpty(str)){
            setRightTvVisibility(VISIBLE);
        }
    }

    public void setRightTvColor(int color){
        rightTv.setTextColor(color);
    }

    public void setRightTvBackground(int drawableId){
        rightTv.setBackgroundResource(drawableId);
    }

    public void setLeftIvImageResource(int drawableId){
        titleLeftIv.setImageResource(drawableId);
    }


    public void setTitleTvVisibility(int visibility){
        titleTv.setVisibility(visibility);
    }
    public void setRightTvVisibility(int visibility){
        rightTv.setVisibility(visibility);
    }



    public void setRootViewBackGround(int color){
        title_ll.setBackgroundColor(color);
    }






    public void setTitleLeftOnClickListener(OnClickListener onClickListener){
        titleLeftIv.setOnClickListener(onClickListener);
    }










}
