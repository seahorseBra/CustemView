package com.example.administrator.custemview;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

public class SettingActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        setTitle("设置");
        mSetting.setVisibility(View.GONE);
        fastSetClickBehave(R.id.root_about);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.root_about:
                goActivity(PhoneDetailInfoActivity.class);
                break;
        }
    }
}
