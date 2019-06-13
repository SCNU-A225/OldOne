package com.campus.oldone.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toolbar;

import com.campus.oldone.R;
import com.campus.oldone.activity.ChangeActivity;
import com.campus.oldone.constant.Constant;
import com.campus.oldone.utils.HttpUtil;
import com.campus.oldone.utils.PreferencesUtil;
import com.campus.oldone.utils.Tools;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class CenterFragment extends Fragment {
    private static final String TAG = "mydebug:CF";
    private Toolbar toolbar;
    private NavigationView navigationView;
    private TextView releaseNum;
    private TextView saleNum;
    private TextView unsaleNum;


    private void initData(){
        String id = String.valueOf(PreferencesUtil.getUserInfo(getContext()).getId());
        String url = Constant.SERVER_URL+"data?method=queryUserGoodsNum&id="+id;
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
                    final List<Integer> data = Tools.gson.fromJson(jsonArray,new TypeToken<List<Integer>>(){}.getType());
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            releaseNum.setText(String.valueOf(data.get(0)));
                            saleNum.setText(String.valueOf(data.get(1)));
                            unsaleNum.setText(String.valueOf(data.get(2)));
                        }
                    });
                } else {
                    Log.d(TAG, "false");
                }
            }
        });
    }

    public CenterFragment() {
        // Required empty public constructor
    }

    private void initView(View view){
        navigationView = view.findViewById(R.id.center_nav_view);
        navigationView.setCheckedItem(R.id.center_menu_name);
        toolbar = view.findViewById(R.id.center_toolbar);
        View headerView = navigationView.getHeaderView(0);//获得个人中心头部view
        releaseNum = headerView.findViewById(R.id.fragment_center_releaseNum);
        saleNum = headerView.findViewById(R.id.fragment_center_saleNum);
        unsaleNum = headerView.findViewById(R.id.fragment_center_unsaleNum);
    }

    public void initListener() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                menuItem.setChecked(true);
                Intent intent = new Intent(getActivity(), ChangeActivity.class);
                intent.putExtra("menuItemID",menuItem.getItemId());
                startActivity(intent);
                return true;
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_center, container, false);
        initView(view);
        initData();
        initListener();
        return view;
    }


}
