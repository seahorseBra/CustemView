package presenter;

import allinterface.Callback;
import allinterface.UserModel;
import allinterface.UserPresenter;
import allinterface.UserView;
import javaBean.User;

/**
 * Created by zchao on 2016/5/19.
 */
public class LoginPresenter implements UserPresenter {
    private UserView userView;
    private UserModel userModel;

    public LoginPresenter(UserView userView, UserModel userModel) {
        this.userView = userView;
        this.userModel = userModel;
    }

    @Override
    public void Login(String userName, String password) {
        userModel.login(userName, password, new Callback() {

            @Override
            public void onSucessful(User user) {
                userView.showLoginSucessfulMsg(user);
            }

            @Override
            public void onFail(String failMag) {
                userView.showLoginFailMsg(failMag);
            }
        });
    }
}
