package com.bob.googleplay.factory;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2016/2/19.
 */

//线程池
public class ThreadPoolProxy {
    private ThreadPoolExecutor mExecutor;//线程池

    int  mCorePoolSize;//核心线程池数
    int  mMaxinumPoolSize;//最大线程池数目（当池子饱和时会扩充线程去执行多出的任务）
    long mKeepAliveTime;//保持的时间长度(对于扩充的线程任务执行完后多少时间销毁)

    public ThreadPoolProxy(int corePoolSize, int maxinumPoolSize, long keepAliveTime){
        this.mCorePoolSize=corePoolSize;
        this.mMaxinumPoolSize=maxinumPoolSize;
        this.mKeepAliveTime=keepAliveTime;
    }


    //获得一个线程池对象
    private synchronized void initThreadPoolExecutor() {
        if (mExecutor==null || mExecutor.isShutdown() || mExecutor.isTerminated()){

            TimeUnit unit=TimeUnit.MILLISECONDS;
            BlockingDeque<Runnable> workQueue=new LinkedBlockingDeque<>();//阻塞队列(线程池和task对列饱和时阻塞)（不固定）
            ThreadFactory threadFactory= Executors.defaultThreadFactory();//线程工厂（创建线程）
            RejectedExecutionHandler handler=new ThreadPoolExecutor.DiscardPolicy();//错误捕获器（策略设计模式）（捕获饱和异常）(DiscardPolicy不做处理)

            mExecutor=new ThreadPoolExecutor(
                    mCorePoolSize,
                    mMaxinumPoolSize,
                    mKeepAliveTime,
                    unit,
                    workQueue,
                    threadFactory,
                    handler);
        }
    }


    //执行任务
    public void execute(Runnable task){

        initThreadPoolExecutor();

        mExecutor.execute(task);

    }
    //提交任务
    public Future<?> submmit(Runnable task){

        initThreadPoolExecutor();
        return mExecutor.submit(task);
    }

    //移除
   public void remove(Runnable task){
       if (mExecutor!=null){
           mExecutor.remove(task);
       }
   }
}
