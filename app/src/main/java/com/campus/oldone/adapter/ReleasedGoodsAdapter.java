package com.campus.oldone.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.campus.oldone.R;
import com.campus.oldone.model.Goods;

import java.util.List;

public class ReleasedGoodsAdapter extends RecyclerView.Adapter<ReleasedGoodsAdapter.ViewHolder>{
        private List<Goods> goodsList;
        private Context context;


    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView goodsImage;
        TextView goodsTitle;
        TextView goodsPrice;
        TextView goodsLocation;
        Button button;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            goodsImage = (ImageView)itemView.findViewById(R.id.released_goods_img);
            goodsTitle = (TextView)itemView.findViewById(R.id.released_goods_title);
            goodsPrice = (TextView)itemView.findViewById(R.id.released_goods_price);
            goodsLocation = (TextView)itemView.findViewById(R.id.released_goods_location);
            button = (Button)itemView.findViewById(R.id.released_goods_button);
        }
    }

    public ReleasedGoodsAdapter(List<Goods> goodsList) {
        this.goodsList = goodsList;
    }


    @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            if(context == null){
                context = viewGroup.getContext();
            }
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.released_goods_item,viewGroup,false);
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
            if(goods.getSold()==0){
                viewHolder.button.setText("未出手");
//                viewHolder.button.setBackgroundColor(0x3F51B5);
//                viewHolder.button.setTextColor(0xFFFAFA);
                //TODO 点击事件转到物品编辑页面
            }else {
                viewHolder.button.setText("已出手");
            }
        }

        @Override
        public int getItemCount() {
            return goodsList.size();
        }



    }
