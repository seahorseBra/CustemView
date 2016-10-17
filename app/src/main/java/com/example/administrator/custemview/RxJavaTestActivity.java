package com.example.administrator.custemview;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.TextView;

import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import bolts.Task;
import rx.Observable;
import rx.Observer;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class RxJavaTestActivity extends BaseActivity {

    private TextView mText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java_test);
        mText = (TextView) findViewById(R.id.text);

        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    Thread.sleep(2000);
                    subscriber.onNext("扯犊子");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).map(new Func1<String, String>() {
            @Override
            public String call(String s) {
                return s + "gawegaew";
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        mText.setText(s);
                    }
                });
    }
}
