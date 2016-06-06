package Utils;

import android.content.Context;

/**
 * Created by zchao on 2016/5/19.
 */
public class Utils {
    public static boolean isEmpty(String string){
        if (string == null) {
            return true;
        }
        if (string != null && string.length() == 0) {
            return true;
        }
        return false;
    }


    public static int dp2Px(float dp) {
        final float scale = CApp.context.getResources()
                .getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
}
