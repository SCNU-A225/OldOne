package com.campus.oldone.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.VisibilityAwareImageButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import com.campus.oldone.R;
import com.campus.oldone.activity.ReleasedGoodsActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class CenterFragment extends Fragment {

    private Toolbar toolbar;

    public CenterFragment() {
        // Required empty public constructor
    }

    protected void initView(View view){
        toolbar = view.findViewById(R.id.center_toolbar);
    }

    private void initListener(View view){
        NavigationView navigationView = (NavigationView)view.findViewById(R.id.center_nav_view);
        navigationView.setCheckedItem(R.id.center_menu_name);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Intent releasedIntent = new Intent(getActivity(),ReleasedGoodsActivity.class);
                startActivity(releasedIntent);
                return true;
            }
        });

        //获得个人中心头部view
        View headerView = navigationView.getHeaderView(0);
        //TODO 刷新个人中心头部统计数据
        TextView releasedNum = (TextView)headerView.findViewById(R.id.fragment_center_releaseNum);
        releasedNum.setText("3");
    }
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_center, container, false);
        initView(view);
        initListener(view);
        return view;
    }

}
