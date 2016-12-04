package singleton;

import android.util.Log;

import java.io.ObjectStreamException;

/**
 * Created by mavin on 2016/8/15.
 */
public class SingletonTest {
    private static final String TAG = "SingletonActivity";

    private SingletonTest() {
        Log.d(TAG, "SingletonTest() called with: " + "");
    }

    public static SingletonTest getInstance() {
        return SingletonHolder.instance;
    }

    static class SingletonHolder {
        private static final SingletonTest instance = new SingletonTest();
        private Object readResolve() throws ObjectStreamException {
            return instance;
        }

    }
}
