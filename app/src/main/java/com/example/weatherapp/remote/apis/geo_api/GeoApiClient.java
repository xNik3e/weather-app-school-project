package com.example.weatherapp.remote.apis.geo_api;

import com.example.weatherapp.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GeoApiClient {
    public static String BASE_URL = "https://api.tomtom.com/search/2/search/";
    private static Retrofit retrofit;

    public static Retrofit getRetrofit() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        if (BuildConfig.DEBUG) {
            httpLoggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
        } else {
            httpLoggingInterceptor.level(HttpLoggingInterceptor.Level.NONE);
        }
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .addInterceptor(httpLoggingInterceptor).build();

        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
