package com.example.kpischedule.model.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiFactoryDays {
    private static ApiFactoryDays apiFactoryDays;

    private final String BASE_URL = "https://api.rozklad.org.ua/v2/groups/";

    private static Retrofit retrofit = null;

    private ApiFactoryDays() {
        if (retrofit == null) {
            Gson gson = new GsonBuilder().setLenient().create();

            retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl(BASE_URL).build();
        }
    }

    public static ApiFactoryDays getInstance() {
        if (apiFactoryDays == null) {
            apiFactoryDays = new ApiFactoryDays();
        }
        return apiFactoryDays;
    }

    public ApiServiceDays getApiServiceDays() {
        return retrofit.create(ApiServiceDays.class);
    }
}
