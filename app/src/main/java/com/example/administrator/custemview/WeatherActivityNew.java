package com.example.administrator.custemview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import view.SunRiseView;

public class WeatherActivityNew extends AppCompatActivity {

    private SunRiseView mSunrise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_activity_new);
        mSunrise = (SunRiseView) findViewById(R.id.sunrise);

        mSunrise.startAnimation();
    }
}
