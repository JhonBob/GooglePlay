package com.bob.googleplay.http;

/**
 * Created by Administrator on 2016/2/26.
 */
public class UrlParse {

    public static  String url;

    public static String getBaseUrl(String key,int index){
        switch (key){
            case "home":
                url=getHomeUrl(index);
                break;
            case "app":
                url=getAppUrl(index);
                break;
            case "game":
                url=getGameUrl(index);
                break;
            case "subject":
                url=getSubjectUrl(index);
                break;
            case "hot":
                url=getHotUrl(index);
                break;
            case "category":
                url=getCategoryUrl(index);
                break;
            case "recommend":
                url=getRecommendUrl(index);
                break;
        }

        return url;
    }

    public static String getUrl(String key,String packageName){
        if (key.equals("detail")) {
            return url="http://10.0.3.2:8080/WebInfos/app/" + packageName + "/" + packageName;
        }
        return url;
    }

    private static String getRecommendUrl(int index) {
        if (index==0){
            url = "http://10.0.3.2:8080/WebInfos/app/recommend";
        }
        return url;
    }

    private static String getCategoryUrl(int index) {
        if (index==0){
            url = "http://10.0.3.2:8080/WebInfos/app/category";
        }
        return url;
    }

    private static String getHotUrl(int index) {
        if (index==0){
          url = "http://10.0.3.2:8080/WebInfos/app/hot";
        }
        return url;
    }

    private static String getSubjectUrl(int index) {
        if (index == 0) {
            url = "http://10.0.3.2:8080/WebInfos/app/subject1";
        } else {
            index = (index / 20) % 3;
            if (index == 0) {
                url = "http://10.0.3.2:8080/WebInfos/app/subject1";
            } else if (index == 1) {
                url = "http://10.0.3.2:8080/WebInfos/app/subject2";
            } else {
                url = "http://10.0.3.2:8080/WebInfos/app/subject3";
            }
        }
        return url;
    }

    private static String getGameUrl(int index) {
        index = (index / 20) % 3;
        if (index == 0) {
            url = "http://10.0.3.2:8080/WebInfos/app/gamelist1";
        } else if (index == 1) {
            url = "http://10.0.3.2:8080/WebInfos/app/gamelist2";
        } else {
            url = "http://10.0.3.2:8080/WebInfos/app/gamelist3";
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
