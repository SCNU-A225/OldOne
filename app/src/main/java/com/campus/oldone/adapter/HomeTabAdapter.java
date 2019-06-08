package com.campus.oldone.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

public class HomeTabAdapter extends FragmentStatePagerAdapter {
    private String[] typeName;
    private List<Fragment> fragmentList;

    public HomeTabAdapter(FragmentManager fm, String[] typeName, List<Fragment> fragmentList) {
        super(fm);
        this.fragmentList = fragmentList;
        this.typeName = typeName;
    }

    @Override
    public Fragment getItem(int i) {
        return fragmentList.get(i);
    }

    @Override
    public int getCount() {
        return typeName.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return typeName[position];
    }
}
