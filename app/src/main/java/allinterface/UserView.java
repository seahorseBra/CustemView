package allinterface;

import javaBean.User;

/**
 * Created by zchao on 2016/5/19.
 */
public interface UserView {
    void showLoginSucessfulMsg(User loginUser);
    void showLoginFailMsg(String msg);
}
