package com.campus.oldone.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.campus.oldone.R;
import com.campus.oldone.adapter.GlideImageAdapter;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;

public class ShowGoodsActivity extends BaseActivity{

    private List<String> images;
    private Banner banner;
    private TextView price;
    private TextView title;
    private TextView location;
    private Button contact;
    private TextView content;
    private TextView phone;
    private TextView email;
    private Button share;

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_show_goods);
    }

    @Override
    protected void initView() {
        banner = findViewById(R.id.showgoods_banner);
        price = findViewById(R.id.showgoods_price);
        title = findViewById(R.id.showgoods_title);
        location = findViewById(R.id.showgoods_location);
        contact = findViewById(R.id.showgoods_contact);
        content = findViewById(R.id.showgoods_content);
        phone = findViewById(R.id.showgoods_phone);
        email = findViewById(R.id.showgoods_email);
        share = findViewById(R.id.showgoods_share);
        images = new ArrayList<>();
        images.add("http://img.zcool.cn/community/0166c756e1427432f875520f7cc838.jpg");
        images.add("http://img.zcool.cn/community/018fdb56e1428632f875520f7b67cb.jpg");
        images.add("http://img.zcool.cn/community/01c8dc56e1428e6ac72531cbaa5f2c.jpg");
        images.add("http://img.zcool.cn/community/01fda356640b706ac725b2c8b99b08.jpg");
        images.add("http://img.zcool.cn/community/01fd2756e142716ac72531cbf8bbbf.jpg");
        images.add("http://img.zcool.cn/community/0114a856640b6d32f87545731c076a.jpg");

    }

    @Override
    protected void initData() {
        banner.setImageLoader(new GlideImageAdapter());
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        banner.setImages(images);
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.DepthPage);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(4000);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
    }

    @Override
    protected void initListener() {
        //短信联系
        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri smsToUri = Uri.parse("smsto:"+phone.getText().toString());
                Intent intent = new Intent(Intent.ACTION_SENDTO,smsToUri);
                startActivity(intent);
            }
        });

        //分享按钮
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowGoodsActivity.this,ShareGoodsActivity.class);
                String goodsContent = content.getText().toString();
                intent.putExtra("goodsContent",goodsContent);
                startActivity(intent);
            }
        });
    }

}
