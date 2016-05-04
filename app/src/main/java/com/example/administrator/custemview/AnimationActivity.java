package com.example.administrator.custemview;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.CharacterPickerDialog;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

public class AnimationActivity extends AppCompatActivity {

    private static final String TAG = "AnimationActivity";
    private ImageView mImage;
    private ImageView mCenterIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);
        mImage = (ImageView)findViewById(R.id.activity_animation_iv);
        mCenterIV = (ImageView) findViewById(R.id.center_line);
        mImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAnimation(mImage);

                Log.d(TAG, "onCreate() called with: " + "mimage = [ " + mCenterIV.getTop() + "]");
            }
        });
    }


    private void addAnimation(View view) {
        TranslateAnimation trAnima = new TranslateAnimation(0, 0, 0, 414);
        trAnima.setDuration(3000);
        trAnima.setRepeatCount(200);
        trAnima.setRepeatMode(Animation.REVERSE);
        view.setAnimation(trAnima);
        trAnima.start();
    }
}
