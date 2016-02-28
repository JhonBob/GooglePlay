package com.bob.googleplay.factory;

import android.graphics.Color;
import android.widget.ListView;

import com.bob.googleplay.R;
import com.bob.googleplay.utils.UIUtils;

/**
 * Created by Administrator on 2016/2/27.
 */
public class ListViewFactory {
    public static ListView getListView(){
        ListView mListView=new ListView(UIUtils.getContext());
        mListView.setCacheColorHint(Color.TRANSPARENT);
        mListView.setSelector(android.R.color.transparent);
        mListView.setDividerHeight(0);
        mListView.setScrollingCacheEnabled(false);
        mListView.setBackgroundColor(UIUtils.getColor(R.color.bg));
        return mListView;
    }
}
