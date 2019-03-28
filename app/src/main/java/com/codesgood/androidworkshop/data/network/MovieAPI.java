package com.codesgood.androidworkshop.data.network;

import com.codesgood.androidworkshop.data.model.MovieListResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieAPI {

    //TODO: [9] Create model classes for endpoint response and add method that will retrieve the popular movie list

    /**
     * Popular movies endpoint/call
     */
    @GET("movie/popular")
    public Call<MovieListResponse> getPopularMovies(@Query("page") int page);
}
