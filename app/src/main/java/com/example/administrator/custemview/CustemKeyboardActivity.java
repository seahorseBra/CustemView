package com.example.administrator.custemview;

import android.os.Bundle;
import android.text.InputType;
import android.view.KeyEvent;
import android.widget.EditText;

import utils.KeyUtil;
import view.CusBoardView;

public class CustemKeyboardActivity extends BaseActivity {

    private CusBoardView mKeyboard;
    private EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custem_keyboard);
        mEditText = (EditText) findViewById(R.id.edit_text);
        mEditText.setInputType(InputType.TYPE_NULL);
        mKeyboard = (CusBoardView) findViewById(R.id.key_board);

        int inputback = mEditText.getInputType();
        new KeyUtil(this, getApplicationContext(), mEditText, mKeyboard);
        mEditText.setInputType(inputback);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        mKeyboard.onEventHandle(keyCode);
        return false;
    }
}
