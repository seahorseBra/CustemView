package Utils;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.WindowManager;

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
