package com.example.administrator.custemview;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import javaBean.Print;
import javaBean.Student;
import view.ClockView;

public class MainActivity extends BaseActivity{

    private static final String TAG = "MainActivity";
    private TextView mText;
    private ImageView m;
    private ClockView mClock;
    /* @Bind(R.id.tv)
    AlmanacItemView tv;
    @Bind(R.id.et)
    EditText et;*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        fastSetClickBehave(R.id.main_activity_imghandle,
                R.id.main_activity_dropdown,
                R.id.main_activity_animation,
                R.id.main_activity_scroller,
                R.id.main_activity_horizontal_scroller,
                R.id.main_activity_qq,
                R.id.main_activity_clock,
                R.id.main_activity_colormattrix,
                R.id.svg_image,
                R.id.clock,
                R.id.main_activity_login,
                R.id.main_activity_service,
                R.id.main_activity_weather,
                R.id.main_activity_mask,
                R.id.main_activity_sql,
                R.id.main_activity_opengl,
                R.id.main_activity_jni,
                R.id.main_activity_bluetooth,
                R.id.main_activity_vedio,
                R.id.main_activity_fragment,
                R.id.main_activity_rx_java,
                R.id.main_activity_okhttp,
                R.id.main_activity_file_system
        );
        mClock = (ClockView) findViewById(R.id.clock);
        m = (ImageView) findViewById(R.id.svg_image);
        m.setOnClickListener(this);

        SimpleDateFormat format = new SimpleDateFormat("MMM.EEEE");
        final String format1 = format.format(new java.util.Date());
        Log.d(TAG, "onCreate() called with: " + "format1 = [" + format1.toUpperCase() + "]");

       /* ApiDal.newInstance().getGitHub("seahorseBra", new ApiDateCallback() {
            @Override
            public void onDateRecieved(Object o, Throwable e, boolean isSuccess) {
                if (isSuccess) {
                    List<GetIpInfoResponse> obj = (List<GetIpInfoResponse>) o;
                    for (int i = 0; i < obj.size(); i++) {
                        Log.d(TAG, "onDateRecieved() called with: " + "o = [" + obj.get(i).getFull_name() + "], e = [" + e + "], isSuccess = [" + isSuccess + "]");

                    }
                }
            }
        });*/
//        tv.setDate(R.mipmap.ic_launcher, "啊喂噶围观");
//        witch(et);

//        testGson();
//        testGson1();
//        testGson2();
    }

    private String witch(View view) {
        ((EditText) view).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.d(TAG, "beforeTextChanged() called with: " + "s = [" + s + "], start = [" + start + "], count = [" + count + "], after = [" + after + "]");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d(TAG, "onTextChanged() called with: " + "s = [" + s + "], start = [" + start + "], before = [" + before + "], count = [" + count + "]");
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d(TAG, "afterTextChanged() called with: " + "s = [" + s + "]");
            }
        });
        return null;
    }


    private void testGson() {
        String s = "{\"20160406\":{\"adArea\":{\"adId\":\"570322bf35da0842b0795c72\",\"img\":\"http://7u2s0k.com2.z0.glb.qiniucdn.com/20160405102800_smad.png\",\"landUrl\":\"http://m.yiqibazi.com/topic/case268.aspx?fr\\u003dyoulu\",\"level\":0,\"expire\":\"20160420000000\",\"fire\":\"20160401000000\"},\"adAreaTime\":\"20160406\",\"clickTime\":\"20160406\"}}";

        Gson gson = new Gson();

        Student stu1 = new Student();
        stu1.setUserID(1);
        stu1.setUserName("阿花");
        stu1.setUserNickName("qq");
        stu1.setBirthDay(new Date());

        Student stu2 = new Student();
        stu2.setUserID(2);
        stu2.setUserName("笑话");
        stu2.setUserNickName("ww");

        Student stu3 = new Student();
        stu3.setUserID(3);
        stu3.setUserName("各位");
        stu3.setUserNickName("ee");

        List<Student> list = new ArrayList<>();
        list.add(stu1);
        list.add(stu2);
        list.add(stu3);

        String simpleBean = gson.toJson(stu1);
        Student stu4 = gson.fromJson(simpleBean, Student.class);

        String listToGson = gson.toJson(list);
        List<Student> list1 = gson.fromJson(listToGson, new TypeToken<ArrayList<Student>>() {
        }.getType());

        System.out.println(simpleBean);
        System.out.println(stu4);
        System.out.println(listToGson);
        for (int i = 0; i < list1.size(); i++) {
            System.out.println(list1.get(i));
        }
    }

    private void testGson1() {
        Gson gson = new GsonBuilder()
                .generateNonExecutableJson()
                .enableComplexMapKeySerialization()
                .serializeNulls()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .setVersion(1)
                .create();

        Student stu1 = new Student();
        stu1.setUserID(1);
        stu1.setUserName("阿花");
        stu1.setUserNickName("qq");
        stu1.setBirthDay(new Date());

        Student stu2 = new Student();
        stu2.setUserID(2);
        stu2.setUserName("笑话");
        stu2.setUserNickName("ww");

        Student stu3 = new Student();
        stu3.setUserID(3);
        stu3.setUserName("各位");
        stu3.setUserNickName("ee");

        List<Student> list = new ArrayList<>();
        list.add(stu1);
        list.add(stu2);
        list.add(stu3);

        String simpleBean = gson.toJson(stu1);
        Student stu4 = gson.fromJson(simpleBean, Student.class);

        String listToGson = gson.toJson(list);
        List<Student> list1 = gson.fromJson(listToGson, new TypeToken<List<Student>>() {
        }.getType());

        System.out.println(simpleBean);
        System.out.println(stu4);
        System.out.println(listToGson);
        for (int i = 0; i < list1.size(); i++) {
            System.out.println(list1.get(i));
        }
    }

    private void testGson2() {
        Gson gson = new GsonBuilder()
                .enableComplexMapKeySerialization()
                .create();

        Map<Print, String> map = new LinkedHashMap<>();
        map.put(new Print(1, 2), "a");
        map.put(new Print(2, 3), "b");

        String map1 = gson.toJson(map);
        System.out.println(map1);

        Map<Print, String> map2 = gson.fromJson(map1, new TypeToken<Map<Print, String>>() {
        }.getType());
        for (Print n : map2.keySet()) {
            System.out.println(n.toString() + n);
        }
    }

    private void testGson3(){
        String s = "[{\"20160406\":{\"adArea\":{\"adId\":\"570322bf35da0842b0795c72\",\"img\":\"http://7u2s0k.com2.z0.glb.qiniucdn.com/20160405102800_smad.png\",\"landUrl\":\"http://m.yiqibazi.com/topic/case268.aspx?fr\\u003dyoulu\",\"level\":0,\"expire\":\"20160420000000\",\"fire\":\"20160401000000\"},\"adAreaTime\":\"20160406\",\"clickTime\":\"20160406\"}}]";

    }

    @Override
    protected void onSetting() {
        goActivity(SettingActivity.class);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.main_activity_imghandle:
                goActivity(ImageHandleActivity.class);
                break;
            case R.id.main_activity_dropdown:
                goActivity(DropDownActivity.class);
                break;
            case R.id.main_activity_animation:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    goActivity(AnimationActivity.class, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                } else {
                    goActivity(AnimationActivity.class);
                }
                break;
            case R.id.svg_image:
                ((Animatable)m.getDrawable()).start();
                break;
            case R.id.main_activity_scroller:
                goActivity(CustomScroller.class);
                break;
            case R.id.main_activity_horizontal_scroller:
                goActivity(HorizontalScrollerActivity.class);
                break;
            case R.id.main_activity_qq:
                goActivity(DragHelperActivity.class);
                break;
            case R.id.main_activity_clock:
                goActivity(ClockActivity.class);
                break;
            case R.id.main_activity_colormattrix:
                goActivity(ColorMatrixActivity.class);
                break;
            case R.id.clock:
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        goActivity(ClockActivity.class, ActivityOptions.makeSceneTransitionAnimation(this, mClock, "clock").toBundle());
                    }
                break;
            case R.id.main_activity_login:
                goActivity(LogingActivity.class);
                break;

            case R.id.main_activity_service:
                goActivity(ServiceTestActivity.class);
//                Intent intent = new Intent();
//                intent.setClass(MainActivity.this, MyDaydreamService.class);
//                startService(intent);
                break;
            case R.id.main_activity_weather:
                goActivity(WeatherActivityNew.class);
                break;
            case R.id.main_activity_mask:
                goActivity(MaskActivity.class);
                break;
            case R.id.main_activity_sql:
                goActivity(SQLTestActivity.class);
                break;
            case R.id.main_activity_opengl:
                goActivity(OpenGLActivity.class);
                break;
            case R.id.main_activity_jni:
                goActivity(JNIActivity.class);
                break;
            case R.id.main_activity_bluetooth:
                goActivity(BluetoothActivity.class);
                break;
            case R.id.main_activity_vedio:
                goActivity(ScreenrecordActivity.class);
                break;
            case R.id.main_activity_fragment:
                goActivity(FragmentActivity.class);
                break;
            case R.id.main_activity_rx_java:
                goActivity(RxJavaTestActivity.class);
                break;
            case R.id.main_activity_okhttp:
                goActivity(OkHttpTestActivity.class);
                break;
            case R.id.main_activity_file_system:
                goActivity(FileSystem.class);
                break;
        }
    }


}
