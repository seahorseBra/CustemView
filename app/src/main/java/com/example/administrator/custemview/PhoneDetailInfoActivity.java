package com.example.administrator.custemview;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
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
        initeRv();

        fillInfo();
    }

    private void fillInfo() {
         List<PhoneInfo> info = new ArrayList();
        info.add(new PhoneInfo("主板（BOARD）", Build.BOARD));
        info.add(new PhoneInfo("系统定制商（BRAND）", Build.BRAND));
        info.add(new PhoneInfo("主板（BOARD）", Build.BOARD));
        info.add(new PhoneInfo("主板（BOARD）", Build.BOARD));
        info.add(new PhoneInfo("主板（BOARD）", Build.BOARD));
        info.add(new PhoneInfo("主板（BOARD）", Build.BOARD));
        info.add(new PhoneInfo("主板（BOARD）", Build.BOARD));
        info.add(new PhoneInfo("主板（BOARD）", Build.BOARD));
        info.add(new PhoneInfo("主板（BOARD）", Build.BOARD));
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
