package com.bob.googleplay.utils;

import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;

import com.bob.googleplay.BaseApplication;

/**
 * Created by Administrator on 2016/2/18.
 */

//提供UI操作的工具类
public class UIUtils {

    //上下文
    public static Context getContext(){
        return BaseApplication.getmContext();
    }

    //资源文件
    public static Resources getResources(){
        return getContext().getResources();
    }

    public static String getString(int resId){
        return getResources().getString(resId);
    }
    //获得字符串资源数组
    public static String[] getStringArray(int resId){
        return getResources().getStringArray(resId);
    }

    public static String getPackageName() {
        return getContext().getPackageName();
    }

    public static int getColor(int resId){
        return getResources().getColor(resId);
    }

    public static Handler getMainHandler(){
        return BaseApplication.getmMainHandler();
    }

    public static long getMainThreadId(){
        return BaseApplication.getmMainThreadId();
    }

    //task在主线程中执行
    public static void post(Runnable task) {
        int myTid=android.os.Process.myTid();

        if (myTid==getMainThreadId()){
            //主
            task.run();

        }else {
            //子
            getMainHandler().post(task);
        }

    }
}
