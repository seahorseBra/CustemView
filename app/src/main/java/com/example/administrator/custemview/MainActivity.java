package com.example.administrator.custemview;

import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import javaBean.Print;
import javaBean.Student;

public class MainActivity extends AppCompatActivity implements OnClickListener{

    private static final String TAG = "MainActivity";
    private TextView mText;
    private ImageView m;
    /* @Bind(R.id.tv)
    AlmanacItemView tv;
    @Bind(R.id.et)
    EditText et;*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        findViewById(R.id.main_activity_imghandle).setOnClickListener(this);
        findViewById(R.id.main_activity_dropdown).setOnClickListener(this);
        findViewById(R.id.main_activity_animation).setOnClickListener(this);
        findViewById(R.id.main_activity_scroller).setOnClickListener(this);
        findViewById(R.id.main_activity_horizontal_scroller).setOnClickListener(this);
        m = (ImageView) findViewById(R.id.svg_image);
        m.setOnClickListener(this);
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_activity_imghandle:
                goActivity(ImageHandleActivity.class);
                break;
            case R.id.main_activity_dropdown:
                goActivity(DropDownActivity.class);
                break;
            case R.id.main_activity_animation:
                goActivity(AnimationActivity.class);
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


        }
    }

    private void goActivity(Class cls){
        Intent intent = new Intent();
        intent.setClass(this, cls);
        startActivity(intent);
    }
}
