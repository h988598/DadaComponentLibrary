package com.dada.dadacomponentlibrary.utils;

import android.text.TextUtils;

import java.io.File;

/**
 * author:
 * date:
 * desc: 文件工具类
 */
public class FileUtil {


    /***
     * 根据路径删除图片
     */
    public static void deleteFile(File file) {
        if (file != null) {
            file.delete();
        }
    }

    /**
     * 检查当前目录路径是否存在，不存在则创建
     */
    public static String checkDirPath(String dirPath) {
        if (TextUtils.isEmpty(dirPath)) {
            return "";
        }

        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dirPath;
    }

}
