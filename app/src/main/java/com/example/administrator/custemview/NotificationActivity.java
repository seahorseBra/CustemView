package com.example.administrator.custemview;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.nfc.tech.NfcA;
import android.os.Bundle;
import android.os.Parcel;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompatExtras;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.TextView;

public class NotificationActivity extends AppCompatActivity implements View.OnClickListener{

    private Button mStartNotifi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        mStartNotifi = (Button) findViewById(R.id.button);
        mStartNotifi.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:

                sendNotification(this);
                break;
        }
    }

    private void sendNotification(Context context) {
        MyView myView = new MyView(context);
        myView.bindDate("自定义的");

        Intent intent = new Intent();
        intent.setAction("com.example.administrator.custemview.VIEW_DRAG");
        PendingIntent pendingIntent = PendingIntent.getActivities(this, 111, new Intent[]{intent}, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = new NotificationCompat.Builder(this)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.sd_icon)
                .setContentTitle("测试")
                .setContentText("什么鬼")
                .setContentInfo("这又是什么卵")
                .setTicker("闪现文字哦！！")
                .setContent(myView)
                .setAutoCancel(false)
                .setOngoing(true)
                .build();
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(1, notification);

    }

    @SuppressLint("ParcelCreator")
    public class MyView extends RemoteViews {
        Context context;
        public MyView(Context context){
            super(context.getPackageName(), R.layout.notification_view);
            this.context = context;
        }


        public void bindDate(String text) {
            setTextViewText(R.id.text, text);
            TextView view = new TextView(context);
            view.getBackground();
            ImageView view1 = new ImageView(context);
//            view.setBackgroundResource();
        }
    }
}
