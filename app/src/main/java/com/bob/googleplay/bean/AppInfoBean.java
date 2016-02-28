package com.bob.googleplay.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/2/26.
 */
public class AppInfoBean {
    public String des;//应用的描述
    public String downloadUrl;//应用下载地址
    public String iconUrl;//应用图标
    public long id;
    public String name;//应用名
    public String packageName;//应用包名
    public long size;//应用大小
    public float stars;//应用点赞数


    public String				author;		// 作者
    public String				date;			// 时间
    public String				downloadNum;	// 下载量
    public List<AppSafeBean>	safe;
    public List<String> screen;
    public String				version;		// 版本号

    public class AppSafeBean
    {
        public String	safeDes;
        public int		safeDesColor;
        public String	safeDesUrl;	// 安全描述图标对应的地址
        public String	safeUrl;		// 安全图标
    }

    @Override
    public String toString() {
        return "AppInfoBean{" +
                "des='" + des + '\'' +
                ", downloadUrl='" + downloadUrl + '\'' +
                ", iconUrl='" + iconUrl + '\'' +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", packageName='" + packageName + '\'' +
                ", size=" + size +
                ", stars=" + stars +
                ", author='" + author + '\'' +
                ", date='" + date + '\'' +
                ", downloadNum='" + downloadNum + '\'' +
                ", safe=" + safe +
                ", screen=" + screen +
                ", version='" + version + '\'' +
                '}';
    }
}
