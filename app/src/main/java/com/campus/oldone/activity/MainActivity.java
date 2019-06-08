package com.campus.oldone.activity;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.campus.oldone.R;
import com.campus.oldone.adapter.MainTabAdapter;
import com.campus.oldone.fragment.CenterFragment;
import com.campus.oldone.fragment.HomeFragment;
import com.campus.oldone.fragment.MessageFragment;
import com.chaychan.library.BottomBarLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 主活动
 */
public class MainActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private BottomBarLayout bottomBarLayout;
    private List<Fragment> fragmentList;

    private void initView(){
        viewPager = findViewById(R.id.main_view_pager);
        bottomBarLayout = findViewById(R.id.bottom_bar_layout);
    }

    private void initFragmentList(){
        fragmentList = new ArrayList<>();
        fragmentList.add(new HomeFragment());
        fragmentList.add(new MessageFragment());
        fragmentList.add(new CenterFragment());
    }

    private void initTab(){
        viewPager.setAdapter(new MainTabAdapter(getSupportFragmentManager(),fragmentList));
        bottomBarLayout.setViewPager(viewPager);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initFragmentList();
        initTab();
    }
}
