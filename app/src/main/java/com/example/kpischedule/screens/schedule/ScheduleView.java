package com.example.kpischedule.screens.schedule;

import com.example.kpischedule.pojo.DayResponse;

public interface ScheduleView {
    void onSuccess(DayResponse dayResponse, boolean fromStart);
}
