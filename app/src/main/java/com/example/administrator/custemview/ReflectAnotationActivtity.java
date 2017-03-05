package com.example.administrator.custemview;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by zchao on 2016/12/28.
 */

public class ReflectAnotationActivtity extends BaseActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            Class c = Class.forName("utils.ReflectTest");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
