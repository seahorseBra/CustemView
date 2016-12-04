package singleton;

import android.util.Log;

/**
 * 懒汉式
 * Created by mavin on 2016/8/16.
 */
public class SingletonTest1 {
    private static final SingletonTest1 instance = new SingletonTest1();
    private static final String TAG = "SingletonActivity";

    //私有化构造函数
    private SingletonTest1() {
        Log.d(TAG, "SingletonTest1() called with: " + "");
    }
    //暴露静态接口给外部
    public static SingletonTest1 getInstance() {
        return instance;
    }
}
