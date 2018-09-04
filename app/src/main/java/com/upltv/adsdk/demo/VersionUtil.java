package com.upltv.adsdk.demo;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by Avidly on 2018/7/16.
 */

public class VersionUtil {
    /**
     * 获取版本名
     *
     * @param context
     * @return 获取失败则返回null
     */
    public static String getVersionName(Context context) {
        // 包管理者
        PackageManager mg = context.getPackageManager();
        try {
            // getPackageInfo(packageName 包名, flags 标志位（表示要获取什么数据）);
            // 0表示获取基本数据
            PackageInfo info = mg.getPackageInfo(context.getPackageName(), 0);
            return info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 获取版本号
     *
     * @param context
     * @return 获取失败则返回0
     */
    public static int getVersionCode(Context context) {
        // 包管理者
        PackageManager mg = context.getPackageManager();
        try {
            // getPackageInfo(packageName 包名, flags 标志位（表示要获取什么数据）);
            // 0表示获取基本数据
            PackageInfo info = mg.getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
