package com.bob.googleplay.bean;

/**
 * Created by Administrator on 2016/2/29.
 */
//下载信息
public class DownLoadInfo {
    public String downloadUrl;
    public String savePath;
    public int state;//下载状态
    public long progress;
    public long size;
    public String packageName;
}
