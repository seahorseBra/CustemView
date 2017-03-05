package utils;

import android.content.res.Resources;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

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

    public static int dp2Px(Resources resources,float dp) {
        final float scale = resources
                .getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public static FloatBuffer mBuffer;
    public static ShortBuffer mBufferSor;
    public static FloatBuffer floatToBuffer(float[] a){
        //先初始化buffer，数组的长度*4，因为一个float占4个字节
        ByteBuffer mbb = ByteBuffer.allocateDirect(a.length*4);
        // 数组排序用
        mbb.order(ByteOrder.nativeOrder());
        mBuffer = mbb.asFloatBuffer();
        mBuffer.put(a);
        mBuffer.position(0);
        return mBuffer;
    }

    public static ShortBuffer shortToBuffer(short[] a){

        ByteBuffer mbb = ByteBuffer.allocateDirect(a.length*2);
        // 数组排序用
        mbb.order(ByteOrder.nativeOrder());
        mBufferSor = mbb.asShortBuffer();
        mBufferSor.put(a);
        mBufferSor.position(0);
        return mBufferSor;
    }
}
