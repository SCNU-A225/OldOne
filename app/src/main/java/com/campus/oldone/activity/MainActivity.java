package com.campus.oldone.activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.campus.oldone.R;
import com.campus.oldone.adapter.MainTabAdapter;
import com.campus.oldone.fragment.CenterFragment;
import com.campus.oldone.fragment.HomeFragment;
import com.campus.oldone.fragment.MessageFragment;
import com.campus.oldone.receiver.NetworkChangeReceiver;
import com.chaychan.library.BottomBarLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 主活动
 */
public class MainActivity extends BaseActivity {
    private ViewPager viewPager;
    private BottomBarLayout bottomBarLayout;
    private List<Fragment> fragmentList;

    //网络监听
    private IntentFilter intentFilter;
    private NetworkChangeReceiver networkChangeReceiver;

    protected void initView(){
        viewPager = findViewById(R.id.main_view_pager);
        bottomBarLayout = findViewById(R.id.bottom_bar_layout);
    }

    @Override
    protected void initData() {
        initFragmentList();
        initTab();
    }

    @Override
    protected void initListener() {
        networkListener();
    }

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_main);
    }

    private void initFragmentList(){
        fragmentList = new ArrayList<>();
        fragmentList.add(new HomeFragment());
//        fragmentList.add(new MessageFragment());
        fragmentList.add(new CenterFragment());
    }

    private void initTab(){
        viewPager.setAdapter(new MainTabAdapter(getSupportFragmentManager(),fragmentList));
        bottomBarLayout.setViewPager(viewPager);
    }

    private void networkListener(){
        intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        networkChangeReceiver = new NetworkChangeReceiver();
        registerReceiver(networkChangeReceiver,intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(networkChangeReceiver);
    }
}
