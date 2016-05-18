package com.example.administrator.custemview;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by zchao on 2016/5/17.
 */
public class BaseActivity extends FragmentActivity implements View.OnClickListener{
    private FrameLayout mContent;
    protected ImageView mBack;
    protected ImageView mSetting;
    protected TextView mTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.base_activity);
        mContent = (FrameLayout) findViewById(R.id.content);
        mBack = (ImageView) findViewById(R.id.back);
        mSetting = (ImageView) findViewById(R.id.setting);
        mTitle = (TextView) findViewById(R.id.title);

        mBack.setOnClickListener(this);
        mSetting.setOnClickListener(this);
        mTitle.setOnClickListener(this);

    }

    @Override
    public void setContentView(int layoutResID) {
        getLayoutInflater().inflate(layoutResID, mContent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                onBack();
                break;
            case R.id.setting:
                onSetting();
                break;
            case R.id.title:
                break;
        }
    }

    protected void onSetting() {
    }

    protected void fastSetClickBehave(int... idRes){
        if (idRes == null) {
            return;
        }
        for (int i = 0; i < idRes.length; i++) {
            findViewById(idRes[i]).setOnClickListener(this);
        }
    }
    protected void onBack() {
        finish();
    }

    protected void setTitle(String title) {
        mTitle.setText(title);
    }

    protected void goActivity(@Nullable Class cls){
        Intent intent = new Intent();
        intent.setClass(this, cls);
        startActivity(intent);
    }

    /**
     * 带转场动画的启动
     * @param cls
     * @param bundle
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    protected void goActivity(@Nullable Class cls, Bundle bundle){
        Intent intent = new Intent();
        intent.setClass(this, cls);
        startActivity(intent, bundle);
    }
}
