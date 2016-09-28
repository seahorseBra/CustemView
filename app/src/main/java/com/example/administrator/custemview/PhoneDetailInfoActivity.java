package com.example.administrator.custemview;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import adapter.PhoneInfoAdapter;

public class PhoneDetailInfoActivity extends BaseActivity {

    private RecyclerView mRv;
    private PhoneInfoAdapter mAdapter;
    private RelativeLayout mHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_detail_info);
        setTitle("关于手机");
        mSetting.setVisibility(View.GONE);

        mRv = (RecyclerView) findViewById(R.id.rv);

        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(0x002c48ff);
        }
        initeRv();
        fillInfo();
    }


    private void fillInfo() {
         List<PhoneInfo> info = new ArrayList();
        info.add(new PhoneInfo("主板（BOARD）", Build.BOARD));
//        info.add(new PhoneInfo("指令集（SUPPORTED_ABIS）", Build.SUPPORTED_ABIS.toString()));
        info.add(new PhoneInfo("设备参数（DEVICE）", Build.DEVICE));
        info.add(new PhoneInfo("屏幕参数（DISPLAY）", Build.DISPLAY));
        info.add(new PhoneInfo("唯一编号（FINGERPRINT）", Build.FINGERPRINT));
        info.add(new PhoneInfo("硬件序列号（SERIAL）", Build.SERIAL));
        info.add(new PhoneInfo("修订版本列表（ID）", Build.ID));
        info.add(new PhoneInfo("硬件制造商（MANUFACTURER）", Build.MANUFACTURER));
        info.add(new PhoneInfo("版本（MODEL）", Build.MODEL));
        info.add(new PhoneInfo("硬件名（HARDWARE）", Build.HARDWARE));
        info.add(new PhoneInfo("手机产品名（PRODUCT）", Build.PRODUCT));
        info.add(new PhoneInfo("Build描述标签（TAGS）", Build.TAGS));
        info.add(new PhoneInfo("Builder类型（TYPE）", Build.TYPE));
        info.add(new PhoneInfo("当前开发代号（VERSION.CODENAME）", Build.VERSION.CODENAME));
        info.add(new PhoneInfo("源码控制版本号（VERSION.INCREMENTAL）", Build.VERSION.INCREMENTAL));
        info.add(new PhoneInfo("版本字符串（VERSION.RELEASE）", Build.VERSION.RELEASE));
        info.add(new PhoneInfo("版本号（VERSION.SDK_INT）", String.valueOf(Build.VERSION.SDK_INT)));
        info.add(new PhoneInfo("Host值（HOST）", Build.HOST));
        info.add(new PhoneInfo("User名（USER）", Build.USER));
        info.add(new PhoneInfo("编译时间（TIME）",  String.valueOf(Build.TIME)));

        mAdapter.addInfo(info);
    }

    private void initeRv() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRv.setLayoutManager(manager);
        mAdapter = new PhoneInfoAdapter(this);
        mRv.setAdapter(mAdapter);
    }

    public class PhoneInfo {
        public PhoneInfo() {
        }

        public PhoneInfo(String name, String info) {
            this.name = name;
            this.info = info;
        }

        public String name;
        public String info;
    }


}
