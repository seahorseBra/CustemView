package Utils;

import android.app.Application;
import android.content.Context;

/**
 * 全局变量
 * Created by zchao on 2016/5/4.
 */
public class CApp extends Application{
    public Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();

        AppContext.inite(context);
    }

}
