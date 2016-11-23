package com.example.administrator.custemview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Time;
import android.util.Log;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ExecutorTestActivity extends AppCompatActivity {

    private static final String TAG = "ExecutorTestActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_executor_test);

       /* ScheduledExecutorService executorService = Executors.newScheduledThreadPool(5);
        Log.d(TAG, "onCreate() calle= [" + System.currentTimeMillis() + "]");

        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        cachedThreadPool.submit(new Runnable() {
            @Override
            public void run() {

            }
        });

        //周期执行，可设置周期时间
        executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "run() called="+System.currentTimeMillis());
            }
        }, 1, 3, TimeUnit.SECONDS);*/
    }
}
