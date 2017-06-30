package com.example.administrator.custemview;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import utils.CApp;


/**
 * Created by javen on 14-7-21.
 */
public class ToastMaster {

    private static Toast mToast;

    public static void showShortToast(final Context context, final Object message, final Object... args) {
        cancelToast(mToast);
        if (message == null)
            return;
        if (Looper.myLooper() != Looper.getMainLooper()) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    mToast = Toast.makeText(context, String.format(message.toString(), args), Toast.LENGTH_SHORT);
//                    mToast.setGravity(Gravity.CENTER, 0, 0);
                    mToast.show();
                }
            });
        } else {
            mToast = Toast.makeText(context, String.format(message.toString(), args), Toast.LENGTH_SHORT);
//            mToast.setGravity(Gravity.CENTER, 0, 0);
            mToast.show();
        }
    }

    public static void showLongToast(final Context context, final Object message, final Object... args) {
        cancelToast(mToast);
        if (message == null)
            return;
        if (Looper.myLooper() != Looper.getMainLooper()) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    mToast = Toast.makeText(context, String.format(message.toString(), args), Toast.LENGTH_LONG);
//                    mToast.setGravity(Gravity.CENTER, 0, 0);
                    mToast.show();
                }
            });
        } else {
            mToast = Toast.makeText(context, String.format(message.toString(), args), Toast.LENGTH_LONG);
//            mToast.setGravity(Gravity.CENTER, 0, 0);
            mToast.show();
        }
    }

    public static void cancelToast(final Toast toast) {


        if (toast != null) {
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    toast.cancel();
                }
            }, 100);

        }
    }

    public static void clean() {
        mToast = null;
    }

    public static void toast(String str) {
        showShortToast(CApp.context, str);
    }
}
