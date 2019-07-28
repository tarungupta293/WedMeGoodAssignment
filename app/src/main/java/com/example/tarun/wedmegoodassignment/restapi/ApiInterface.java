package com.example.tarun.wedmegoodassignment.restapi;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Tarun on 25/07/2019.
 */

public interface ApiInterface {
    @GET("movie")
    Call<ResponseBody> getMoviesResults(@Query("api_key") String api_key, @Query("language") String language, @Query("sort_by") String sort_by, @Query("include_adult") String include_adult, @Query("include_video") String include_video, @Query("page") int page);

    @GET("tv")
    Call<ResponseBody> getTVResults(@Query("api_key") String api_key, @Query("language") String language, @Query("sort_by") String sort_by, @Query("include_adult") String include_adult, @Query("include_video") String include_video, @Query("page") int page);

}
