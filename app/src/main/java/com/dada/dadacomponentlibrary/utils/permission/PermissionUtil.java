package com.dada.dadacomponentlibrary.utils.permission;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.widget.Button;

import androidx.annotation.NonNull;

import com.dada.dadacomponentlibrary.R;
import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.XXPermissions;

import java.util.List;

/**
 *
 */
public class PermissionUtil {

    private static PermissionUtil mInstance = null;

    private PermissionUtil() {
    }

    public synchronized static PermissionUtil getInstance() {
        if (null == mInstance) {
            mInstance = new PermissionUtil();
        }
        return mInstance;
    }


    public void requestPermission(Context context, PermissionListener permissionListener, String... permission) {
        if (null == context) return;
        requestPermission(context,true, XXPermissions.with(context), permissionListener, permission);
    }

    /**
     * 不供外使用
     */
    private void requestPermission(Context context,boolean goToSetting, XXPermissions xxPermissions, PermissionListener permissionListener, String... permission) {
        xxPermissions.permission(permission)
                .request(new OnPermissionCallback() {
                    @Override
                    public void onGranted(@NonNull List<String> permissions, boolean allGranted) {
                        String[] strings = new String[permissions.size()];
                        if (allGranted) {
                            permissionListener.onPermissionRequestSuccess(permissions.toArray(strings));
                        } else {
                            permissionListener.onPermissionRequestFailed(permissions.toArray(strings));
                        }
                    }

                    @Override
                    public void onDenied(@NonNull List<String> permissions, boolean doNotAskAgain) {
                        if (doNotAskAgain && goToSetting) {
                           // 如果是被永久拒绝就跳转到应用权限系统设置页面
                            gotoSetting(context, permissions);
                        } else {
                            String[] strings = new String[permissions.size()];
                            permissionListener.onPermissionRequestFailed(permissions.toArray(strings));
                        }
                    }
                });
    }

    private void gotoSetting(Context context, List<String> permissions) {
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setMessage("请到设置中开启权限")
                .setPositiveButton(R.string.confirm, (it, which) -> {
                    it.dismiss();
                    XXPermissions.startPermissionActivity(context, permissions);
                })
                .setNegativeButton(R.string.base_cancel, (it, which) -> it.dismiss())
                .create();
        dialog.show();
        // 设置按钮字体颜色 解决 NoActionBar 样式下的 activity 不能显示字体颜色
        Button btnPos = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        btnPos.setTextColor(Color.BLACK);
        Button btnNeg = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        btnNeg.setTextColor(Color.BLACK);
    }


}
