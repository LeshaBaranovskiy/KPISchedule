package com.example.kpischedule.model.api;

import com.example.kpischedule.pojo.GroupsListResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiServiceGroups {
    @GET("groups/")
    Observable<GroupsListResponse> getGroupsList(@Query("filter") String offset);


}
