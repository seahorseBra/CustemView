package utils;

import android.content.Context;

/**
 * Created by zchao on 2016/5/4.
 */
public class AppContext {
    public static Context context;

    public static void inite(Context context) {
        if (context == null) {
            return;
        }
        AppContext.context = context;
    }


}
