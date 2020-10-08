package com.example.kpischedule.api;

import com.example.kpischedule.pojo.DayResponse;
import com.example.kpischedule.pojo.GroupsListResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiServiceDays {
    @GET("{group}/lessons")
    Observable<DayResponse> getDaysList(@Path("group") String group);
}
