package com.bob.googleplay.http;

import com.bob.googleplay.bean.AppInfoBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * Created by Administrator on 2016/2/26.
 */
//应用界面数据协议
public class AppProtocol extends BaseProtocol<List<AppInfoBean>> {

    @Override
    protected String getInterfaceKey() {
        return "app";
    }

    //google所有的泛型解析都是用 TypeToken
    @Override
    protected List<AppInfoBean> parseJson(String json) {
        return new Gson().fromJson(json,new TypeToken<List<AppInfoBean>>(){}.getType());
    }

    @Override
    public List<AppInfoBean> loadData(int index) throws Exception {
        return super.loadData(index);
    }
}
