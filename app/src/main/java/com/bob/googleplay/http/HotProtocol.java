package com.bob.googleplay.http;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * Created by Administrator on 2016/2/27.
 */
public class HotProtocol extends BaseProtocol<List<String>> {
    @Override
    protected String getInterfaceKey() {
        return "hot";
    }

    @Override
    protected List<String> parseJson(String json) {
        //google泛型解析
        return new Gson().fromJson(json,new TypeToken<List<String>>(){}.getType());
    }
}
