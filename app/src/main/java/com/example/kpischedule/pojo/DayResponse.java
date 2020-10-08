package com.example.kpischedule.pojo;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DayResponse {

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
    private String debugInfo;
    @SerializedName("meta")
    @Expose
    private Object meta;
    @SerializedName("data")
    @Expose
    private List<Lesson> lessons = null;

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

    public String getDebugInfo() {
        return debugInfo;
    }

    public void setDebugInfo(String debugInfo) {
        this.debugInfo = debugInfo;
    }

    public Object getMeta() {
        return meta;
    }

    public void setMeta(Object meta) {
        this.meta = meta;
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
    }

}