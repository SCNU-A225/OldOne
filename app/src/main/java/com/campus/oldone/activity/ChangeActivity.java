package com.campus.oldone.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.campus.oldone.R;
import com.campus.oldone.constant.Constant;
import com.campus.oldone.model.User;
import com.campus.oldone.utils.HttpUtil;
import com.campus.oldone.utils.PreferencesUtil;
import com.campus.oldone.utils.Tools;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ChangeActivity extends BaseActivity {
    private static final String TAG = "mydebug:CA";

    private Button confirmButton;
    private Button backButton;
    private EditText changeEditText;
    private EditText changeEditTextConfirm;
    private int changeData;

//  测试
    private User userUpdate;
    public void setUserUpdate() {
        userUpdate =  PreferencesUtil.getUserInfo(this);
    }

    @Override
    protected void initView() {
        confirmButton = findViewById(R.id.change_confirm);
        changeEditText = findViewById(R.id.change_edit_text);
        backButton = findViewById(R.id.change_back);
        changeEditTextConfirm = findViewById(R.id.change_edit_text_confirm);
//        user
        setUserUpdate();
    }

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_change);
    }

    @Override
    protected void initData() {
        changeData = getIntent().getIntExtra("menuItemID",0);
        intUI();
    }

    @Override
    protected void initListener() {
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(changeData) {
                    case R.id.center_menu_name:
                        userUpdate.setName(changeEditText.getText().toString());
                        break;
                    case R.id.center_menu_email:
                        userUpdate.setEmail(changeEditText.getText().toString());
                        break;
                    case R.id.center_menu_phone:
                        userUpdate.setPhone(changeEditText.getText().toString());
                        break;
                    case R.id.center_menu_area:
                        userUpdate.setCampus(changeEditText.getText().toString());
                        break;
                    case R.id.center_menu_username:
                        userUpdate.setAccount(changeEditText.getText().toString());
                        if (changeEditText.getText().equals(changeEditTextConfirm.getText())) {
                            String password = Tools.md5(changeEditText.getText().toString());
                            userUpdate.setPassword(password);
                        } else {
                            AlertDialog.Builder dialog = new AlertDialog.Builder(ChangeActivity.this);
                            dialog.setMessage("两次密码输入不一致");
                            dialog.setCancelable(false);
                            dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    changeEditTextConfirm.clearComposingText();
                                }
                            });
                            dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    changeEditTextConfirm.clearComposingText();
                                }
                            });
                            dialog.show();
                        }
                        break;
                    default:
                }

                String url = Constant.SERVER_URL+"data";
                RequestBody requestBody = new FormBody.Builder()
                        .add("method","updateUser")
                        .add("user_data",new Gson().toJson(userUpdate))
                        .build();
                HttpUtil.sendOkHttpPostRequest(url, requestBody, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.d(TAG, "onFailure: ");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if(response.header("data_result",Constant.STATUS_FAILED).equals(Constant.STATUS_OK)){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(ChangeActivity.this,"修改成功！",Toast.LENGTH_SHORT).show();
                                    PreferencesUtil.saveUserInfo(ChangeActivity.this,userUpdate);
                                    TimerTask timerTask = new TimerTask() {
                                        @Override
                                        public void run() {
                                            finish();
                                        }
                                    };
                                    Timer timer = new Timer();
                                    timer.schedule(timerTask,2000);
                                }
                            });
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(ChangeActivity.this,"修改失败！",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });
            }
        });

    }

    private void intUI() {
        changeEditText.setText(changeData);
        switch(changeData){
            case R.id.center_menu_name: changeEditText.setText(userUpdate.getName()); break;
            case R.id.center_menu_email: changeEditText.setText(userUpdate.getEmail()); break;
            case R.id.center_menu_phone: changeEditText.setText(userUpdate.getPhone()); break;
            case R.id.center_menu_area: changeEditText.setText(userUpdate.getCampus()); break;
            case R.id.center_menu_username: changeEditText.setText(userUpdate.getAccount()); break;
            case R.id.center_menu_password: 
                changeEditTextConfirm.setVisibility(View.VISIBLE);
                changeEditText.setHint("新密码");
                break;
            default:
        }
    }

}