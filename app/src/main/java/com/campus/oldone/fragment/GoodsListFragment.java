package com.campus.oldone.fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.campus.oldone.R;
import com.campus.oldone.activity.LoginActivity;
import com.campus.oldone.adapter.GoodsAdapter;
import com.campus.oldone.constant.Constant;
import com.campus.oldone.model.Goods;
import com.campus.oldone.utils.HttpUtil;
import com.campus.oldone.utils.Tools;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class GoodsListFragment extends Fragment {
    private static final String TAG = "mydebug:GLF";
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView goodsRecyclerView;
    private GoodsAdapter goodsAdapter;
    private List<Goods> goodsList = new ArrayList<>();

    private boolean mIsViewCreate = false;		//视图还没准备好
    private boolean mIsVisible= false;		//不可见
    private boolean mIsFirstLoad = true;	//第一次加载


    public GoodsListFragment() {
        // Required empty public constructor
    }

    private void initView(View view){
        goodsRecyclerView = view.findViewById(R.id.goods_recycler_view);
        swipeRefreshLayout = view.findViewById(R.id.goods_list_swipe_refresh);
    }

    private void initDataList(){
        goodsList.clear();
        String type = getArguments().getString(Constant.TYPE_NAME);
        String url = Constant.SERVER_URL+"data?method=getAllGoods&type="+type;
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
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            goodsAdapter.refresh(goodsList);
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    });
                    Log.d(TAG, "onResponse: "+goodsList.size());
                } else {
                    Log.d(TAG, "false");
                }
            }
        });
    }

    private void initAdapter(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        goodsRecyclerView.setLayoutManager(layoutManager);
        goodsAdapter = new GoodsAdapter(getContext(),goodsList);
        goodsRecyclerView.setAdapter(goodsAdapter);
    }

    private void initListener(){
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initDataList();
            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_goods_list, container, false);
        initView(view);
        initAdapter();
        initListener();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mIsViewCreate = true;
        lazyLoad();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            mIsVisible = true;
            lazyLoad();
        } else {
            mIsVisible = false;
        }
    }

    private void lazyLoad() {
        if (!mIsViewCreate || !mIsVisible || !mIsFirstLoad) {
            return;
        }
        initDataList();
        mIsFirstLoad = false;
    }
}
