package model;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by zchao on 2016/5/19.
 */
public interface ApiService {
    @GET("users/{username}/repos")
    Call<GetIpInfoResponse> getIpInfo(@Query("username") String username);
}
