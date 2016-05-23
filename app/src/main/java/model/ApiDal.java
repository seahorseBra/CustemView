package model;

import android.util.Log;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.DateTypeAdapter;

import java.util.Date;
import java.util.List;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Created by zchao on 2016/5/23.
 */
public class ApiDal {

    private static final String TAG = "ApiDal";
    private static ApiDal instance = null;
    public static ApiDal newInstance(){
        if (instance == null) {
            synchronized (ApiDal.class) {
                if (instance == null) {
                    instance = new ApiDal();
                }
            }
        }
        return instance;
    };
    private ApiService mApiService;

    public ApiDal() {

        initeService();
    }

    private void initeService() {
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .registerTypeAdapter(Date.class, new DateTypeAdapter())
                .create();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("https://api.github.com")
                .setConverter(new GsonConverter(gson))
                .build();

        mApiService = restAdapter.create(ApiService.class);
    }


    public void getGitHub(final String userName){

        new Thread(new Runnable() {
            @Override
            public void run() {
                List<GetIpInfoResponse> getIpInfoResponses = mApiService.listRepos(userName);
                if (getIpInfoResponses != null) {
                    Log.d(TAG, "run() called with: " + "");
                } else {
                    Log.d(TAG, "run() called with: " + "null");
                }
            }
        });

    }
}
