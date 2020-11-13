package com.example.kpischedule.screens.detail;

import com.example.kpischedule.model.db.LessonDatabase;
import com.example.kpischedule.pojo.Lesson;

public interface DetailView  {
    LessonDatabase getDatabaseContext();

    void findLesson(Lesson lesson);
}
