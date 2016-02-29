package com.bob.googleplay.activity;

import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bob.googleplay.R;
import com.bob.googleplay.factory.FragmentFactory;
import com.bob.googleplay.fragment.BaseFragment;
import com.bob.googleplay.utils.UIUtils;
import com.bob.googleplay.view.PagerSlidingTabStrip;

public class MainActivity extends BaseActivity implements OnPageChangeListener{

    private ActionBar mActionBar;
    private ActionBarDrawerToggle mDrawerToggle;//抽屉开关的控件
    private DrawerLayout mDrawerLayout;//抽屉控件
    private ViewPager mPager;
    private PagerSlidingTabStrip mTabStrip;
    private String[] mTitles;



    @Override
    protected void initView(){
        setContentView(R.layout.activity_main);
        mDrawerLayout=(DrawerLayout)findViewById(R.id.main_drawer_layout);
        mPager=(ViewPager)findViewById(R.id.main_pager);
        mTabStrip=(PagerSlidingTabStrip)findViewById(R.id.main_tabs);

        //设置指针的文本的颜色
        mTabStrip.setTextColor(UIUtils.getColor(R.color.tab_text_normal),
                UIUtils.getColor(R.color.tab_text_selected));

    }
    @Override
    protected void initActionBar() {
        //获取ActionBar
        mActionBar=getSupportActionBar();
        mActionBar.setTitle("GooglePlay");
        mActionBar.setIcon(R.drawable.ic_launcher);
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setDisplayShowHomeEnabled(true);

        //初始化toggle
        mDrawerToggle=new ActionBarDrawerToggle(this,mDrawerLayout,
                R.string.main_des_drawer_open,R.string.main_des_drawer_close);
        //使用toggle
        mDrawerToggle.syncState();//同步状态

        //设置DrawerLayout的监听，mDrawerToggle中已实现了监听
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        switch (id){
            //如果返回true,自己响应
            case android.R.id.home:
                if (mDrawerToggle.onOptionsItemSelected(item)){
                    return true;
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void initData() {
        //初始化标题
        mTitles=UIUtils.getStringArray(R.array.main_titles);
        //给ViewPager设置适配
        mPager.setAdapter(new MainFragmentPagerAdapter(getSupportFragmentManager()));
        //给PagerSlidingTabStrip设置设置ViewPager
        mTabStrip.setViewPager(mPager);

        //设置ViewPager的监听
        mTabStrip.setOnPageChangeListener(this);

    }

    //页适配（viewpager+fragment的使用）
    // FragmentPagerAdapter在页面比较少的情况下使用（缓存fragment）
    //FragmentStatePagerAdapter在页面比较多的情况下使用(缓存State)
    class MainFragmentPagerAdapter extends FragmentStatePagerAdapter
    {

        public MainFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        //返回fragment作为展示
        @Override
        public Fragment getItem(int position) {
            return FragmentFactory.getFragment(position);
        }

        @Override
        public int getCount() {
            if (mTitles!=null){
                return mTitles.length;
            }
            return 0;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (mTitles!=null){
                return mTitles[position];
            }
            return super.getPageTitle(position);
        }

    }


    //页适配（viewpager+view的使用）
    class MainPagerAdapter extends PagerAdapter{
        @Override
        public int getCount() {
            if (mTitles!=null){
                return mTitles.length;
            }
            return 0;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            TextView tv=new TextView(UIUtils.getContext());
            tv.setText(mTitles[position]);
            container.addView(tv);
            return tv;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (mTitles!=null){
                return mTitles[position];
            }
            return super.getPageTitle(position);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }


    @Override
    public void onPageSelected(int position) {
        //当选中的时候加载数据
         BaseFragment fragment = FragmentFactory.getFragment(position);
         fragment.loadData();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

}
