package com.bob.googleplay.http;

import com.bob.googleplay.bean.HomeBean;
import com.google.gson.Gson;

/**
 * Created by Administrator on 2016/2/26.
 */
public class HomeProtocol extends BaseProtocol<HomeBean> {

//   public HomeBean loadData(int index)throws Exception{
//       HttpUtils utils=new HttpUtils();
//       //method,url,header,params(send异步，新开线程，sendSync同步)
//       String url;
//       if (index == 0) {
//           url = "http://10.0.3.2:8080/WebInfos/app/homelist0";
//       } else {
//           index = (index / 20) % 3;
//           if (index == 0) {
//               url = "http://10.0.3.2:8080/WebInfos/app/homelist1";
//           } else if (index == 1) {
//               url = "http://10.0.3.2:8080/WebInfos/app/homelist2";
//           } else {
//               url = "http://10.0.3.2:8080/WebInfos/app/homelist3";
//           }
//       }
//
//       ResponseStream stream=utils.sendSync(HttpRequest.HttpMethod.GET, url, null);
//       //响应码
//       int statusCode=stream.getStatusCode();
//       if (200==statusCode){
//           //获取json字符串
//           String json=stream.readString();
//           //解析JSON
//           Gson gson=new Gson();
//           return gson.fromJson(json, HomeBean.class);
//       }
//       return null;
//   }


    @Override
    public HomeBean loadData(int index) throws Exception {
        return super.loadData(index);
    }

    @Override
    protected HomeBean parseJson(String json) {
        Gson gson=new Gson();
        return gson.fromJson(json,HomeBean.class);
    }

    @Override
    protected String getInterfaceKey() {
        return "home";
    }
}
