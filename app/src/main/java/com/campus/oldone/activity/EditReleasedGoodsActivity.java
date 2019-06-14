package com.campus.oldone.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.Toast;

import com.campus.oldone.R;
import com.campus.oldone.adapter.ReleaseImageAdapter;
import com.campus.oldone.constant.Constant;
import com.campus.oldone.model.Goods;
import com.campus.oldone.utils.HttpUtil;
import com.google.gson.Gson;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

public class EditReleasedGoodsActivity extends BaseActivity {
    private static final String TAG = "mydebug:ERGA";

    private Goods goods;
    private static final int  REQUEST_CODE_CHOOSE = 23;
    private GridView gridView;
    private List<Uri> selectedPhotos;
    private List<String> images = new ArrayList<>();
    private Button btn_select;  //图片选择
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
        Intent intent = getIntent();
        goods = (Goods)intent.getSerializableExtra("goods");
        images = goods.getImages();
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
        et_content.setText(goods.getContent());
        et_title.setText(goods.getTitle());
        et_price.setText(goods.getPrice().toString());
        et_email.setText(goods.getEmail());
        et_phone.setText(goods.getPhone());
        String[] typesArray = this.getResources().getStringArray(R.array.goods_types);
        List<String> list = Arrays.asList(typesArray);
        types.setSelection(list.indexOf(goods.getType()),true);
        btn_release.setText("确认");
        btn_select.setText("重新选择");
        imageAdapter = new ReleaseImageAdapter(images,EditReleasedGoodsActivity.this,1);
        gridView.setAdapter(imageAdapter);
    }

    @Override
    protected void initListener() {
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获得读取内存权限
                if(ContextCompat.checkSelfPermission(EditReleasedGoodsActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(EditReleasedGoodsActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
                }else {
                    openAlbum();
                }
            }
        });

        btn_release.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goods.setTitle(et_title.getText().toString());
                goods.setContent(et_content.getText().toString());
                goods.setEmail(et_email.getText().toString());
                goods.setPhone(et_phone.getText().toString());
                goods.setType(types.getSelectedItem().toString());
                goods.setPrice(Double.parseDouble(et_price.getText().toString()));

                String url = Constant.SERVER_URL+"data";
                RequestBody requestBody = new FormBody.Builder()
                        .add("method","updateGoods")
                        .add("goods_data",new Gson().toJson(goods))
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
                                    Toast.makeText(EditReleasedGoodsActivity.this,"修改成功！",Toast.LENGTH_SHORT).show();
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
                                    Toast.makeText(EditReleasedGoodsActivity.this,"修改失败！",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK){
            selectedPhotos = Matisse.obtainResult(data);  //获得选中照片uri
            //展示照片
            imageAdapter = new ReleaseImageAdapter(selectedPhotos,EditReleasedGoodsActivity.this);
            gridView.setAdapter(imageAdapter);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    openAlbum();
                }else {
                    Toast.makeText(EditReleasedGoodsActivity.this, "请授权读取内存权限", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }

    private void openAlbum(){
        Matisse.from(EditReleasedGoodsActivity.this)
                .choose(MimeType.allOf())
                .countable(true)
                .maxSelectable(9)
                .capture(true)
                .captureStrategy(new CaptureStrategy(true,"com.compus.oldone.fileprovider"))
                .imageEngine(new GlideEngine())
                .theme(R.style.Matisse_Dracula)
                .forResult(REQUEST_CODE_CHOOSE);
    }

}
