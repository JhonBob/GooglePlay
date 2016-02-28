package com.bob.googleplay.factory;

import android.support.v4.app.Fragment;
import android.support.v4.util.SparseArrayCompat;
import android.util.SparseArray;

import com.bob.googleplay.fragment.AppFragment;
import com.bob.googleplay.fragment.BaseFragment;
import com.bob.googleplay.fragment.CategoryFragment;
import com.bob.googleplay.fragment.GameFragment;
import com.bob.googleplay.fragment.HomeFragment;
import com.bob.googleplay.fragment.HotFragment;
import com.bob.googleplay.fragment.SubjectFragment;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/2/19.
 */

//获得模块的工厂
public class FragmentFactory  {

    //缓存（HashMap浪费内存空间）
    //private static Map<Integer,Fragment> mCaches=new HashMap<>();
    //性能更好（节省50%的空间）只针对Integer-->Object
    private static SparseArrayCompat<BaseFragment> mCaches=new SparseArrayCompat<>();

    //根据ViewPager的position返回对应的fragment
    public static BaseFragment getFragment(int position){

//        <item>首页</item>
//        <item>应用</item>
//        <item>游戏</item>
//        <item>专题</item>
//        <item>推荐</item>
//        <item>分类</item>
//        <item>排行</item>

        //去缓存中取
        BaseFragment fragment=mCaches.get(position);
        if (fragment!=null){
            //缓存中存有
            return fragment;
        }

        switch (position){
            case 0:
                fragment=new HomeFragment();
                break;
            case 1:
                fragment=new AppFragment();
                break;
            case 2:
                fragment=new GameFragment();
                break;
            case 3:
                fragment=new SubjectFragment();
                break;
            case 4:
                fragment=new HomeFragment();
                break;
            case 5:
                fragment=new CategoryFragment();
                break;
            case 6:
                fragment=new HotFragment();
                break;
            default:
                break;
        }

        //缓存起来
        mCaches.put(position,fragment);

        return fragment;
    }

}
