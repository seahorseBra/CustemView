package com.example.administrator.custemview;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Bundle;

import service.TestService;

public class ServiceTestActivity extends BaseActivity {

    private static final String TAG = "ServiceTestActivity";
    private Intent intent;
private ServiceConnection connection = new ServiceConnection() {
    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        int add = ((TestService.B) service).add(3, 4);
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
    }
};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_test);

        intent = new Intent();
        intent.setClass(this, TestService.class);
        bindService(intent , connection, BIND_AUTO_CREATE);

    }

    @Override
    protected void onDestroy() {
        unbindService(connection);
        super.onDestroy();
    }
}
