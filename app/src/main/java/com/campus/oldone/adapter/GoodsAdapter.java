package com.campus.oldone.adapter;

import android.app.PendingIntent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.campus.oldone.R;
import com.campus.oldone.model.Goods;

import java.util.List;

public class GoodsAdapter extends RecyclerView.Adapter<GoodsAdapter.ViewHolder> {
    private List<Goods> goodsList;

    public GoodsAdapter(List<Goods> goodsList) {
        this.goodsList = goodsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_goods,viewGroup,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Goods goods = goodsList.get(i);
        viewHolder.goodsImage.setImageResource(goods.getImageId());
        viewHolder.goodsTitle.setText(goods.getTitle());
        viewHolder.goodsLocation.setText(goods.getLocation());
        viewHolder.goodsPrice.setText(goods.getPrice()+"");
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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            goodsImage = itemView.findViewById(R.id.item_goods_img);
            goodsTitle = itemView.findViewById(R.id.item_goods_title);
            goodsPrice = itemView.findViewById(R.id.item_goods_price);
            goodsLocation = itemView.findViewById(R.id.item_goods_location);
        }
    }
}
