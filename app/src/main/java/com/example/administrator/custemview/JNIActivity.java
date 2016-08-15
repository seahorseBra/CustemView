package com.example.administrator.custemview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class JNIActivity extends AppCompatActivity {

    @Bind(R.id.result)
    TextView mTextResult;
    @Bind(R.id.java)
    Button java;
    @Bind(R.id.move)
    Button move;
    @Bind(R.id.jni)
    Button jni;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jni);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.java)
    public void jisuanWithJava(){
        long startTime = System.currentTimeMillis();
        int result = 1;
        for (int i = 0; i < 20; i++) {
            result *= 2;
        }
        long endTime = System.currentTimeMillis();

        mTextResult.setText(result + "用时：" + (endTime - startTime)/1000 + "秒" + (endTime - startTime)%1000 + "毫秒");
    }
    @OnClick(R.id.move)
    public void jisuanWithMove(){
        long startTime = System.currentTimeMillis();
        int result = 1;
        for (int i = 0; i < 20; i++) {
            result <<= 1;
        }
        long endTime = System.currentTimeMillis();

        mTextResult.setText(result + "用时：" + (endTime - startTime)/1000 + "秒" + (endTime - startTime)%1000 + "毫秒");
    }
    @OnClick(R.id.jni)
    public void jisuanWithJNI(){

    }
}
