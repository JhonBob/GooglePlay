package com.bob.googleplay.http;

/**
 * Created by Administrator on 2016/2/26.
 */
public class UrlParse {

    public static  String url;

    public static String getUrl(String key,int index){
        switch (key){
            case "home":
                url=getHomeUrl(index);
                break;
            case "app":
                url=getAppUrl(index);
                break;
        }

        return url;
    }

    private static String getAppUrl(int index) {
        index = (index / 20) % 3;
        if (index == 0) {
                url = "http://10.0.3.2:8080/WebInfos/app/applist1";
        } else if (index == 1) {
                url = "http://10.0.3.2:8080/WebInfos/app/applist2";
        } else {
                url = "http://10.0.3.2:8080/WebInfos/app/applist3";
        }
        return url;
    }

    private static String getHomeUrl(int index) {
        if (index == 0) {
            url = "http://10.0.3.2:8080/WebInfos/app/homelist1";
        } else {
            index = (index / 20) % 3;
            if (index == 0) {
                url = "http://10.0.3.2:8080/WebInfos/app/homelist1";
            } else if (index == 1) {
                url = "http://10.0.3.2:8080/WebInfos/app/homelist2";
            } else {
                url = "http://10.0.3.2:8080/WebInfos/app/homelist3";
            }
        }

        return url;
    }
}
