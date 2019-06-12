package com.campus.oldone.activity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.campus.oldone.R;

import java.util.ArrayList;
import java.util.List;

public class ShareGoodsActivity extends BaseActivity {

    private ListView contactsview;
    private List<String> contacts;
    private ArrayAdapter<String> adapter;

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_share_goods);
    }

    @Override
    protected void initView() {
        contactsview = findViewById(R.id.contacts_list);
        contacts = new ArrayList<>();
        adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,contacts);
        contactsview.setAdapter(adapter);
        readContacts();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        //注册点击事件
        contactsview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int length = contacts.get(position).length();
                String number = contacts.get(position).substring(length-11);
                Intent lastIntent = getIntent();
                String content = lastIntent.getStringExtra("goodsContent");
                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:"+number));
                intent.putExtra("sms_body",content);
                startActivity(intent);
            }
        });
    }

    private void readContacts(){
        Cursor cursor = null;
        try{
            cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,null);
            if(cursor != null){
                while (cursor.moveToNext()){
                    String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    contacts.add(name+"\n"+number);
                }
                adapter.notifyDataSetChanged();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(cursor != null){
                cursor.close();
            }
        }
    }
}
