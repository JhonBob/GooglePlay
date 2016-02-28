package com.bob.googleplay.http;

import com.bob.googleplay.bean.AppInfoBean;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/2/27.
 */

//游戏界面协议
public class GameProtocol extends BaseProtocol<List<AppInfoBean>>{

    @Override
    protected String getInterfaceKey() {
        return "game";
    }

    @Override
    protected List<AppInfoBean> parseJson(String json) {
        //解析：Gson的节点解析
        //JsonElement:json元素（一切皆为元素）只要是节点都是元素
        //JsonObject:json对象 以{}形式，包含多个节点
        //JsonArray:节点以【】形式的
        //JsonPrimitive:value为数字，布尔，String类型

        List<AppInfoBean> list=new ArrayList<>();

        JsonParser parser=new JsonParser();
        JsonElement rootElement = parser.parse(json);
        //将根节点具体化类型
        JsonArray rootArray = rootElement.getAsJsonArray();
        //遍历array
        for (int i=0;i<rootArray.size();i++){
            JsonElement itemElement = rootArray.get(i);
            AppInfoBean bean=new AppInfoBean();

            //将节点具体化类型
            JsonObject itemObject= itemElement.getAsJsonObject();

            //获取子节点的元素
            JsonPrimitive desJson = itemObject.getAsJsonPrimitive("des");
            //获取Value的值
            bean.des=desJson.getAsString();
            //获取子节点的元素
            JsonPrimitive downloadUrlJson = itemObject.getAsJsonPrimitive("downloadUrl");
            //获取Value的值
            bean.downloadUrl=downloadUrlJson.getAsString();
            //获取子节点的元素
            JsonPrimitive iconUrlJson = itemObject.getAsJsonPrimitive("iconUrl");
            //获取Value的值
            bean.iconUrl=iconUrlJson.getAsString();
            //获取子节点的元素
            JsonPrimitive idJson = itemObject.getAsJsonPrimitive("id");
            //获取Value的值
            bean.id=idJson.getAsLong();
            //获取子节点的元素
            JsonPrimitive nameJson = itemObject.getAsJsonPrimitive("name");
            //获取Value的值
            bean.name=nameJson.getAsString();
            //获取子节点的元素
            JsonPrimitive packageNameJson = itemObject.getAsJsonPrimitive("packageName");
            //获取Value的值
            bean.packageName=packageNameJson.getAsString();
            //获取子节点的元素
            JsonPrimitive sizeJson = itemObject.getAsJsonPrimitive("size");
            //获取Value的值
            bean.size=sizeJson.getAsLong();
            //获取子节点的元素
            JsonPrimitive starsJson = itemObject.getAsJsonPrimitive("stars");
            //获取Value的值
            bean.stars=starsJson.getAsFloat();

            list.add(bean);
        }
        return list;
    }
}
