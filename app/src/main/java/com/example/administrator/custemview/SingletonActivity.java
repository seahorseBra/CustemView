package com.example.administrator.custemview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import singleton.SingletonTest;
import singleton.SingletonTest1;

/**
 * Created by mavin on 2016/8/15.
 */
public class SingletonActivity extends BaseActivity {

    private static final String TAG = "SingletonActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        for (int i = 0; i < 5; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(300);//模拟延迟加载
                        Log.d(TAG, "hashCode:" +  SingletonTest.getInstance().hashCode());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
}
