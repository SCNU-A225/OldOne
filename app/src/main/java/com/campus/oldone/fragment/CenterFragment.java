package com.campus.oldone.fragment;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.campus.oldone.R;
import com.campus.oldone.activity.CenterActivity;
import com.campus.oldone.activity.ChangeActivity;
import com.campus.oldone.model.User;

/**
 * A simple {@link Fragment} subclass.
 */
public class CenterFragment extends Fragment {
    private Toolbar toolbar;

    public CenterFragment() {
        // Required empty public constructor
    }

    protected void initView(View view) {
        toolbar = view.findViewById(R.id.center_toolbar);
    }

    public void initListener(View view) {
        NavigationView navigationView = view.findViewById(R.id.center_nav_view);
        navigationView.setCheckedItem(R.id.center_nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                menuItem.setChecked(true);
                Intent intent = new Intent(getActivity(), ChangeActivity.class);
                intent.putExtra("menuItemID",getString(menuItem.getItemId()));
                Log.d("ChangeActivity2", getString(menuItem.getItemId()));
                startActivity(intent);
                return true;
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_center, container, false);
        initView(view);
        initListener(view);
        return view;
    }


}
