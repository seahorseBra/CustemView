package model;

import java.util.List;

import javaBean.GetIpInfoResponse;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by zchao on 2016/5/19.
 */
public interface ApiService {
    @GET("/users/{username}/repos")
    public void listRepos(@Path("username") String user, Callback<List<GetIpInfoResponse>> callback);

}
