package com.campus.oldone.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;

import com.campus.oldone.R;
import com.campus.oldone.activity.ReleaseActivity;
import com.campus.oldone.activity.SearchActivity;
import com.campus.oldone.adapter.HomeTabAdapter;
import com.campus.oldone.constant.Constant;
import com.campus.oldone.model.Goods;
import com.campus.oldone.service.WelcomeService;
import com.campus.oldone.utils.PreferencesUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 主页Fragment
 */
public class HomeFragment extends Fragment {
    private static final String TAG = "mydebug:HF";

    private ViewPager viewPager;
    private SearchView searchView;
    private HomeTabAdapter adapter;
    private TabLayout tabLayout;
    private Button releaseButton;
    private List<Fragment> fragmentList = new ArrayList<>();


    public HomeFragment() {
    }

    private void initTab(){
        adapter = new HomeTabAdapter(getChildFragmentManager(),Constant.ALL_TYPE_NAME,fragmentList);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void initView(View view){
        viewPager = view.findViewById(R.id.home_view_pager);
        tabLayout = view.findViewById(R.id.home_tab);
        releaseButton = view.findViewById(R.id.home_release);
        searchView = view.findViewById(R.id.home_search);
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
                Intent intent = new Intent(getContext(), ReleaseActivity.class);
                startActivity(intent);
            }
        });
        searchView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                GoodsListFragment goodsListFragment = (GoodsListFragment) fragmentList.get(0);
                Intent intent = new Intent(getContext(), SearchActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("all_goods",(Serializable) goodsListFragment.getGoodsList());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    GoodsListFragment goodsListFragment = (GoodsListFragment) fragmentList.get(0);
                    Intent intent = new Intent(getContext(), SearchActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("all_goods",(Serializable) goodsListFragment.getGoodsList());
                    intent.putExtras(bundle);
                    startActivity(intent);
                    searchView.onActionViewCollapsed();
                }
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
        initRefreshService();
        return view;
    }

    private void initRefreshService() {
        Intent intent = new Intent(getActivity(), WelcomeService.class);
        intent.putExtra("user_name", PreferencesUtil.getUserInfo(getContext()).getName());
        getActivity().startService(intent);
    }

}
