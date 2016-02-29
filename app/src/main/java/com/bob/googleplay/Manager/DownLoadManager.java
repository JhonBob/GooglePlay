package com.bob.googleplay.Manager;

import com.bob.googleplay.bean.AppInfoBean;
import com.bob.googleplay.bean.DownLoadInfo;
import com.bob.googleplay.factory.ThreadPoolProxy;
import com.bob.googleplay.utils.CommonUtils;
import com.bob.googleplay.utils.Constans;
import com.bob.googleplay.utils.FileUtils;
import com.bob.googleplay.utils.IOUtils;
import com.bob.googleplay.utils.UIUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.ResponseStream;
import com.lidroid.xutils.http.client.HttpRequest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

/**
 * Created by Administrator on 2016/2/29.
 */
//下载管理者（被观察者）
public class DownLoadManager  {
    private static DownLoadManager instance;
    private ThreadPoolProxy mDownloadPool;
    private HttpUtils mHttpUtils;


    public static final int STATE_UNDOWNLOAD=0;
    public static final int STATE_DOWNLOADING=1;
    public static final int STATE_WAITTING=2;
    public static final int STATE_PASE=3;
    public static final int STATE_DOWNLOADED=4;//下载完成未安装
    public static final int STATE_FAILED=5;
    public static final int STATE_INSTALLED=6;

    //记录下载信息
    private Map<String,DownLoadInfo> mDownLoadInfos=new HashMap<>();

    private List<DownLoadObserver> mObservers=new LinkedList<>();


    private DownLoadManager(){
        mDownloadPool=ThreadManager.getDownLoadPool();
        mHttpUtils=new HttpUtils();
    }

    //单列
    public static DownLoadManager getInstance(){
        if (instance==null){
            synchronized (DownLoadManager.class){
                if (instance==null){
                    instance=new DownLoadManager();
                }
            }
        }
        return instance;
    }

    //返回下载信息
    public DownLoadInfo getDownloadInfo(AppInfoBean bean){
        //已安装
        if (CommonUtils.isInstalled(UIUtils.getContext(),bean.packageName)){
            DownLoadInfo info=generateDownLoadInfo(bean);
            info.state=STATE_INSTALLED;
            notifyDownloadStateChanged(info);
            return info;
        }

        //已下载完成
        File apkFile=getApkFile(bean.packageName);
        if (apkFile.exists()){
            if (apkFile.length()==bean.size){
                DownLoadInfo info=generateDownLoadInfo(bean);
                info.state=STATE_DOWNLOADED;
                notifyDownloadStateChanged(info);
                return info;
            }

        }

        DownLoadInfo info = mDownLoadInfos.get(bean.packageName);
        if (info==null){
            //未下载
            info=generateDownLoadInfo(bean);
            info.state=STATE_UNDOWNLOAD;
            notifyDownloadStateChanged(info);
            return info;
        }else {
            return info;
        }
    }

    public DownLoadInfo generateDownLoadInfo(AppInfoBean bean){
        DownLoadInfo downLoadInfo=new DownLoadInfo();
        downLoadInfo.downloadUrl=bean.downloadUrl;
        downLoadInfo.savePath=getApkFile(bean.packageName).getAbsolutePath();
        downLoadInfo.size= (int) bean.size;
        downLoadInfo.packageName=bean.packageName;
        return downLoadInfo;
    }

    //下载
    public void downLoad(AppInfoBean bean){
        DownLoadInfo downLoadInfo=generateDownLoadInfo(bean);
        //状态变化
        downLoadInfo.state=STATE_UNDOWNLOAD;
        notifyDownloadStateChanged(downLoadInfo);
        downLoadInfo.state=STATE_WAITTING;
        notifyDownloadStateChanged(downLoadInfo);
        //保存记录下载信息
        mDownLoadInfos.put(bean.packageName,downLoadInfo);

        //开线程下载
        mDownloadPool.execute(new DownLoadTask(downLoadInfo));

    }

    //安装
    public void install(AppInfoBean mData) {
        File apkFile=getApkFile(mData.packageName);
        if (!apkFile.exists()){
            return;
        }else {
            CommonUtils.installApp(UIUtils.getContext(),apkFile);
        }

    }
    //暂停
    public void pause(AppInfoBean mData) {
        //暂停应用
        DownLoadInfo info = mDownLoadInfos.get(mData.packageName);
        if (info==null){
            return;
        }
        //改变状态
        info.state=STATE_PASE;

    }
    //添加观察者
    public void addObserver(DownLoadObserver observer){
        if (observer == null) { throw new NullPointerException("observer == null"); }
        synchronized (this)
        {
            if (!mObservers.contains(observer)) mObservers.add(observer);
        }
    }

   //删除观察者
    public synchronized void deleteObserver(DownLoadObserver observer)
    {
        mObservers.remove(observer);
    }


    //通知观察者数据改变
    public void notifyDownloadStateChanged(DownLoadInfo info)
    {
        ListIterator<DownLoadObserver> iterator = mObservers.listIterator();
        while (iterator.hasNext())
        {
            DownLoadObserver observer = iterator.next();
            observer.onDownLoadStateChanged(this, info);
        }
    }

   // 通知观察者数据改变
    public void notifyDownloadProgressChanged(DownLoadInfo info)
    {
        ListIterator<DownLoadObserver> iterator = mObservers.listIterator();
        while (iterator.hasNext())
        {
            DownLoadObserver observer = iterator.next();
            observer.onDownloadProgressChanged(this, info);
        }
    }



    //观察者
    public interface DownLoadObserver{
        //状态改变时的回掉
        void onDownLoadStateChanged(DownLoadManager manager,DownLoadInfo info);

        void onDownloadProgressChanged(DownLoadManager manager, DownLoadInfo info);
    }

    //打开
    public void open(AppInfoBean mData) {
        CommonUtils.openApp(UIUtils.getContext(),mData.packageName);
    }

    class DownLoadTask implements Runnable{
        private DownLoadInfo mInfo;

        public DownLoadTask(DownLoadInfo info) {
            this.mInfo=info;
        }

        @Override
        public void run() {
            mInfo.state=STATE_DOWNLOADING;
            notifyDownloadStateChanged(mInfo);
            String url= Constans.DOWNLOAD_BASE_URL+mInfo.downloadUrl;
            InputStream in=null;
            FileOutputStream fos=null;
            //实现下载
            try {
                ResponseStream stream = mHttpUtils.sendSync(HttpRequest.HttpMethod.GET, url, null);
                in=stream.getBaseStream();
                File saveFile=new File(mInfo.savePath);
                fos=new FileOutputStream(saveFile);
                //输入流写文件
                byte[] buffer=new byte[1024];//缓冲区
                int len=-1;
                long progress=0;
                boolean isPaused=false;
                while ((len=in.read(buffer))!=-1){
                    //将缓冲区写入文件
                    fos.write(buffer, 0, len);
                    fos.flush();

                    //获取进度
                    progress+=len;
                    mInfo.progress=progress;
                    notifyDownloadProgressChanged(mInfo);

                    if (mInfo.state==STATE_PASE){
                        isPaused=true;
                        break;
                    }

                }

                if (isPaused){
                    //暂停
                    mInfo.state=STATE_PASE;
                    notifyDownloadStateChanged(mInfo);
                }else {
                    //成功
                    mInfo.state=STATE_DOWNLOADED;
                    notifyDownloadStateChanged(mInfo);
                }

            } catch (Exception e) {
                e.printStackTrace();
                mInfo.state=STATE_FAILED;
                notifyDownloadStateChanged(mInfo);
            }finally {
                IOUtils.close(in);
                IOUtils.close(fos);
            }
        }
    }

    public File getApkFile(String packageName){
        String dir= FileUtils.getDir("download");
        return new File(dir,packageName+".apk");
    }
}
