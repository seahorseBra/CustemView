package com.example.administrator.custemview;

import android.os.Bundle;

import view.WeatherImageView;

public class WeatherBgActivity extends BaseActivity {

    private WeatherImageView mWeather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_bg);
        mWeather = (WeatherImageView) findViewById(R.id.weather);
        mWeather.setWeatherType(1, true);
    }
}
