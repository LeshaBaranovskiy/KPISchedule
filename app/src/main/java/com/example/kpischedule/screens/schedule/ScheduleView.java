package com.example.kpischedule.screens.schedule;

import android.content.Context;

import com.example.kpischedule.model.db.LessonDatabase;
import com.example.kpischedule.pojo.DayResponse;
import com.example.kpischedule.pojo.Lesson;

import java.util.List;

public interface ScheduleView {
    void onSuccess(DayResponse dayResponse);

    LessonDatabase getDatabaseContext();

    void lessonsFromDb(List<Lesson> lessons);

    void finishActivity();
}
