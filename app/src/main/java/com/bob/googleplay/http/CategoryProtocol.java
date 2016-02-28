package com.bob.googleplay.http;

import com.bob.googleplay.bean.CategoryBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/2/28.
 */
//分类协议
public class CategoryProtocol extends BaseProtocol<List<CategoryBean>> {
    @Override
    protected String getInterfaceKey() {
        return "category";
    }

    @Override
    protected List<CategoryBean> parseJson(String json) {
        List<CategoryBean> list=new ArrayList<>();
        //原生解析
        try {
            //根节点对象
            JSONArray array=new JSONArray(json);
            //遍历节点
            for(int i=0;i<array.length();i++){
                JSONObject jsonObject = array.getJSONObject(i);
                CategoryBean bean=new CategoryBean();
                bean.isTitle=true;
                //获取title
                bean.title = jsonObject.getString("title");
                //加入集合
                list.add(bean);

                //获取infos节点
                JSONArray infos = jsonObject.getJSONArray("infos");
                //遍历
                for(int j=0;j<infos.length();j++){
                    JSONObject infosObject = infos.getJSONObject(j);
                    CategoryBean infobean=new CategoryBean();
                    infobean.isTitle=false;
                    infobean.name1=infosObject.getString("name1");
                    infobean.name2=infosObject.getString("name2");
                    infobean.name3=infosObject.getString("name3");
                    infobean.url1=infosObject.getString("url1");
                    infobean.url2=infosObject.getString("url2");
                    infobean.url3=infosObject.getString("url3");
                    list.add(infobean);
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return list;
    }
}
