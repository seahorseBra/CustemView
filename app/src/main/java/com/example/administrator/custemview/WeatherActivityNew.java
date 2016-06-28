package com.example.administrator.custemview;

import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import view.SunRiseView;
import view.WeatherImageView;

public class WeatherActivityNew extends FragmentActivity {

    private SunRiseView mSunrise;
    private int type;

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
        mSunrise.startAnimation();

        final WeatherImageView weatherBg = (WeatherImageView) findViewById(R.id.weather_image_bg);
        weatherBg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type>=3) {
                    type = 0;
                }
                weatherBg.setWeatherType(type++);
            }
        });

    }
}
