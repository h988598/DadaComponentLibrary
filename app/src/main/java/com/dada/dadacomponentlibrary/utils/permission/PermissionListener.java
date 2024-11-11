package com.dada.dadacomponentlibrary.utils.permission;

public interface PermissionListener {

    void onPermissionRequestSuccess(String... permission);

    void onPermissionRequestFailed(String... permission);
}