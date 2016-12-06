package com.example.administrator.custemview;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

/**
 * Created by zchao on 2016/11/23.
 */

public class ScreenReciver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String path = intent.getStringExtra("PATH");
        if (!TextUtils.isEmpty(path)) {
            Intent intent1 = new Intent(context, ScreenShotActivity.class);
            intent1.putExtra("PATH", path);
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent1);
        }
    }
}
