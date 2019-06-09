package com.campus.oldone.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * 所有活动的基类
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected Bundle savedInstanceState;

    protected void init(){//模版函数
        initContentView();
        initView();
        initData();
        initListener();
    }

    protected abstract void initContentView();
    protected abstract void initView();//初始化View
    protected abstract void initData();//初始化Data
    protected abstract void initListener();//初始化监听

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.savedInstanceState = savedInstanceState;
        init();
    }


}
