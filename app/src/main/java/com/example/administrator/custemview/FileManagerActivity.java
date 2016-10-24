package com.example.administrator.custemview;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.RemoteViews;

import adapter.MyTestAdapter;


public class FileManagerActivity extends BaseActivity {

//    private RecyclerView mFileList;
    private MyTestAdapter adapter;
    private TypedArray typedArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_manager);
        NotificationManager systemService = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        int color = Color.WHITE;
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            TypedValue typedValue = new TypedValue();
            getApplicationContext().getTheme().resolveAttribute(android.support.v7.appcompat.R.style.TextAppearance_StatusBar_EventContent_Title, typedValue, true);
            int[] attribute = new int[] {android.support.v7.appcompat.R.attr.titleTextColor};
            TypedArray array = getApplicationContext().obtainStyledAttributes(typedValue.resourceId, attribute);
            array.recycle();
        } else {
            TypedValue typedValue = new TypedValue();
            getApplicationContext().getTheme().resolveAttribute(android.support.v7.appcompat.R.style.TextAppearance_StatusBar_EventContent_Title, typedValue, true);
            int[] attribute = new int[] {android.support.v7.appcompat.R.style.TextAppearance_StatusBar_EventContent_Title};
            TypedArray array = getApplicationContext().obtainStyledAttributes(typedValue.resourceId, attribute);
            array.recycle();
        }


        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.custem_notify);
//        remoteViews.setTextColor(R.id.text, );
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            Notification builder = new Notification.Builder(this)
                        .setContentTitle("测试")
                        .setSmallIcon(R.mipmap.checkbox)
                        .setAutoCancel(false)
                        .setContent(remoteViews)
                        .setContentText("agaweg")
                        .build();
            systemService.notify(5555, builder);
        }
//        mFileList = (RecyclerView) findViewById(R.id.file_system_list);
//        inite();

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            Notification builder = new Notification.Builder(this)
                    .setContentTitle("测试")
                    .setSmallIcon(R.mipmap.checkbox)
                    .setAutoCancel(false)
                    .setContentText("agaweg")
                    .build();
            systemService.notify(222, builder);
        }
    }

   /* private void inite() {
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        ArrayList<String> list = new ArrayList<String>();
        list.add("egaweg");
        list.add("egaweg");
        list.add("egaweg");
        list.add("egaweg");
        list.add("egaweg");
        list.add("egaweg");
        list.add("egaweg");
        list.add("egaweg");
        list.add("egaweg");
        list.add("egaweg");
        list.add("egaweg");
        list.add("egaweg");
        mFileList.setLayoutManager(manager);
        adapter = new MyTestAdapter(this, list);
        mFileList.setAdapter(adapter);
    }*/

}
