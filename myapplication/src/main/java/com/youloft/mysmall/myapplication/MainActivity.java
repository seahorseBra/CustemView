package com.youloft.mysmall.myapplication;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private TextView mText;
    private Button mBtn;
    private Handler handler1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mText = (TextView) findViewById(R.id.text);
        mBtn = (Button) findViewById(R.id.btn);



        final MyThread myThreadA = new MyThread("A");
        final MyThread myThreadB = new MyThread("B");
        myThreadA.start();
        myThreadB.start();
        handler1 = new Handler(myThreadA.getLooper()){
           @Override
           public void handleMessage(Message msg) {
               Log.d(TAG, "handleMessage() = [" + msg.what + (Looper.myLooper() == Looper.getMainLooper()) + Looper.myLooper().getThread() + "]");
           }
       };

        final Handler handler2 = new Handler(myThreadB.getLooper()){
            @Override
            public void handleMessage(Message msg) {
                Log.d(TAG, "handleMessage() = [" + msg.what + (Looper.myLooper() == Looper.getMainLooper()) + Looper.myLooper().getThread() + "]");
            }
        };

        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler1.sendEmptyMessage(1);
                handler2.sendEmptyMessage(1);
            }
        });

//        Thread threadA = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                for (int i = 0; i < 3; i++) {
//                    try {
//                        Thread.sleep(200);
//                        Log.d(TAG, "Thread run threadA =" + getNextNum());
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        });
//        Thread threadB = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                for (int i = 0; i < 3; i++) {
//                    try {
//                        Thread.sleep(100);
//                        Log.d(TAG, "Thread run threadB =" + getNextNum());
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        });
//
//        threadA.start();
//        threadB.start();
    }

    public static ThreadLocal<Integer> num = new ThreadLocal<Integer>(){
        @Override
        protected Integer initialValue() {
            return 0;
        }
    };

    public int getNextNum(){
        num.set(num.get() + 1);
        return num.get();
    }



    private static final String TAG = "MainActivity";
    class MyThread extends HandlerThread{
        public MyThread(String s) {
            super(s);
        }

    }
}
