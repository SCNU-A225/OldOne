package com.campus.oldone.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.campus.oldone.R;
import com.campus.oldone.adapter.ReleaseImageAdapter;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ReleaseActivity extends BaseActivity {
    private static final int  REQUEST_CODE_CHOOSE = 23;

    private GridView gridView;
    private List<Uri> selectedPhotos;
    private Button btn_select;
    private Button btn_release; //发布键
    private Button btn_back; //返回键
    private EditText et_content; //详情内容
    private EditText et_title; //标题
    private EditText et_price; //价格
    private EditText et_email; //邮箱
    private EditText et_phone; //电话
    private Spinner types; //类型
    private ReleaseImageAdapter imageAdapter;

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_release);
    }

    @Override
    protected void initView() {
        gridView = findViewById(R.id.release_gridview);
        btn_select = findViewById(R.id.release_select);
        btn_release = findViewById(R.id.release_confirm);
        btn_back = findViewById(R.id.release_back);
        et_content = findViewById(R.id.release_content);
        et_title = findViewById(R.id.release_title);
        et_price = findViewById(R.id.release_price);
        et_email = findViewById(R.id.release_email);
        et_phone = findViewById(R.id.release_phone);
        types = findViewById(R.id.release_spinner);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        btn_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Matisse.from(ReleaseActivity.this)
                        .choose(MimeType.allOf())
                        .countable(true)
                        .maxSelectable(9)
                        .capture(true)
                        .captureStrategy(new CaptureStrategy(true,"com.compus.oldone.fileprovider"))
                        .imageEngine(new GlideEngine())
                        .theme(R.style.Matisse_Dracula)
                        .forResult(REQUEST_CODE_CHOOSE);
                btn_select.setText("重新选择");
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK){
            selectedPhotos = Matisse.obtainResult(data);  //获得选中照片uri
            //展示照片
            imageAdapter = new ReleaseImageAdapter(selectedPhotos,ReleaseActivity.this);
            gridView.setAdapter(imageAdapter);
        }
    }


}
