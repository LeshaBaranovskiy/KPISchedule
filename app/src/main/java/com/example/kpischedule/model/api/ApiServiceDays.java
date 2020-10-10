package com.example.kpischedule.model.api;

import com.example.kpischedule.pojo.DayResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiServiceDays {
    @GET("{group}/lessons")
    Observable<DayResponse> getDaysList(@Path("group") String group);
}
