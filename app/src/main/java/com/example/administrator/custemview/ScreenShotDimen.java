package com.example.administrator.custemview;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.List;

/**
 * Created by zchao on 2016/11/23.
 */

public class ScreenShotDimen extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_TIME_TICK)) {
            ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningServiceInfo> runningServices = manager.getRunningServices(Integer.MAX_VALUE);
            boolean isRun = false;
            for (ActivityManager.RunningServiceInfo r:runningServices) {
                if (r.service.getClassName().equals("com.example.administrator.custemview.ScreenShotService")) {
                    isRun = true;
                    break;
                }
            }
            if (!isRun) {
                Intent i = new Intent(context, ScreenShotService.class);
                context.startService(i);
            }
        }
    }
}
