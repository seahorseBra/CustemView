package com.example.administrator.custemview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import view.CusBoardView;

public class CustemKeyboardActivity extends BaseActivity {

    private CusBoardView mKeyboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custem_keyboard);
        mKeyboard = (CusBoardView) findViewById(R.id.key_board);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mKeyboard.getVisibility() == View.VISIBLE) {
            mKeyboard.onEventHandle(keyCode);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
