package com.campus.oldone.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

import com.campus.oldone.R;
import com.campus.oldone.adapter.GoodsAdapter;
import com.campus.oldone.model.Goods;

import java.util.ArrayList;
import java.util.List;

import static com.campus.oldone.app.MyApplication.getContext;

public class SearchActivity extends BaseActivity {
    private SearchView searchView;
    private RecyclerView recyclerView;
    private GoodsAdapter adapter;
    private Button returnButton;
    private List<Goods> allGoods;
    private List<Goods> curList;

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_search);
    }

    @Override
    protected void initView() {
        searchView = findViewById(R.id.search_search_view);
        recyclerView = findViewById(R.id.search_recycler_view);
        returnButton = findViewById(R.id.search_return);
    }

    @Override
    protected void initData() {
        allGoods = (List<Goods>) getIntent().getSerializableExtra("all_goods");
        curList = new ArrayList<>();
        curList.addAll(allGoods);
        adapter = new GoodsAdapter(getContext(),curList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        searchView.onActionViewExpanded();
    }

    @Override
    protected void initListener() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                curList.clear();
                for(Goods goods:allGoods){
                    if(goods.getTitle().contains(query)){
                        curList.add(goods);
                    }
                }
                adapter.refresh(curList);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
