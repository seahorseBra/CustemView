package com.example.administrator.custemview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import Utils.Utils;
import allinterface.UserView;
import javaBean.User;
import model.UserModelImpl;
import presenter.LoginPresenter;

public class LogingActivity extends BaseActivity implements UserView{

    private LoginPresenter mPresenter;
    private EditText mUserName;
    private EditText mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loging);
        mPresenter = new LoginPresenter(this, new UserModelImpl());
        fastSetClickBehave(R.id.login);

        mUserName = (EditText) findViewById(R.id.user_name);
        mPassword = (EditText) findViewById(R.id.user_password);
    }


    @Override
    public void showLoginSucessfulMsg(User loginUser) {
        Toast.makeText(this, "亲爱的"+loginUser.user+",恭喜注册成功" ,Toast.LENGTH_LONG).show();
    }

    @Override
    public void showLoginFailMsg(String msg) {
        Toast.makeText(this, msg ,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.login:
                if (Utils.isEmpty(mUserName.getText().toString()) ||Utils.isEmpty(mPassword.getText().toString())) {
                    Toast.makeText(this, "账户或密码不能为空", Toast.LENGTH_LONG).show();
                    return;
                }
                mPresenter.Login(mUserName.getText().toString(), mPassword.getText().toString());
                break;
        }
    }
}
