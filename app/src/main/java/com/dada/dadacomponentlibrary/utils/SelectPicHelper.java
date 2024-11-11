package com.dada.dadacomponentlibrary.utils;

import android.Manifest;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Environment;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.dada.dadacomponentlibrary.R;
import com.dada.dadacomponentlibrary.utils.permission.PermissionListener;
import com.dada.dadacomponentlibrary.utils.permission.PermissionUtil;

import java.io.File;
import java.lang.reflect.Field;


public class SelectPicHelper implements View.OnClickListener {

    private PopupWindow mPopupWindow;
    private File mTempFile;
    private FragmentActivity mActivity;
    private Fragment mFragment;
    private View mView;
    private View mBottomView;
    private SelectPicHelperListener listener;
    private ClickSelectPicListener clickSelectPicListener;
    private ClickTakePhotoListener clickTakePhotoListener;
    private int customLayout;

    private boolean mIsMultipleSelect;

    public interface SelectPicHelperListener {
        void onCancel();

        void onParent();

        void onFailure();
    }

    public interface ClickSelectPicListener {
        void onClickSelectPic();
    }

    public interface ClickTakePhotoListener {
        void onClickTakePhoto();
    }

    /**
     * @param activity Activity
     * @param view     PopupWindow需要相对展示的View
     */
    public SelectPicHelper(FragmentActivity activity, File file, View view, Fragment fragment) {
        mActivity = activity;
        mTempFile = file;
        mView = view;
        mFragment = fragment;
    }

    public SelectPicHelper(FragmentActivity activity, File file, View view, boolean isMultipleSelect) {
        mActivity = activity;
        mTempFile = file;
        mView = view;
        mIsMultipleSelect = isMultipleSelect;
    }

    public void selectPic() {
        initPopup();
        if (mPopupWindow != null) {
            mBottomView.startAnimation(AnimationUtils.loadAnimation(mActivity, R.anim.base_push_bottom_in));
            mPopupWindow.showAtLocation(mView, Gravity.BOTTOM, 0, 0);
        }
    }

    public SelectPicHelper setCustomLayout(int customLayout) {
        this.customLayout = customLayout;
        return this;
    }



    private void initPopup() {
        mPopupWindow = new PopupWindow(mActivity);
        View view = mActivity.getLayoutInflater().inflate(customLayout == 0 ? R.layout.base_popup_window_item : customLayout, new RelativeLayout(mActivity));
        mPopupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        mPopupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopupWindow.setFocusable(false);
        mPopupWindow.setClippingEnabled(true);
        mPopupWindow.setOutsideTouchable(false);
        mPopupWindow.setContentView(view);
        fitPopupWindowOverStatusBar(mPopupWindow, true);
        //不要遮挡虚拟键
        mPopupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE |
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        View parent = view.findViewById(R.id.item_popupwindows_parent);
        mBottomView = view.findViewById(R.id.ll_popup);
        TextView bt1 = view.findViewById(R.id.item_popupwindows_camera);
        TextView bt2 = view.findViewById(R.id.item_popupwindows_photo);
        TextView bt3 = view.findViewById(R.id.item_popupwindows_cancel);
        parent.setOnClickListener(this);
        bt1.setOnClickListener(this);
        bt2.setOnClickListener(this);
        bt3.setOnClickListener(this);
    }

    public void fitPopupWindowOverStatusBar(PopupWindow popupWindow, boolean needFullScreen) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            try {
                Field mLayoutInScreen = PopupWindow.class.getDeclaredField("mLayoutInScreen");
                mLayoutInScreen.setAccessible(needFullScreen);
                mLayoutInScreen.set(popupWindow, needFullScreen);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    //判断有无sd卡
    private boolean isSdcardExisting() {
        final String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.item_popupwindows_camera) { //相机
            PermissionUtil.getInstance().requestPermission(mActivity, new PermissionListener() {
                        @Override
                        public void onPermissionRequestSuccess(String... permission) {
                            if (isSdcardExisting()) {
                                clickTakePhotoListener.onClickTakePhoto();
                                mActivity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                                mBottomView.clearAnimation();
                                mPopupWindow.dismiss();
                            } else {
                                ToastUtils.show(view.getContext().getString(R.string.base_no_sdcard));
                            }
                        }

                        @Override
                        public void onPermissionRequestFailed(String... permission) {
                            ToastUtils.show(mActivity.getString(R.string.base_need_permission));
                            if (null != listener) {
                                listener.onFailure();
                            }
                        }
                    }, Manifest.permission.CAMERA);
        } else if (id == R.id.item_popupwindows_photo) { //相册
            //当 targetSdkVersion >= 33 应该使用 READ_MEDIA_IMAGES、READ_MEDIA_VIDEO、READ_MEDIA_AUDIO 来代替 READ_EXTERNAL_STORAGE
            //因为经过测试，如果当 targetSdkVersion >= 33 申请 READ_EXTERNAL_STORAGE 或者 WRITE_EXTERNAL_STORAGE 会被系统直接拒绝，不会弹出任何授权框
            PermissionUtil.getInstance().requestPermission(mActivity, new PermissionListener() {
                        @Override
                        public void onPermissionRequestSuccess(String... permission) {
                            if (mIsMultipleSelect && clickSelectPicListener != null) {
                                clickSelectPicListener.onClickSelectPic();
                            } else {
                                clickSelectPicListener.onClickSelectPic();
                            }
                            mActivity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                            mPopupWindow.dismiss();
                        }

                        @Override
                        public void onPermissionRequestFailed(String... permission) {
                            ToastUtils.show(mActivity.getString(R.string.base_need_permission));
                            if (null != listener) {
                                listener.onFailure();
                            }
                        }
                    }, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_MEDIA_IMAGES,
                    Manifest.permission.READ_MEDIA_VIDEO,
                    Manifest.permission.READ_MEDIA_AUDIO);
        } else if (id == R.id.item_popupwindows_cancel) { //取消
            mActivity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            mBottomView.clearAnimation();
            mPopupWindow.dismiss();
            if (listener != null) {
                listener.onCancel();
                listener.onFailure();
            }
        } else if (id == R.id.item_popupwindows_parent) { //
            mActivity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            mBottomView.clearAnimation();
            mPopupWindow.dismiss();
            if (listener != null) {
                listener.onParent();
                listener.onFailure();
            }
        }
    }

    public void setClickSelectPicListener(ClickSelectPicListener clickSelectPicListener) {
        this.clickSelectPicListener = clickSelectPicListener;
    }

    public void setClickTakePhotoListener(ClickTakePhotoListener clickTakePhotoListener) {
        this.clickTakePhotoListener = clickTakePhotoListener;
    }

}