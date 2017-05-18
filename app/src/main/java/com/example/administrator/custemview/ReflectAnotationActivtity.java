package com.example.administrator.custemview;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.TranslateAnimation;
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
//        final Animation animation = AnimationUtils.loadAnimation(this, R.anim.slide_in_bottom);

        final Animation animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF,0,Animation.RELATIVE_TO_SELF,0,Animation.RELATIVE_TO_SELF,1,Animation.RELATIVE_TO_SELF,0);
        animation.setDuration(340);
        animation.setInterpolator(new ACC());
        animation.setFillAfter(true);
//        animation.setInterpolator(new AccelerateDecelerateInterpolator());
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text.startAnimation(animation);
            }
        });

//        try {
//            Class c = Class.forName("utils.ReflectTest");
//            Method testMethed = c.getDeclaredMethod("testMethed", String.class);
//            testMethed.setAccessible(true);
//            String awleg = (String) testMethed.invoke(c.newInstance(), "awleg");
//            if (!TextUtils.isEmpty(awleg)) {
//                text.setText(awleg);
//            }
//
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        }
    }

    public class ACC extends AccelerateDecelerateInterpolator{
        @Override
        public float getInterpolation(float input) {
            if (input < 0.5) {
                return (float) (4 * Math.pow(input, 3));
            } else {
                return (float) ((input - 1) * Math.pow(2 * input - 2, 2) + 1);
            }
        }
    }
}
