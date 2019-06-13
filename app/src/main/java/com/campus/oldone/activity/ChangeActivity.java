package com.campus.oldone.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.campus.oldone.R;
import com.campus.oldone.model.User;
import com.campus.oldone.utils.PreferencesUtil;

import java.util.HashMap;
import java.util.Map;

public class ChangeActivity extends BaseActivity {

    private Button confirmButton;
    private Button backButton;
    private EditText changeEditText;
    private EditText changeEditTextConfirm;
    private int changeData;
    private Map<Object,Integer> mMap = new HashMap<>();

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
        mMap.put("R.id.center_menu_name",1);
        mMap.put("R.id.center_menu_email",2);
        mMap.put("R.id.center_menu_phone",3);
        mMap.put("R.id.center_menu_area",4);
        mMap.put("R.id.center_menu_username",5);
        mMap.put("R.id.center_menu_password",6);
        changeData = getIntent().getIntExtra("menuItemID",0);
        Log.d("ChangeActivity1", String.valueOf(changeData));
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
//        为不同修改设置不同触发事件
        switch(changeData){
            case R.id.center_menu_name:
                confirmButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                将改动保存进user对象中
                    userUpdate.setName(changeEditText.getText().toString());
                }
            }); break;
            case R.id.center_menu_email:
                confirmButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                将改动保存进user对象中
                        userUpdate.setEmail(changeEditText.getText().toString());
                    }
                });break;
            case R.id.center_menu_phone:
                confirmButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                将改动保存进user对象中
                        userUpdate.setPhone(changeEditText.getText().toString());
                    }
                });break;
            case R.id.center_menu_area:
                confirmButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                 将改动保存进user对象中
                        userUpdate.setCampus(changeEditText.getText().toString());
                    }
                }); break;
            case R.id.center_menu_username:
                confirmButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                将改动保存进user对象中
                    userUpdate.setAccount(changeEditText.getText().toString());
                }
            });break;
            case R.id.center_menu_password:
                confirmButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                将改动保存进user对象中
                    if(changeEditText.getText().equals(changeEditTextConfirm.getText()) ) userUpdate.setPassword(changeEditText.getText().toString());
                    else{
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
                }
            }); break;
            default:
        }

    }

    private void intUI() {
        changeEditText.setText(changeData);
//        Log.d("ChangeActivity",changeData);
        switch(changeData){
            case R.id.center_menu_name: changeEditText.setText(userUpdate.getName()); break;
            case R.id.center_menu_email: changeEditText.setText(userUpdate.getEmail()); break;
            case R.id.center_menu_phone: changeEditText.setText(userUpdate.getPhone()); break;
            case R.id.center_menu_area: changeEditText.setText(userUpdate.getCampus()); break;
            case R.id.center_menu_username: changeEditText.setText(userUpdate.getAccount()); break;
            case R.id.center_menu_password: changeEditTextConfirm.setVisibility(View.VISIBLE);break;
            default:
        }
    }

}