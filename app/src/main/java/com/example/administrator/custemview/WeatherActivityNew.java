package com.example.administrator.custemview;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javaBean.HourWeather;
import view.FutureWeatherHour2;
import view.SunRiseView;
import view.WeatherImageView;

public class WeatherActivityNew extends FragmentActivity {

    private SunRiseView mSunrise;
    private int type;
    private FutureWeatherHour2 weather;
    private Button mButton;
    private FrameLayout mRoot;
    private ImageView mImage;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                Window window = getWindow();
//                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
//                        | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
//////                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//                window.setStatusBarColor(Color.TRANSPARENT);
//                window.setNavigationBarColor(Color.TRANSPARENT);
//            }


        }
        setContentView(R.layout.activity_weather_activity_new);
        mSunrise = (SunRiseView) findViewById(R.id.sunrise);
        mButton = (Button) findViewById(R.id.shortcut);
        mRoot = (FrameLayout) findViewById(R.id.root);
        mImage = (ImageView) findViewById(R.id.image);
        mSunrise.startAnimation();

        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.notify_test);
        remoteViews.setTextColor(R.id.text, Color.BLACK);
        remoteViews.setInt(R.id.text, "setBackgroundResource", R.drawable.notify_text_bg);

        Notification.Builder builder = new Notification.Builder(this)
                .setContent(remoteViews)
                .setContentText("222")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("111");
        Notification notification = builder.build();
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(1111, notification);

        final WeatherImageView weatherBg = (WeatherImageView) findViewById(R.id.weather_image_bg);
        weatherBg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type>=3) {
                    type = 0;
                }
                weatherBg.setWeatherType(type++,true);
            }
        });


        weather = (FutureWeatherHour2) findViewById(R.id.future_weather);

        weather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<HourWeather> hourWeathers = new ArrayList<>();
                for (int i = 0; i < 8; i++) {
                    hourWeathers.add(new HourWeather(2, 4, 99));
                }
                weather.setList(hourWeathers);
            }
        });

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shortCut();
            }
        });


    }

    private void shortCut() {
        final View decorView = getWindow().getDecorView();

        Rect rect = new Rect();
        decorView.getWindowVisibleDisplayFrame(rect);
        Bitmap bitmap = Bitmap.createBitmap(1080, 1920, Bitmap.Config.RGB_565);
        decorView.draw(new Canvas(bitmap));
        /*
        Bitmap bitmap = null;
        decorView.setDrawingCacheEnabled(true);
        decorView.destroyDrawingCache();
        decorView.buildDrawingCache();
        bitmap = decorView.getDrawingCache();
        decorView.setDrawingCacheEnabled(false);*/
            mImage.setImageBitmap(bitmap);
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        View view = getWindow().getDecorView();
        WindowManager.LayoutParams lp = (WindowManager.LayoutParams) view.getLayoutParams();
        lp.width = (metrics.widthPixels * 4) / 5;
        lp.height = (metrics.heightPixels * 4) / 5;
        lp.gravity  = Gravity.CENTER;

        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().getWindowManager().updateViewLayout(view, lp);


    }
}
