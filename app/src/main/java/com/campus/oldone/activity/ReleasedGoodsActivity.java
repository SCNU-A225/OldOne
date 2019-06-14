package com.campus.oldone.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.campus.oldone.R;
import com.campus.oldone.adapter.ReleasedGoodsAdapter;
import com.campus.oldone.constant.Constant;
import com.campus.oldone.model.Goods;
import com.campus.oldone.model.User;
import com.campus.oldone.utils.HttpUtil;
import com.campus.oldone.utils.PreferencesUtil;
import com.campus.oldone.utils.Tools;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ReleasedGoodsActivity extends BaseActivity {
    private static final String TAG = "mydeubg:RGA";

    private List<Goods> goodsList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ReleasedGoodsAdapter adapter;

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_released_goods);
    }

    @Override
    protected void initView() {
        recyclerView = findViewById(R.id.release_goods_rv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ReleasedGoodsAdapter(goodsList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        goodsList.clear();
        User user = PreferencesUtil.getUserInfo(this);
        String url = Constant.SERVER_URL+"data?method=getUserGoods&user_id="+user.getId();
        HttpUtil.sendOkHttpGetRequest(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: "+e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.header("data_result",Constant.STATUS_FAILED).equals(Constant.STATUS_OK)){
                    String json = response.body().string();
                    JsonArray jsonArray = new JsonParser().parse(json).getAsJsonArray();
                    goodsList = Tools.gson.fromJson(jsonArray,new TypeToken<List<Goods>>(){}.getType());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.refresh(goodsList);
                        }
                    });
                    Log.d(TAG, "onResponse: "+goodsList.size());
                } else {
                    Log.d(TAG, "false");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ReleasedGoodsActivity.this,"获取信息失败！",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    @Override
    protected void initListener() {

    }

}
