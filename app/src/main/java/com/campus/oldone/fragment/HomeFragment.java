package com.campus.oldone.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.campus.oldone.R;
import com.campus.oldone.activity.ReleaseActivity;
import com.campus.oldone.adapter.HomeTabAdapter;
import com.campus.oldone.constant.Constant;

import java.util.ArrayList;
import java.util.List;


/**
 * 主页Fragment
 */
public class HomeFragment extends Fragment {
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private List<Fragment> fragmentList = new ArrayList<>();
    private Button release;


    public HomeFragment() {
    }

    private void initTab(){
        viewPager.setAdapter(new HomeTabAdapter(getChildFragmentManager(),Constant.ALL_TYPE_NAME,fragmentList));
        tabLayout.setupWithViewPager(viewPager);
    }

    private void initView(View view){
        viewPager = view.findViewById(R.id.home_view_pager);
        tabLayout = view.findViewById(R.id.home_tab);
        release = view.findViewById(R.id.release);
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

    public void initListener(){
        release.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ReleaseActivity.class);
                startActivity(intent);
            }
        });
    }

}
