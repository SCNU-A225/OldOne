package com.campus.oldone.fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.bumptech.glide.Glide;
import com.campus.oldone.R;
import com.campus.oldone.adapter.HomeTabAdapter;
import com.campus.oldone.constant.Constant;
import com.campus.oldone.model.Goods;
import com.campus.oldone.utils.HttpUtil;
import com.campus.oldone.utils.ImageUtil;
import com.campus.oldone.utils.Tools;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 主页Fragment
 */
public class HomeFragment extends Fragment {
    private static final String TAG = "mydebug:HF";

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private Button releaseButton;
    private List<Fragment> fragmentList = new ArrayList<>();


    public HomeFragment() {
    }

    private void initTab(){
        viewPager.setAdapter(new HomeTabAdapter(getChildFragmentManager(),Constant.ALL_TYPE_NAME,fragmentList));
        tabLayout.setupWithViewPager(viewPager);
    }

    private void initView(View view){
        viewPager = view.findViewById(R.id.home_view_pager);
        tabLayout = view.findViewById(R.id.home_tab);
        releaseButton = view.findViewById(R.id.home_release);
    }

    private void initFragmentList(){
        for(String name: Constant.ALL_TYPE_NAME){
            GoodsListFragment goodsListFragment = new GoodsListFragment();
            Bundle bundle = new Bundle();
            bundle.putString(Constant.TYPE_NAME, name);
            goodsListFragment.setArguments(bundle);
            fragmentList.add(goodsListFragment);
        }
    }

    private void initListener(){
        releaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Goods goods = new Goods(R.drawable.img_test,"测试","内容测试",99.0,"位置","18613189882","1197749258@qq.com");
                Gson gson = new Gson();
                Log.d(TAG, gson.toJson(goods));


//                File file = new File();
//                    new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//                            try {
//                                File file =Glide.with(getContext())
//                                        .asFile()
//                                        .load("http://n.sinaimg.cn/news/1_img/upload/cf3881ab/66/w1000h666/20190604/5ad7-hxyuaph3003402.jpg")
//                                        .submit()
//                                        .get();
//                                String fileName = System.currentTimeMillis()+"_"+Tools.random.nextInt(5000) + ImageUtil.getSuffix(ImageUtil.getMimeType(file));
//                                RequestBody requestBody = new MultipartBody.Builder()
//                                        .setType(MultipartBody.FORM)
//                                        .addFormDataPart("title","吃吃吃")
//                                        .addFormDataPart("content","测试内容")
//                                        .addFormDataPart("campus","南海校区")
//                                        .addFormDataPart("price","100")
//                                        .addFormDataPart("img",fileName, RequestBody.create(MediaType.parse("image/*"),file))
//                                        .build();
//                                String url = Constant.SERVER_URL+"release";
//                                HttpUtil.sendOkHttpPostRequest(url, requestBody, new Callback() {
//                                    @Override
//                                    public void onFailure(Call call, IOException e) {
//                                        Log.d(TAG, "onFailure: "+e.getMessage());
//                                    }
//
//                                    @Override
//                                    public void onResponse(Call call, Response response) throws IOException {
//                                        Log.d(TAG, "onResponse: 成功");
//                                    }
//                                });
//                            } catch (ExecutionException e) {
//                                e.printStackTrace();
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                            }
//
//                        }
//                    }).start();


            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initView(view);
        initFragmentList();
        initTab();
        initListener();
        return view;
    }

}
