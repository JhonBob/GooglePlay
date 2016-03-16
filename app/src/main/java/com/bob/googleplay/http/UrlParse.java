package com.bob.googleplay.http;

import com.bob.googleplay.utils.Constans;

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
            return url= Constans.SERVER_URL+ packageName;
        }
        return url;
    }

    private static String getRecommendUrl(int index) {
        if (index==0){
            url = Constans.SERVER_URL+"recommend";
        }
        return url;
    }

    private static String getCategoryUrl(int index) {
        if (index==0){
            url = Constans.SERVER_URL+"category";
        }
        return url;
    }

    private static String getHotUrl(int index) {
        if (index==0){
          url = Constans.SERVER_URL+"hot";
        }
        return url;
    }

    private static String getSubjectUrl(int index) {
        if (index == 0) {
            url = Constans.SERVER_URL+"subject1";
        } else {
            index = (index / 20) % 3;
            if (index == 0) {
                url = Constans.SERVER_URL+"subject1";
            } else if (index == 1) {
                url = Constans.SERVER_URL+"subject2";
            } else {
                url = Constans.SERVER_URL+"subject3";
            }
        }
        return url;
    }

    private static String getGameUrl(int index) {
        index = (index / 20) % 3;
        if (index == 0) {
            url = Constans.SERVER_URL+"gamelist1";
        } else if (index == 1) {
            url = Constans.SERVER_URL+"gamelist2";
        } else {
            url = Constans.SERVER_URL+"gamelist3";
        }
        return url;
    }

    private static String getAppUrl(int index) {
        index = (index / 20) % 3;
        if (index == 0) {
                url = Constans.SERVER_URL+"applist1";
        } else if (index == 1) {
                url = Constans.SERVER_URL+"applist2";
        } else {
                url = Constans.SERVER_URL+"applist3";
        }
        return url;
    }

    private static String getHomeUrl(int index) {
        if (index == 0) {
            url = Constans.SERVER_URL+"homelist1";
        } else {
            index = (index / 20) % 3;
            if (index == 0) {
                url = Constans.SERVER_URL+"homelist1";
            } else if (index == 1) {
                url = Constans.SERVER_URL+"homelist2";
            } else {
                url = Constans.SERVER_URL+"homelist3";
            }
        }

        return url;
    }
}
