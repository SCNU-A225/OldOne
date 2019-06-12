package com.campus.oldone.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.campus.oldone.R;
import com.campus.oldone.adapter.ReleasedGoodsAdapter;
import com.campus.oldone.model.Goods;

import java.util.ArrayList;
import java.util.List;

public class ReleasedGoodsActivity extends BaseActivity {

    private List<Goods> goodsList;
    private RecyclerView recyclerView;

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_released_goods);
    }

    @Override
    protected void initView() {
        recyclerView = (RecyclerView)findViewById(R.id.release_goods_rv);
        initGoodsList();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        ReleasedGoodsAdapter adapter = new ReleasedGoodsAdapter(goodsList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    private void initGoodsList(){
        goodsList = new ArrayList<>();
        Goods goods3 = new Goods(R.drawable.img_test,"测试","内容测试",99.0,"位置","18613189882","1197749258@qq.com");
        goods3.setSold(1);
        goodsList.add(new Goods(R.drawable.img_test,"测试","内容测试",99.0,"位置","18613189882","1197749258@qq.com"));
        goodsList.add(new Goods(R.drawable.img_test,"测试","内容测试",99.0,"位置","18613189882","1197749258@qq.com"));
        goodsList.add(goods3);
    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_released_goods);
//    }
}
