package com.campus.oldone.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.campus.oldone.R;
import com.campus.oldone.adapter.ReleaseImageAdapter;
import com.campus.oldone.constant.Constant;
import com.campus.oldone.model.User;
import com.campus.oldone.utils.HttpUtil;
import com.campus.oldone.utils.ImageUtil;
import com.campus.oldone.utils.PreferencesUtil;
import com.campus.oldone.utils.Tools;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.Response;


public class ReleaseActivity extends BaseActivity {
    private static final int  REQUEST_CODE_CHOOSE = 23;
    private static final String TAG = "mydebug:RA";

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
                //获得读取内存权限
                if(ContextCompat.checkSelfPermission(ReleaseActivity.this,Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(ReleaseActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
                }else {
                    openAlbum();
                }
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_release.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                                User user = PreferencesUtil.getUserInfo(ReleaseActivity.this);
                                Map<String,String> parms = new HashMap<>();
                                parms.put("title",et_title.getText().toString());
                                parms.put("content",et_content.getText().toString());
                                parms.put("location",user.getCampus());
                                parms.put("price",et_price.getText().toString());
                                parms.put("email",et_email.getText().toString());
                                parms.put("phone",et_phone.getText().toString());
                                parms.put("type",types.getSelectedItem().toString());
                                parms.put("sold","0");
                                parms.put("ownerId", String.valueOf(user.getId()));

                                MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
                                for(Map.Entry<String,String> entry : parms.entrySet()){
                                    builder.addFormDataPart(entry.getKey(),entry.getValue());
                                }
                                if(selectedPhotos != null){
                                    for(Uri uri:selectedPhotos){
                                        File img = new File(ImageUtil.getRealFilePath(ReleaseActivity.this,uri));
                                        String imgName = System.currentTimeMillis()+"_"+ Tools.random.nextInt(5000) + ImageUtil.getSuffix(ImageUtil.getMimeType(img));
                                        builder.addFormDataPart("img",imgName,RequestBody.create(MediaType.parse("image/*"),img));
                                    }
                                }

                                String url = Constant.SERVER_URL+"release";
                                HttpUtil.sendOkHttpPostRequest(url, builder.build(), new Callback() {
                                    @Override
                                    public void onFailure(Call call, IOException e) {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(ReleaseActivity.this,"网络错误！",Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }

                                    @Override
                                    public void onResponse(Call call, Response response) throws IOException {
                                        if(response.header("release_result",Constant.STATUS_FAILED).equals(Constant.STATUS_OK)){
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Toast.makeText(ReleaseActivity.this,"发布成功！",Toast.LENGTH_SHORT).show();
                                                    TimerTask timerTask = new TimerTask() {
                                                        @Override
                                                        public void run() {
                                                            Intent intent = new Intent(ReleaseActivity.this,MainActivity.class);
                                                            startActivity(intent);
                                                        }
                                                    };
                                                    Timer timer = new Timer();
                                                    timer.schedule(timerTask,3000);

                                                }
                                            });
                                        } else {
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Toast.makeText(ReleaseActivity.this,"发布失败！",Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }
                                    }
                                });

                        }
                    }).start();
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

    private void openAlbum(){
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    openAlbum();
                }else {
                    Toast.makeText(ReleaseActivity.this, "请授权读取内存权限", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }
}
