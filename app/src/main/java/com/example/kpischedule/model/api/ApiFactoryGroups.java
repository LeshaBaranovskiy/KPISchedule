package com.example.kpischedule.model.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiFactoryGroups {
    private static ApiFactoryGroups apiFactoryGroups;

    private final String BASE_URL = "https://api.rozklad.org.ua/v2/";

    private static Retrofit retrofit = null;

    private ApiFactoryGroups() {
        if (retrofit == null) {
            Gson gson = new GsonBuilder().setLenient().create();

            retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl(BASE_URL).build();
        }
    }

    public static ApiFactoryGroups getInstance() {
        if (apiFactoryGroups == null) {
            apiFactoryGroups = new ApiFactoryGroups();
        }
        return apiFactoryGroups;
    }

    public ApiServiceGroups getApiServiceGroups() {
        return retrofit.create(ApiServiceGroups.class);
    }
}
