package com.campus.oldone.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.campus.oldone.R;
import com.campus.oldone.adapter.GoodsAdapter;
import com.campus.oldone.model.Goods;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class GoodsListFragment extends Fragment {
    private RecyclerView goodsRecyclerView;
    private List<Goods> goodsList = new ArrayList<>();

    public GoodsListFragment() {
        // Required empty public constructor
    }

    private void initView(View view){
        goodsRecyclerView = view.findViewById(R.id.goods_recycler_view);
    }

    private void initDataList(){
        goodsList.clear();
        goodsList.add(new Goods(R.drawable.img_test,"测试","内容测试",99.0));
    }

    private void initAdapter(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        goodsRecyclerView.setLayoutManager(layoutManager);
        GoodsAdapter goodsAdapter = new GoodsAdapter(goodsList);
        goodsRecyclerView.setAdapter(goodsAdapter);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_goods_list, container, false);
        initView(view);
        initDataList();
        initAdapter();
        return view;
    }

}
