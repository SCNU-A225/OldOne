package com.campus.oldone.adapter;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.campus.oldone.R;
import com.campus.oldone.activity.GetPermissionActivity;
import com.campus.oldone.activity.MainActivity;
import com.campus.oldone.activity.ShowGoodsActivity;
import com.campus.oldone.app.MyApplication;
import com.campus.oldone.constant.Constant;
import com.campus.oldone.model.Goods;

import java.util.List;

public class GoodsAdapter extends RecyclerView.Adapter<GoodsAdapter.ViewHolder> {
    private static final String TAG = "mydebug:GA";
    private List<Goods> goodsList;
    private Context context;

    public void refresh(List<Goods> goodsList){
        this.goodsList = goodsList;
        notifyDataSetChanged();
    }

    public GoodsAdapter(Context context, List<Goods> goodsList) {
        this.goodsList = goodsList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if(context == null){
            context = viewGroup.getContext();
        }
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_goods,viewGroup,false);
        final ViewHolder holder = new ViewHolder(view);
        //点击展示物品详情
        holder.goodsCarView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,ShowGoodsActivity.class);
                int posistion = holder.getAdapterPosition();
                intent.putExtra("goods",goodsList.get(posistion));
                context.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Goods goods = goodsList.get(i);
        Glide.with(context).load(goods.getImages().get(0)).centerCrop().into(viewHolder.goodsImage);
        viewHolder.goodsTitle.setText(goods.getTitle());
        viewHolder.goodsLocation.setText(goods.getLocation());
        viewHolder.goodsPrice.setText(goods.getPrice()+"元");
    }

    @Override
    public int getItemCount() {
        return goodsList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView goodsImage;
        TextView goodsTitle;
        TextView goodsPrice;
        TextView goodsLocation;
        CardView goodsCarView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            goodsImage = itemView.findViewById(R.id.item_goods_img);
            goodsTitle = itemView.findViewById(R.id.item_goods_title);
            goodsPrice = itemView.findViewById(R.id.item_goods_price);
            goodsLocation = itemView.findViewById(R.id.item_goods_location);
            goodsCarView = itemView.findViewById(R.id.item_goods_carview);
        }
    }

}
