package com.dexter.newsapp.data.restApi;

import com.dexter.newsapp.data.model.network.NewsResponse;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {


    @GET("top-headlines")
    Single<NewsResponse> fetchNews(
            @Query("country") String country,
            @Query("category") String category,
            @Query("apiKey") String apiKey,
            @Query("page") long page
    );

}
