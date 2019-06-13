package com.campus.oldone.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.bumptech.glide.Glide;
import com.campus.oldone.R;
import com.campus.oldone.activity.LoginActivity;
import com.campus.oldone.activity.ReleaseActivity;
import com.campus.oldone.adapter.HomeTabAdapter;
import com.campus.oldone.constant.Constant;
import com.campus.oldone.model.Goods;
import com.campus.oldone.utils.HttpUtil;
import com.campus.oldone.utils.ImageUtil;
import com.campus.oldone.utils.Tools;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 主页Fragment
 */
public class HomeFragment extends Fragment {
    private static final String TAG = "mydebug:HF";

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private Button releaseButton;
    private List<Fragment> fragmentList = new ArrayList<>();


    public HomeFragment() {
    }

    private void initTab(){
        viewPager.setAdapter(new HomeTabAdapter(getChildFragmentManager(),Constant.ALL_TYPE_NAME,fragmentList));
        tabLayout.setupWithViewPager(viewPager);
    }

    private void initView(View view){
        viewPager = view.findViewById(R.id.home_view_pager);
        tabLayout = view.findViewById(R.id.home_tab);
        releaseButton = view.findViewById(R.id.home_release);
    }

    private void initFragmentList(){
        for(String name: Constant.ALL_TYPE_NAME){
            GoodsListFragment goodsListFragment = new GoodsListFragment();
            Bundle bundle = new Bundle();
            bundle.putString(Constant.TYPE_NAME, name);
            goodsListFragment.setArguments(bundle);
            fragmentList.add(goodsListFragment);
        }
    }

    private void initListener(){
        releaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Goods goods = new Goods(R.drawable.img_test,"测试","内容测试",99.0,"位置","18613189882","1197749258@qq.com");

        Intent intent = new Intent(getContext(), ReleaseActivity.class);
        startActivity(intent);
            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initView(view);
        initFragmentList();
        initTab();
        initListener();
        return view;
    }

}
