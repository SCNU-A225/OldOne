package com.campus.oldone.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
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

public class UnsaleGoodsActivity extends BaseActivity {

    private Goods goods;
    private List<String> images = new ArrayList<>();
    private Banner banner;
    private SuperTextView price;
    private SuperTextView title;
    private SuperTextView location;
    private SuperTextView content;
    private SuperTextView phone;
    private SuperTextView email;
    private Button comfirm;
    private Button edit;
    private Button delete;

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_unsale_goods);
    }

    @Override
    protected void initView() {
        Intent myIntent = getIntent();
        goods = (Goods)myIntent.getSerializableExtra("mygood");
        images = goods.getImages();
        banner = findViewById(R.id.unsale_goods_banner);
        price = findViewById(R.id.unsale_goods_price);
        title = findViewById(R.id.unsale_goods_title);
        location = findViewById(R.id.unsale_goods_location);
        content = findViewById(R.id.unsale_goods_content);
        phone = findViewById(R.id.unsale_goods_phone);
        email = findViewById(R.id.unsale_goods_email);
        comfirm = findViewById(R.id.unsale_goods_confirm);
        edit = findViewById(R.id.unsale_goods_edit);
        delete = findViewById(R.id.unsale_goods_delete);
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
    }

    @Override
    protected void initListener() {
        comfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmDialog();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteDialog();
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UnsaleGoodsActivity.this,EditReleasedGoodsActivity.class);
                intent.putExtra("goods",goods);
                startActivity(intent);
            }
        });
    }

    private void showConfirmDialog(){
        final AlertDialog.Builder confirmDialog = new AlertDialog.Builder(UnsaleGoodsActivity.this);
        confirmDialog.setIcon(R.drawable.ic_dialog_comfirm);
        confirmDialog.setTitle("OldOne");
        confirmDialog.setMessage("确认物品已出手");
        confirmDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //TODO 将物品的sold状态设为已出手并关闭
            }
        });
        confirmDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //TODO 关闭
            }
        });
        confirmDialog.show();
    }

    private void showDeleteDialog(){
        final AlertDialog.Builder deleteDialog = new AlertDialog.Builder(UnsaleGoodsActivity.this);
        deleteDialog.setIcon(R.drawable.delete);
        deleteDialog.setTitle("OldOne");
        deleteDialog.setMessage("是否删除物品");
        deleteDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //TODO 将物品的sold状态设为已出手并关闭
            }
        });
        deleteDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //TODO 关闭
            }
        });
        deleteDialog.show();
    }
}
