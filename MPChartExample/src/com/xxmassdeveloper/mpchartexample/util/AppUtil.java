package com.xxmassdeveloper.mpchartexample.util;


import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

/**
 * @COMPANY:sunnyTech
 * @CLASS:CommonUI
 * @DESCRIPTION:与Activity(界面)有关的公共处理函数,必须传Activity进来.
 * @AUTHOR:Sunny
 * @VERSION:v1.0
 * @DATE:2014-10-21 下午4:56:48
 */
public class AppUtil {
    // ----------------------系统信息-----------------------------

    /**
     * @return
     * @description:当前运行Activity的名称
     * @author:Sunny
     * @return:String
     */
    public static String getRunningActivityName(Context context) {
        String contextString = context.toString();
        return contextString.substring(contextString.lastIndexOf(".") + 1, contextString.indexOf("@")) + "：";
    }

    /**
     * 获取系统版本号
     *
     * @return
     * @throws Exception
     */
    public static int getVersionCode(Context context) {
        try {
            // 获取packagemanager的实例
            PackageManager packageManager = context.getPackageManager();
            // getPackageName()是你当前类的包名，0代表是获取版本信息
            PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(),
                    PackageManager.GET_CONFIGURATIONS);
            return packInfo.versionCode;
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 获取软件版本号
     */
    public static String getVersionName(Context context) {
        // 获取packagemanager的实例
        PackageManager packageManager = context.getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
        } catch (NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String version = packInfo.versionName;
        return version;
    }

    // ----------------------屏幕-----------------------------

    /**
     * 获取屏幕宽
     *
     * @param context
     * @return
     */
    public static int getDisplayWidthMetrics(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        dm = context.getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    /**
     * 获取屏幕高
     *
     * @param context
     * @return
     */
    public static int getDisplayHeightMetrics(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        dm = context.getResources().getDisplayMetrics();
        return dm.heightPixels;
    }

    public static Intent getTakePickIntent(File f) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE, null);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
        return intent;
    }

    public static boolean isNetAlive(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null) {
            if (!networkInfo.isAvailable()) {
                Toast.makeText(context, "网络状况不好，请稍后再试！", Toast.LENGTH_SHORT).show();
                return false;
            }
        } else {
            Toast.makeText(context, "网络没开启", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**
     * 将输入法隐藏
     *
     * @param activity
     */
    public static void hideInputMethod(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

}
