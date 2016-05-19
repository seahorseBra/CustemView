package model;

import Utils.Utils;
import allinterface.Callback;
import allinterface.UserModel;
import javaBean.User;

/**
 * Created by zchao on 2016/5/19.
 */
public class UserModelImpl implements UserModel {
    @Override
    public void login(String userName, String password, Callback callback) {
        if (password.length() < 6) {
            callback.onFail("密码长度过短");
            return;
        }
        if (Utils.isEmpty(userName)) {
            callback.onFail("用户名不能为空");
        } else {
            callback.onSucessful(new User(userName, password));
        }
    }
}
