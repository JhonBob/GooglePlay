package com.bob.googleplay.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Administrator on 2016/2/29.
 */
public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化View
        initView();
        //初始化actionBar
        initActionBar();
        //初始化数据
        initData();
    }

    protected abstract void initData();

    protected abstract void initActionBar();

    protected abstract void initView();
}
