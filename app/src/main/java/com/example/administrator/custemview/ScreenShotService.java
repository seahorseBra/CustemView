package com.example.administrator.custemview;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

import utils.ScreenShotDetector;

public class ScreenShotService extends Service {

    private ScreenShotDetector detector;

    public ScreenShotService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        ScreenShotDimen dimen = new ScreenShotDimen();
        getApplicationContext().registerReceiver(dimen, new IntentFilter(Intent.ACTION_TIME_TICK));
        detector = new ScreenShotDetector(this);
        detector.start();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        detector.stop();
        super.onDestroy();
    }
}
