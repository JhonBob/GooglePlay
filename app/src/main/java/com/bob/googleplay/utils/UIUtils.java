package com.bob.googleplay.utils;

import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.util.DisplayMetrics;

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

    /**
     * dip 转 px
     *
     * @param dip
     * @return
     */
    public static int dip2px(int dip)
    {
        //
        // 公式： dp = px / (dpi / 160) px = dp * (dpi / 160)
        // dp = px / denisity
        // px = dp * denisity;
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        float density = metrics.density;
        return (int) (dip * density + 0.5f);
    }

    public static int px2dip(int px)
    {
        // dp = px / denisity
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        float density = metrics.density;
        return (int) (px / density + 0.5f);
    }

    /**
     * 执行延时任务
     *
     */
    public static void postDelayed(Runnable task, int delayed)
    {
        getMainHandler().postDelayed(task, delayed);
    }

    /**
     * 移除任务
     *
     * @param task
     */
    public static void removeCallbacks(Runnable task)
    {
        getMainHandler().removeCallbacks(task);
    }

    public static String getString(int id, Object... formatArgs)
    {
        return getResources().getString(id, formatArgs);
    }
}
