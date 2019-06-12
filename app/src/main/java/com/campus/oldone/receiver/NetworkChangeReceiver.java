package com.campus.oldone.receiver;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.campus.oldone.R;
import com.campus.oldone.activity.MainActivity;

import static android.content.Context.NOTIFICATION_SERVICE;


public class NetworkChangeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        NotificationManager manager=
                (NotificationManager)context.getSystemService(NOTIFICATION_SERVICE);

        if(networkInfo!=null && networkInfo.isAvailable()){
            if(Build.VERSION.SDK_INT>=26) {
                NotificationChannel channel = new NotificationChannel("networkLink", "link", NotificationManager.IMPORTANCE_DEFAULT);
                manager.createNotificationChannel(channel);
                Notification notification =new NotificationCompat.Builder(context,"networkLink")
                        .setContentTitle("OldOne")
                        .setContentText("网络已连接")
                        .setWhen(System.currentTimeMillis())
                        .setSmallIcon(R.mipmap.ic_launcher_round)
                        .build();
                manager.notify(1,notification);
            }
            else {
                Toast.makeText(context, "网络已连接", Toast.LENGTH_SHORT).show();
            }
        }else {

            if(Build.VERSION.SDK_INT>=26) {
                NotificationChannel channel = new NotificationChannel("networkDrop", "drop", NotificationManager.IMPORTANCE_DEFAULT);
                manager.createNotificationChannel(channel);
                Notification notification =new NotificationCompat.Builder(context,"networkDrop")
                        .setContentTitle("OldOne")
                        .setContentText("网络不可用")
                        .setWhen(System.currentTimeMillis())
                        .setSmallIcon(R.mipmap.ic_launcher_round)
                        .build();
                manager.notify(1,notification);
            }
            else {
                Toast.makeText(context, "网络不可用", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
