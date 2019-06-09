package com.campus.oldone.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.campus.oldone.R;

public class RegisterActivity extends BaseActivity {
    private static final String TAG = "mydebug:RA";

    private EditText accountEditText;
    private EditText password1EditText;
    private EditText password2EditText;
    private Spinner campusSpinner;
    private Button signUpButton;


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
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account = accountEditText.getText().toString();
                String password1 = password1EditText.getText().toString();
                String password2 = password2EditText.getText().toString();
                String[] campusTypes = getResources().getStringArray(R.array.campus_types);
                String campus = campusTypes[(int) campusSpinner.getSelectedItemId()];

            }
        });
    }
}
