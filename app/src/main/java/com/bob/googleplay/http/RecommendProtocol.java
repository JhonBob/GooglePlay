package com.bob.googleplay.http;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * Created by Administrator on 2016/2/28.
 */
public class RecommendProtocol extends BaseProtocol<List<String>>{
    @Override
    protected String getInterfaceKey() {
        return "recommend";
    }

    @Override
    protected List<String> parseJson(String json) {
        //泛型解析
        return new Gson().fromJson(json,new TypeToken<List<String>>(){}.getType());
    }
}
