package allinterface;

import javaBean.User;

/**
 * Created by zchao on 2016/5/19.
 */
public interface Callback {
    void onSucessful(User user);
    void onFail(String failMag);
}
