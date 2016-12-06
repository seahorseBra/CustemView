package com.example.administrator.custemview;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.TextView;

import java.sql.Time;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.xml.transform.Transformer;

import bolts.Continuation;
import bolts.Task;
import rx.Observable;
import rx.Observer;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.observables.AsyncOnSubscribe;
import rx.schedulers.Schedulers;

public class RxJavaTestActivity extends BaseActivity {

    private static final String TAG = "RxJavaTestActivity";
    private TextView mText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java_test);
        mText = (TextView) findViewById(R.id.text);

        //基本使用
       /* Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("wage");
            }
        });

        Subscriber<String> action1 = new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {

            }
        };
        observable.subscribe(action1);*/



       /* Observable.just(1,2,3)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        mText.setText(String.valueOf(integer));
                    }
                });
*/
     /*   String[] words = {"111", "222", "333"};
        Observable.from(words)
                .delay(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.d(TAG, "call() called with: s = [" + s + "]");
                        mText.setText(s);
                    }
                });*/

        /*ArrayList<String> list = new ArrayList<>();
        list.add("a1");
        list.add("a2");
        list.add("a3");
        Observable.from(list)
                .delay(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.d(TAG, "call() called with: s = [" + s + "]");
                        mText.setText(s);
                    }
                });*/
        //map变换
/* Observable.create(new Observable.OnSubscribe<String>() {
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
*/
        //faltMap()
        //lift变换,尽量不用，易出问题。而使用已有包装方法（map（），flatMap（））；
        /*Observable.just(1, 2, 3).lift(new Observable.Operator<String, Integer>() {
            @Override
            public Subscriber<? super Integer> call(final Subscriber<? super String> subscriber) {
                return new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        subscriber.onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        subscriber.onError(e);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        subscriber.onNext("aaaaa" + integer);
                    }
                };
            }
        }).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.d(TAG, "call() called with: s = [" + s + "]");
            }
        });*/

        //使用Transformar进行公共变换
        LiftAllTransformar liftAllTransformar = new LiftAllTransformar();
        Observable.just(1,2)
                .compose(liftAllTransformar)
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.d(TAG, "call() called with: s = [" + s + "]");
                    }
                });
        
        Task.call(new Callable<String>() {
            @Override
            public String call() throws Exception {
                Log.d(TAG, "call() called");
                return "true";
            }
        }).onSuccess(new Continuation<String, Integer>() {
            @Override
            public Integer then(Task<String> task) throws Exception {
                Log.d(TAG, "then() called with: task = [" + task + "]");
                Thread.sleep(2000);
                return 100;
            }
        }, Executors.newSingleThreadExecutor()).continueWith(new Continuation<Integer, Void>() {
            @Override
            public Void then(Task<Integer> task) throws Exception {
                return null;
            }
        });
    }

    public class LiftAllTransformar implements Observable.Transformer<Integer, String> {
        @Override
        public Observable<String> call(Observable<Integer> integerObservable) {
            return integerObservable.map(new Func1<Integer, String>() {
                @Override
                public String call(Integer integer) {
                    return integer + "|";
                }
            }).map(new Func1<String, String>() {
                @Override
                public String call(String s) {
                    return s+ "二变";
                }
            });
        }
    }

}
