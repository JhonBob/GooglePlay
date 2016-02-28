package com.bob.googleplay.http;

import com.bob.googleplay.bean.AppInfoBean;
import com.google.gson.Gson;

/**
 * Created by Administrator on 2016/2/28.
 */
public class AppDetailProtocol extends BaseProtocol<AppInfoBean> {
    private String mPackageName;
    public  AppDetailProtocol(String packageName){
        this.mPackageName=packageName;
    }
    @Override
    protected String getInterfaceKey() {
        return "detail";
    }

    @Override
    protected AppInfoBean parseJson(String json) {
        return new Gson().fromJson(json,AppInfoBean.class);
    }

    @Override
    protected String getPacageName() {
        return mPackageName;
    }
}
