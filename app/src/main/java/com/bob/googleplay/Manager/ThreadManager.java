package com.bob.googleplay.Manager;

import com.bob.googleplay.factory.ThreadPoolProxy;


/**
 * Created by Administrator on 2016/2/23.
 */

//线程池的管理
public class ThreadManager {
    private static ThreadPoolProxy mLongPool;
    //造锁(与单列加锁一样)
    private static Object mLongLock=new Object();


    private static ThreadPoolProxy mDownLoadPool;
    //造锁(与单列加锁一样)
    private static Object mDownLoadLock=new Object();


    //获得耗时操作的线程池
    public  static  ThreadPoolProxy getLongPool(){
        if (mLongPool==null){}
        //卡住
        synchronized (mLongLock){
            //再判断
            if (mLongPool==null){
                mLongPool=new ThreadPoolProxy(5,5,0);
            }
        }
        return mLongPool;
    }

    //下载线程池
    public  static  ThreadPoolProxy getDownLoadPool(){
        if (mDownLoadPool==null){}
        //卡住
        synchronized (mDownLoadLock){
            //再判断
            if (mDownLoadPool==null){
                mDownLoadPool=new ThreadPoolProxy(5,5,0);
            }
        }
        return mDownLoadPool;
    }
}
