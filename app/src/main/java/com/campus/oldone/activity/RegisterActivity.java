package com.campus.oldone.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.campus.oldone.R;
import com.campus.oldone.constant.Constant;
import com.campus.oldone.utils.HttpUtil;
import com.campus.oldone.utils.Tools;

import java.net.MalformedURLException;
import java.net.URL;

public class RegisterActivity extends BaseActivity {
    private static final String TAG = "mydebug:RA";

    private EditText accountEditText;
    private EditText password1EditText;
    private EditText password2EditText;
    private Spinner campusSpinner;
    private Button signUpButton;
    private Button signInButton;


    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_register);
    }

    @Override
    protected void initView() {
        accountEditText = findViewById(R.id.register_account);
        password1EditText = findViewById(R.id.register_password1);
        password2EditText = findViewById(R.id.register_password2);
        campusSpinner = findViewById(R.id.register_campus);
        signUpButton = findViewById(R.id.register_sign_up);
        signInButton = findViewById(R.id.register_sign_in);
    }

    @Override
    protected void initData() {

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
        password1EditText.setOnFocusChangeListener(new FocusListener());
        password2EditText.setOnFocusChangeListener(new FocusListener());


        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account = accountEditText.getText().toString();
                String password1 = password1EditText.getText().toString();
                String password2 = password2EditText.getText().toString();
                String[] campusTypes = getResources().getStringArray(R.array.campus_types);
                String campus = campusTypes[(int) campusSpinner.getSelectedItemId()];

                if(password1.equals(password2)){
                    String password = Tools.md5(password1);
                    String url = String.format("%sregister?account=%s&name=%s&password=%s&campus=%s",Constant.SERVER_URL,account,account,password,campus);
                    new RegisterTask().execute(url);
                } else {
                    Toast.makeText(RegisterActivity.this,"密码不一致！",Toast.LENGTH_SHORT).show();
                }
            }
        });

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    class RegisterTask extends AsyncTask<String,Integer,Boolean>{

        @Override
        protected Boolean doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);
                String result = HttpUtil.sendGet(url);
                return (result!=null && result.equals(Constant.STATUS_OK));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            if(result){
                Toast.makeText(RegisterActivity.this,"注册成功！",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(RegisterActivity.this,"注册失败！",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
