package com.campus.oldone.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.allen.library.SuperTextView;
import com.campus.oldone.R;
import com.campus.oldone.adapter.GlideImageAdapter;
import com.campus.oldone.model.Goods;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;

public class SoldGoodsActivity extends BaseActivity{

    private Goods goods;
    private List<String> images = new ArrayList<>();
    private Banner banner;
    private SuperTextView price;
    private SuperTextView title;
    private SuperTextView location;
    private SuperTextView content;
    private SuperTextView phone;
    private SuperTextView email;
    private Button contact;
    private Button share;

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_show_goods);
    }

    @Override
    protected void initView() {
        Intent myIntent = getIntent();
        goods = (Goods)myIntent.getSerializableExtra("mygood");
        images = goods.getImages();
        banner = findViewById(R.id.showgoods_banner);
        price = findViewById(R.id.showgoods_price);
        title = findViewById(R.id.showgoods_title);
        location = findViewById(R.id.showgoods_location);
        contact = findViewById(R.id.showgoods_contact);
        content = findViewById(R.id.showgoods_content);
        phone = findViewById(R.id.showgoods_phone);
        email = findViewById(R.id.showgoods_email);
        share = findViewById(R.id.showgoods_share);
    }

    @Override
    protected void initData() {
        title.setLeftString(title.getLeftString()+goods.getTitle());
        price.setLeftString(price.getLeftString()+goods.getPrice()+"元");
        location.setLeftString(location.getLeftString()+goods.getLocation());
        phone.setLeftString(goods.getPhone());
        email.setLeftString(goods.getEmail());
        content.setLeftString(goods.getContent());
        images = goods.getImages();

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

        contact.setVisibility(View.INVISIBLE);
        share.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void initListener() {

    }

}
