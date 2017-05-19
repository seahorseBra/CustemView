package com.example.administrator.custemview;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import view.WeatherImageView;

public class WeatherBgActivity extends BaseActivity {

    private WeatherImageView mWeather;
    private Button button;

    private int[] w = new int[]{0,1,8,14,30};
    private int index = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_bg);
        mWeather = (WeatherImageView) findViewById(R.id.weather);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (index >= w.length) {
                    index = 0;
                }
                mWeather.setWeatherType(w[index], true);
                index++;
            }
        });
    }
}
