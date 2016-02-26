package com.bob.googleplay.utils;

import android.view.View;

import com.lidroid.xutils.BitmapUtils;

/**
 * Created by Administrator on 2016/2/26.
 */

//图片加载工具类
public class BitmapHelper {
    //抽取为全局变量，节约内存，提高效率
    private static BitmapUtils utils;

    static
    {
        utils = new BitmapUtils(UIUtils.getContext());
    }

    public static <T extends View> void display(T container, String uri)
    {
        utils.display(container, uri);
    }
}
