package model;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by zchao on 2016/5/19.
 */
public interface ApiService {
    @GET("/users/{username}/repos")
    List<GetIpInfoResponse> listRepos(@Path("username") String user);

}
