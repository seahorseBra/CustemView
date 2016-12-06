package com.example.administrator.custemview;


import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RemoteViews;
import android.widget.TextView;

import adapter.MyTestAdapter;


public class FileManagerActivity extends BaseActivity {

//    private RecyclerView mFileList;
    private MyTestAdapter adapter;
    private TypedArray typedArray;
    private LinearLayout mDelayGroup;
    private Button mShowDialog;
    private PopupWindow popupWindow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_manager);
        NotificationManager systemService = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mDelayGroup = (LinearLayout) findViewById(R.id.delay_root);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            textView.setTextAppearance(context, android.R.style.TextAppearance_Material_Notification_Title);
//        } else {
//            textView.setTextAppearance(context, android.R.style.TextAppearance_StatusBar_EventContent_Title);
//        }
        mShowDialog = (Button) findViewById(R.id.show_dialog);
        fastSetClickBehave(R.id.show_dialog);
        TextView textView = new TextView(this);
        textView.setTextAppearance(this, android.R.style.TextAppearance_Material_Notification_Title);
        int textColor = textView.getCurrentTextColor();
        int temp = textColor&0x00ffffff;
        int color = temp|0x99000000;
        String[] s = new String[]{"#"+Integer.toHexString(textColor), "#"+Integer.toHexString(color)};
        int i = Color.parseColor(s[0]);
        int j = Color.parseColor(s[1]);
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.custem_notify);
        remoteViews.setTextColor(R.id.text, i);
        remoteViews.setTextColor(R.id.text2, j);
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


    private void showPopUp(Context context, View v) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_layout, null);

        popupWindow = new PopupWindow(view,800,400);
        popupWindow.setAnimationStyle(R.style.popup_window_anim);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.transparent)));

        int[] location = new int[2];
        v.getLocationOnScreen(location);

        popupWindow.showAtLocation(v, Gravity.NO_GRAVITY, location[0] - popupWindow.getWidth()/2 + v.getWidth()/2, location[1]-popupWindow.getHeight());
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
    public void showOrHide(View view) {
        TranslateAnimation animationShow = new TranslateAnimation(TranslateAnimation.RELATIVE_TO_SELF, 0,
                TranslateAnimation.RELATIVE_TO_SELF, 0,
                TranslateAnimation.RELATIVE_TO_SELF, 1f,
                TranslateAnimation.RELATIVE_TO_SELF, 0);
        TranslateAnimation animationHide = new TranslateAnimation(TranslateAnimation.RELATIVE_TO_SELF, 0,
                TranslateAnimation.RELATIVE_TO_SELF, 0,
                TranslateAnimation.RELATIVE_TO_SELF, 0,
                TranslateAnimation.RELATIVE_TO_SELF, 1f);
        animationShow.setDuration(300);
        animationHide.setDuration(300);
        if (mDelayGroup.getVisibility() != View.VISIBLE) {
            mDelayGroup.startAnimation(animationShow);
            mDelayGroup.setVisibility(View.VISIBLE);
        } else {
            mDelayGroup.startAnimation(animationHide);
            mDelayGroup.setVisibility(View.INVISIBLE);
        }
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.show_dialog:
                showDialog(v);
                break;
        }
    }

    private void showDialog(View v) {
        if (popupWindow != null &&popupWindow.isShowing()) {
            popupWindow.dismiss();
        } else {
            showPopUp(getApplicationContext(), v);
        }
    }
}
