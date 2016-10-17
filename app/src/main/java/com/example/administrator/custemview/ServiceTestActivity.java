package com.example.administrator.custemview;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.IBinder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

import service.TestService;

public class ServiceTestActivity extends BaseActivity{

    private static final String TAG = "ServiceTestActivity";
    private Intent intent;
    ServiceConnection sc;
    private TextView mResult;
    private Button mStartBtn;
    private Button mStopBtn;
    private boolean serviceIsBind = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_test);
        initView();
        sc = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                TestService.SimpleBinder binder = (TestService.SimpleBinder)service;
                ArrayList<File> allMusic = binder.getAllMusic();
                StringBuilder SB = new StringBuilder();

                if (allMusic != null) {
                    for (int i = 0; i < allMusic.size(); i++) {
                        SB.append(allMusic.get(i).getAbsolutePath());
                        SB.append("\r\n");
                    }
                    mResult.setText(SB.toString());
                } else {
                    mResult.setText("暂无数据");
                }
                Log.d(TAG, "onServiceConnected() called with: name = [" + name + "], service = [" + service + "]");
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                Log.d(TAG, "onServiceDisconnected() called with: name = [" + name + "]");
            }
        };
        intent = new Intent();
        intent.setClass(this, TestService.class);

    }

    private void initView() {
        mResult = (TextView) findViewById(R.id.result);
        mStartBtn = (Button) findViewById(R.id.start_btn);
        mStopBtn = (Button) findViewById(R.id.stop_btn);
        fastSetClickBehave(R.id.get_music_dir);
        fastSetClickBehave(R.id.start_btn);
        fastSetClickBehave(R.id.stop_btn);
        fastSetClickBehave(R.id.bind_btn);
        fastSetClickBehave(R.id.unbind_btn);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopIService();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.get_music_dir:
                selectMusicImportDir();
                break;
            case R.id.start_btn:
                startIService();
                break;
            case R.id.stop_btn:
                stopIService();
                break;
            case R.id.bind_btn:
                bindIService();
                break;
            case R.id.unbind_btn:
                unbindIService();
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 200 && resultCode == RESULT_OK) {
            Uri url = data.getData();

        }
    }

    /**
     * 文件选择
     */
    private void selectMusicImportDir() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        startActivityForResult(intent, 200);
    }


    /**
     * 取消绑定
     */
    private void unbindIService() {
        if (serviceIsBind) {
            unbindService(sc);
            serviceIsBind = false;
        }
    }

    /**
     * 绑定服务
     */
    private void bindIService() {
        Intent serviceIntent = new Intent();
        serviceIntent.setClass(ServiceTestActivity.this, TestService.class);
        bindService(serviceIntent, sc, Context.BIND_AUTO_CREATE);
        serviceIsBind = true;
    }


    /**
     * 停止服务
     */
    private void stopIService() {
        Intent serviceIntent = new Intent();
        serviceIntent.setClass(ServiceTestActivity.this, TestService.class);
        if (serviceIsBind) {
            unbindService(sc);
            serviceIsBind = false;
        }
        stopService(serviceIntent);
    }

    /**
     * 绑定服务
     */
    private void startIService() {
        Intent serviceIntent = new Intent();
        serviceIntent.setClass(ServiceTestActivity.this, TestService.class);
        startService(serviceIntent);
    }


}
