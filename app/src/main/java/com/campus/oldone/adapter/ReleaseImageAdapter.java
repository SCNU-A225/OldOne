package com.campus.oldone.adapter;

import android.content.Context;
import android.graphics.Picture;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.campus.oldone.R;

import java.util.LinkedList;
import java.util.List;

public class ReleaseImageAdapter extends BaseAdapter {

    private Context context;
    List<Uri> photos = new LinkedList<>();

    public ReleaseImageAdapter(List<Uri> list, Context context){
        this.context = context;
        for(int i=0; i<list.size(); i++){
            photos.add(list.get(i));
        }
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

        Uri imageUri = photos.get(position);
        viewHolder.image.setImageURI(imageUri);
        return convertView;
    }

    class ViewHolder{
        public ImageView image;
    }
}
