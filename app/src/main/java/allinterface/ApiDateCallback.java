package allinterface;

/**
 * Created by zchao on 2016/5/24.
 */
public interface ApiDateCallback<T> {
    void onDateRecieved(T t, Throwable e, boolean isSuccess);
}
