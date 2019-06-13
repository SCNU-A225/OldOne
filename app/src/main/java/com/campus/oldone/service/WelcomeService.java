package com.campus.oldone.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.campus.oldone.R;
import com.campus.oldone.activity.MainActivity;
import com.campus.oldone.adapter.HomeTabAdapter;

import java.util.Timer;
import java.util.TimerTask;

public class WelcomeService extends Service {
    private static final String TAG = "mydebug:ARS";
    private long startTime;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String name = intent.getStringExtra("user_name");
        startForeground(1,getNotification("OldOne","欢迎使用OldOne，"+name));
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                onDestroy();
            }
        };
        timer.schedule(task,5*60*1000);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        stopForeground(true);
        super.onDestroy();
    }

    private NotificationManager getNotificationManager(){
        return (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    private Notification getNotification(String title, String content){
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this,0,intent,0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"oldone_info")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher))
                .setContentIntent(pi)
                .setContentTitle(title)
                .setContentText(content);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            String id = "oldone_info";
            NotificationChannel channel = new NotificationChannel(id,"OldeOne提示",NotificationManager.IMPORTANCE_DEFAULT);
            getNotificationManager().createNotificationChannel(channel);
            builder.setChannelId(id);
        }
        return builder.build();
    }

}
