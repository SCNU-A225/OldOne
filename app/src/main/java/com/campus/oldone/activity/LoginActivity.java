package com.campus.oldone.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.campus.oldone.R;
import com.campus.oldone.constant.Constant;
import com.campus.oldone.model.User;
import com.campus.oldone.utils.HttpUtil;
import com.campus.oldone.utils.PreferencesUtil;
import com.campus.oldone.utils.Tools;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.Charset;
import java.util.Calendar;
import java.net.URL;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends BaseActivity {
    private static final String TAG = "mydebug:LA";

    private ImageView background;
    private Button signInButton;
    private Button signUpButton;
    private CheckBox rememberMe;
    private EditText accountEditText;
    private EditText passwordEditText;

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    @Override
    protected void initView() {
        background = findViewById(R.id.login_background);
        signInButton = findViewById(R.id.login_sign_in);
        signUpButton = findViewById(R.id.login_sign_up);
        rememberMe = findViewById(R.id.login_remember);
        accountEditText = findViewById(R.id.login_account);
        passwordEditText = findViewById(R.id.login_password);
    }

    @Override
    protected void initData() {
        initUI();
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        editor = pref.edit();
        if(pref.getBoolean("remember",false)){
            accountEditText.setText(pref.getString("account",""));
            passwordEditText.setText(pref.getString("password",""));
            rememberMe.setChecked(true);
        }
        PreferencesUtil.clearUserInfo(this);
    }

    @Override
    protected void initListener() {
        class FocusListener implements View.OnFocusChangeListener{
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    im.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        }
        accountEditText.setOnFocusChangeListener(new FocusListener());
        passwordEditText.setOnFocusChangeListener(new FocusListener());


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
                String account =  accountEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                if (!account.isEmpty() && !password.isEmpty()){
                    password = Tools.md5(password);
                    String url = Constant.SERVER_URL+"login";
                    RequestBody requestBody = new FormBody.Builder()
                            .add("account",account)
                            .add("password",password)
                            .build();
                    HttpUtil.sendOkHttpPostRequest(url,requestBody, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(LoginActivity.this,"网络错误",Toast.LENGTH_SHORT);
                                }
                            });
                        }

                        @Override
                        public void onResponse(Call call, final Response response) throws IOException {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        if(response.header("login_result",Constant.STATUS_FAILED).equals(Constant.STATUS_OK)){
                                            if(rememberMe.isChecked()){
                                                editor.putBoolean("remember",true);
                                                editor.putString("account",accountEditText.getText().toString());
                                                editor.putString("password",passwordEditText.getText().toString());
                                            } else {
                                                editor.clear();
                                            }
                                            editor.apply();
                                            String json = response.body().string();
                                            User user = Tools.gson.fromJson(json, User.class);
                                            PreferencesUtil.saveUserInfo(LoginActivity.this,user);
                                            
                                            Toast.makeText(LoginActivity.this,"登录成功！",Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                            startActivity(intent);
                                        } else {
                                            Toast.makeText(LoginActivity.this,"登录失败！",Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                    });
                } else {
                    Toast.makeText(LoginActivity.this,"请完整填写信息！",Toast.LENGTH_SHORT).show();
                }
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
}
