package com.example.kpischedule.api;

import com.example.kpischedule.pojo.GroupsListResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.observables.BlockingObservable;

public interface ApiServiceGroups {
    @GET("groups/")
    Observable<GroupsListResponse> getGroupsList(@Query("filter") String offset);


}
