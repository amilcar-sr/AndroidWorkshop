package com.codesgood.androidworkshop.data.network;

import com.codesgood.androidworkshop.data.model.MovieListResponse;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkManager {

    private final static String API_KEY_PARAM = "api_key";
    private final static String BASE_API_URL = "https://api.themoviedb.org/3/";
    public final static String BASE_IMAGE_URL = "https://image.tmdb.org/t/p/w185";

    //TODO: [8] Create endpoints Interface for retrofit

    //TODO: [9] Hold instance for API
    private static MovieAPI movieAPI;

    public static void init() {
        //TODO: [10] Create a client that will insert the API_KEY param into every request
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @NotNull
            @Override
            public Response intercept(@NotNull Chain chain) throws IOException {
                Request request = chain.request();
                HttpUrl url = request.url().newBuilder().addQueryParameter(API_KEY_PARAM, "fc92c7ccbf0cda485182f22d68394913").build();
                request = request.newBuilder().url(url).build();
                return chain.proceed(request);
            }
        }).addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)).build(); //TODO: [11] Add logging interceptor

        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_API_URL).client(client).addConverterFactory(GsonConverterFactory.create()).build();

        movieAPI = retrofit.create(MovieAPI.class);
    }

    //TODO: [11] Add method to get the API call for popular movies
    //TODO: [12] Create layouts and views needed to present the movie information

    /**
     * Returns a list of movies according to the page number
     *
     * @param page number to be fetched
     */
    public static Call<MovieListResponse> getPopularMovies(int page) {
        return movieAPI.getPopularMovies(page);
    }
}
