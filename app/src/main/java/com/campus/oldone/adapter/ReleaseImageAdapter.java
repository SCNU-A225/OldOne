package com.campus.oldone.adapter;

import android.content.Context;
import android.graphics.Picture;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.campus.oldone.R;

import java.util.LinkedList;
import java.util.List;

public class ReleaseImageAdapter extends BaseAdapter {

    private Context context;
    private int type;
    List<Uri> photos = new LinkedList<>();
    List<String> photosUrl = new LinkedList<>();

    public ReleaseImageAdapter(List<Uri> list, Context context){
        this.context = context;
        for(int i=0; i<list.size(); i++){
            photos.add(list.get(i));
        }
        this.type = 0;
    }

    public ReleaseImageAdapter(List<String> list, Context context, int type){
        this.context = context;
        this.photosUrl = list;
        this.type = type;
    }
    @Override
    public int getCount() {
        if(photos != null){
            return photos.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return photos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(this.context).inflate(R.layout.release_imageitem,null);
            viewHolder.image = (ImageView)convertView.findViewById(R.id.release_imageitem);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if(type==0) {
            Uri imageUri = photos.get(position);
            viewHolder.image.setImageURI(imageUri);
        }else {
            String imageUrl = photosUrl.get(position);
            Glide.with(context).load(imageUrl).into(viewHolder.image);
        }
        return convertView;
    }

    class ViewHolder{
        public ImageView image;
    }
}
