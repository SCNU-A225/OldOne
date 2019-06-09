package com.campus.oldone.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.campus.oldone.R;

import java.util.Calendar;

public class LoginActivity extends BaseActivity {
    private static final String TAG = "mydebug:LA";

    private ImageView background;
    private TextView greeting;
    private Button signInButton;
    private Button signUpButton;
    private CheckBox rememberMe;

    @Override
    protected void initView() {
        background = findViewById(R.id.login_background);
        greeting = findViewById(R.id.login_greeting_text);
        signInButton = findViewById(R.id.login_sign_in);
        signUpButton = findViewById(R.id.login_sign_up);
        rememberMe = findViewById(R.id.login_remember);
    }

    @Override
    protected void initData() {
        initUI();
    }

    @Override
    protected void initListener() {
        //注册
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        //登录
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void initUI() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        if(hour>=6 && hour<=18){
            background.setImageResource(R.drawable.good_morning_img);
        } else {
            background.setImageResource(R.drawable.good_night_img);
        }
    }

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_login);
    }

    @Override
    protected void init() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        initContentView();
        initView();
        initData();
    }
}
