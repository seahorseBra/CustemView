package model;

/**
 * Created by zchao on 2016/5/24.
 */
public final class CacheObj<T> {
    private T obj;
    private long cacheTime;
    private String appVer;

    public CacheObj(T obj, long cacheTime, String appVer) {
        this.obj = obj;
        this.cacheTime = cacheTime;
        this.appVer = appVer;
    }

    public boolean isOutOfDate(long cacheTime){
        return Math.abs(System.currentTimeMillis() - cacheTime) >= cacheTime;
    }



    public T getObj() {
        return obj;
    }

    public long getCacheTime() {
        return cacheTime;
    }

    public String getAppVer() {
        return appVer;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }

    public void setCacheTime(long cacheTime) {
        this.cacheTime = cacheTime;
    }

    public void setAppVer(String appVer) {
        this.appVer = appVer;
    }
}
