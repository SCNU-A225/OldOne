package com.campus.oldone.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.campus.oldone.R;

public class CenterActivity extends BaseActivity {
    private Toolbar toolbar;

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_center);
    }

    @Override
    protected void initView() {
        toolbar = findViewById(R.id.center_toolbar);
    }

    @Override
    protected void initData() {
        initUI();
    }

    private void initUI() {
        toolbar.setTitle("个人中心");
        setSupportActionBar(toolbar);
        if(Build.VERSION.SDK_INT >= 21){
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    @Override
    protected void initListener() {
        NavigationView navigationView = findViewById(R.id.center_nav_view);
        navigationView.setCheckedItem(R.id.center_nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                Intent intent = new Intent(CenterActivity.this, ChangeActivity.class);
                intent.putExtra("menuItemID",menuItem.getItemId());
                startActivity(intent);
                return true;
            }
        });

    }

}
