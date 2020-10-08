package com.example.kpischedule.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GroupsListResponse {

    @SerializedName("statusCode")
    @Expose
    private int statusCode;
    @SerializedName("timeStamp")
    @Expose
    private int timeStamp;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("debugInfo")
    @Expose
    private Object debugInfo;
    @SerializedName("meta")
    @Expose
    private Meta meta;
    @SerializedName("data")
    @Expose
    private List<Group> group = null;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public int getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(int timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getDebugInfo() {
        return debugInfo;
    }

    public void setDebugInfo(Object debugInfo) {
        this.debugInfo = debugInfo;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public List<Group> getGroups() {
        return group;
    }

    public void setGroup(List<Group> group) {
        this.group = group;
    }
}
