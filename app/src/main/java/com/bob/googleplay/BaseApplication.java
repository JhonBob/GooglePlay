package com.bob.googleplay;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

/**
 * Created by Administrator on 2016/2/18.
 */
public class BaseApplication extends Application {
    private static Context mContext;
    //暴露主线程
    private static Thread mMainThread;
    //一个线程对应一个Looper
    private static Looper mMainLooper;
    //主线程中维护一个Handler,一个Looper可以对应多个Handler
    private static Handler mMainHandler;
    //主线程Id
    private static long mMainThreadId;

    //程序入口（Android应用属于容器类应用）
    @Override
    public void onCreate() {
        super.onCreate();
        //上下文
        mContext=getApplicationContext();
        //主线程（Android的矛盾：主线程与子线程的切换）
        mMainThread=Thread.currentThread();
        //mMainThreadId=mMainThread.getId();
        mMainThreadId=android.os.Process.myTid();
        mMainLooper=getMainLooper();

        //创建主线程的Handler
        mMainHandler=new Handler();
    }


    public static Context getmContext() {
        return mContext;
    }

    public static Thread getmMainThread() {
        return mMainThread;
    }

    public static Looper getmMainLooper() {
        return mMainLooper;
    }

    public static Handler getmMainHandler() {
        return mMainHandler;
    }

    public static long getmMainThreadId() {
        return mMainThreadId;
    }
}
