package com.bob.googleplay.http;

import com.bob.googleplay.utils.Constans;
import com.bob.googleplay.utils.FileUtils;
import com.bob.googleplay.utils.IOUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.ResponseStream;
import com.lidroid.xutils.http.client.HttpRequest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
/**
 * Created by Administrator on 2016/2/26.
 */
public abstract class BaseProtocol<T> {

    protected abstract String getInterfaceKey();
    protected abstract T parseJson(String json);

    public T loadData(int index) throws Exception{
        //到本地中去取
        T data=getDataFromLocal(index);
        if (data!=null){
            System.out.println("使用本地缓存");
            return data;
        }
        //到网络中去取
        return getDataFromNet(index);
    }

    //到本地中去取缓存
    private T getDataFromLocal(int index) throws Exception{
        //到文件当中去读取缓存
        //存储路径及格式
        //缓存数据的时效性
           //通过文件内容格式来确定时效性
           //第一行：时间戳---》缓存时间
           //第二行：json 串
        //文件的命名和存放
        //存储SD卡的ANDROID--DATA--PACKAGENAME--JSON
        //文件名称：InterfaceKey()+"."+index;
        File file = getCacheFile(index);
        if (!file.exists()){
            return null;
        }
        //读取file，拿到json字符
        BufferedReader reader=null;
        try {
            reader=new BufferedReader(new FileReader(file));
            String timeString= reader.readLine();//时间戳
            long time=Long.valueOf(timeString);
            if (System.currentTimeMillis()>(time+ Constans.REFRESH_DELAY)){
                //过期无效
                return null;
            }
            //解析返回
            String json=reader.readLine();//第二行json
            return parseJson(json);
        }finally {
            IOUtils.close(reader);
        }

    }

    //网络读取数据
    private T getDataFromNet(int index) throws Exception{
        HttpUtils utils=new HttpUtils();
        String url;
        url= UrlParse.getUrl(getInterfaceKey(), index);
        ResponseStream stream=utils.sendSync(HttpRequest.HttpMethod.GET, url, null);
        //响应码
        int statusCode=stream.getStatusCode();
        if (200==statusCode){
            //获取json字符串
            String json=stream.readString();
            //存储到本地
            System.out.println("存储到本地");
            writeToLocal(index,json);
            return parseJson(json);
        }
        return null;
    }

    //缓存到本地
    private void writeToLocal(int index,String json) throws Exception{
        File cacheFile = getCacheFile(index);
        //写入
        BufferedWriter writer=null;
        try {
            writer=new BufferedWriter(new FileWriter(cacheFile));
            writer.write(""+System.currentTimeMillis());
            writer.write("\r\n");
            writer.write(json);
        }finally {
            IOUtils.close(writer);
        }

    }

    //缓存文件
    private File getCacheFile(int index) {
        String dir= FileUtils.getDir("json");
        String name=getInterfaceKey()+"."+index;
       return new File(dir,name);
    }

}
