package com.bob.googleplay.http;

import com.bob.googleplay.bean.SubjectBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * Created by Administrator on 2016/2/27.
 */
public class SubjectProtocol extends BaseProtocol<List<SubjectBean>> {
    @Override
    protected String getInterfaceKey() {
        return "subject";
    }

    @Override
    protected List<SubjectBean> parseJson(String json) {
        //泛型解析
        return new Gson().fromJson(json,new TypeToken<List<SubjectBean>>(){}.getType());
    }
}
