package utils;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;

import model.ApiDal;

/**
 * 全局变量
 * Created by zchao on 2016/5/4.
 */
public class CApp extends Application{
    public static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();

        appinite();
    }

    private void appinite() {
        utils.AppContext.inite(context);
        ApiDal.newInstance().initeApiDal(getApplicationContext());
        Fresco.initialize(context);
    }

}
