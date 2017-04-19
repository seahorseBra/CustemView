package com.example.administrator.custemview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.RemoteViews;
import android.widget.TextView;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by zchao on 2016/12/28.
 */

public class ReflectAnotationActivtity extends BaseActivity{

    private TextView text;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reflect);
        text = (TextView) findViewById(R.id.text);
        try {
            Class c = Class.forName("utils.ReflectTest");
            Method testMethed = c.getDeclaredMethod("testMethed", String.class);
            testMethed.setAccessible(true);
            String awleg = (String) testMethed.invoke(c.newInstance(), "awleg");
            if (!TextUtils.isEmpty(awleg)) {
                text.setText(awleg);
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
