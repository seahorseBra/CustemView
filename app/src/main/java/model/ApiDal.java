package model;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import allinterface.ApiDateCallback;
import javaBean.GetIpInfoResponse;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;
import retrofit.http.GET;

/**
 * Created by zchao on 2016/5/23.
 */
public class ApiDal {

    private static final String TAG = "ApiDal";
    private DiskCache mDiskCache;
    private Context mContext;
    private static ApiDal instance = null;
    private Executor mPreloadExecutor;
    private Handler mMainHandler;
    public static ApiDal newInstance(){
        if (instance == null) {
            synchronized (ApiDal.class) {
                if (instance == null) {
                    instance = new ApiDal();
                }
            }
        }
        return instance;
    }
    private ApiService mApiService;

    public ApiDal() {
        initeService();
    }

    public ApiDal initeApiDal(Context appContext) {
        this.mContext = appContext;
        mDiskCache = DiskCache.getInstance(appContext);
        mPreloadExecutor = Executors.newFixedThreadPool(5);
        mMainHandler = new Handler(Looper.getMainLooper());

        return this;
    }

    private void initeService() {
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("https://api.github.com")
                .setConverter(new GsonConverter(new Gson()))
                .build();

        mApiService = restAdapter.create(ApiService.class);
    }

    private  <T>void postResultToListener(final ApiDateCallback callback, final T data, final Exception e, final boolean success) {
        mMainHandler.post(new Runnable() {
            @Override
            public void run() {
                callback.onDateRecieved(data, e, success);
            }
        });
    }

    /**
     * 获取github用户的项目信息
     * @param userName
     */
    public void getGitHub(final String userName, final ApiDateCallback callback){
        final String cacheKey = "GITHUB" + userName;
        mPreloadExecutor.execute(new Runnable() {
            @Override
            public void run() {
                List<GetIpInfoResponse> model;
                Type type = new TypeToken<CacheObj<List<GetIpInfoResponse>>>(){}.getType();
                CacheObj<List<GetIpInfoResponse>> cacheObj = mDiskCache.getCacheObj(cacheKey, type);
                if (cacheObj != null && !cacheObj.isOutOfDate(CacheTime.CACHE_TIME)) {
                    model = cacheObj.getObj();
                    if (model != null) {
                        postResultToListener(callback, model, null, true);
                        return;
                    }
                }

                mApiService.listRepos(userName, new Callback<List<GetIpInfoResponse>>() {
                    @Override
                    public void success(List<GetIpInfoResponse> getIpInfoResponses, Response response) {
                        if (getIpInfoResponses != null) {
                            mDiskCache.storyCacheObj(cacheKey, getIpInfoResponses);
                            postResultToListener(callback, getIpInfoResponses, null, true);
                            return;
                        }
                    }
                    @Override
                    public void failure(RetrofitError error) {
                        postResultToListener(callback, null, new RuntimeException(error), false);
                        return;
                    }
                });
            }
        });


    }
}
