package com.campus.oldone.activity;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.allen.library.SuperTextView;
import com.campus.oldone.R;
import com.campus.oldone.adapter.GlideImageAdapter;
import com.campus.oldone.model.Goods;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;

public class ShowGoodsActivity extends BaseActivity{

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
        Intent intent = getIntent();
        Goods goods = (Goods) intent.getSerializableExtra("goods");
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
        //短信联系
        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri smsToUri = Uri.parse("smsto:"+phone.getLeftString());
                Intent intent = new Intent(Intent.ACTION_SENDTO,smsToUri);
                startActivity(intent);
            }
        });

        //分享按钮
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowGoodsActivity.this,ShareGoodsActivity.class);
                String goodsContent = content.getLeftString();
                intent.putExtra("goodsContent",goodsContent);
                startActivity(intent);
            }
        });

        phone.setOnSuperTextViewClickListener(new SuperTextView.OnSuperTextViewClickListener() {
            @Override
            public void onClickListener(SuperTextView superTextView) {
                final String[] items = new String[]{"复制到粘贴板", "电话联系"};
                AlertDialog alertDialog = new AlertDialog.Builder(ShowGoodsActivity.this)
                        .setTitle("请选择操作")
                        .setIcon(R.mipmap.ic_launcher)
                        .setItems(items, new DialogInterface.OnClickListener() {//添加列表
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                switch (i){
                                    case 0:
                                        ClipboardManager cmb = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                                        ClipData clipData = ClipData.newPlainText(null, phone.getLeftString());
                                        cmb.setPrimaryClip(clipData);
                                        break;
                                    case 1:
                                        Uri phoneCallUri = Uri.parse("tel:" + phone.getLeftString());
                                        Intent intent = new Intent(Intent.ACTION_DIAL,phoneCallUri);
                                        startActivity(intent);
                                        break;
                                    default:
                                        break;
                                }
                            }
                        })
                        .create();
                alertDialog.show();
            }
        });

        email.setOnSuperTextViewClickListener(new SuperTextView.OnSuperTextViewClickListener() {
            @Override
            public void onClickListener(SuperTextView superTextView) {
                final String[] items = new String[]{"复制到粘贴板", "发送邮件"};
                AlertDialog alertDialog = new AlertDialog.Builder(ShowGoodsActivity.this)
                        .setTitle("请选择操作")
                        .setIcon(R.mipmap.ic_launcher)
                        .setItems(items, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                switch (i){
                                    case 0:
                                        ClipboardManager cmb = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                                        ClipData clipData = ClipData.newPlainText(null, phone.getLeftString());
                                        cmb.setPrimaryClip(clipData);
                                        break;
                                    case 1:
                                        Intent data=new Intent(Intent.ACTION_SENDTO);
                                        data.setData(Uri.parse("mailto:"+email.getLeftString()));
                                        data.putExtra(Intent.EXTRA_SUBJECT, "这是标题");
                                        data.putExtra(Intent.EXTRA_TEXT, "这是内容");
                                        startActivity(data);
                                        break;
                                    default:
                                        break;
                                }
                            }
                        })
                        .create();
                alertDialog.show();
            }
        });
    }

}
