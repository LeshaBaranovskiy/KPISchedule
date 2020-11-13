package com.example.kpischedule.model.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.kpischedule.pojo.Lesson;

@Database(entities = Lesson.class, version = 1, exportSchema = false)
public abstract class LessonDatabase extends RoomDatabase {
    private static LessonDatabase lessonDatabase;
    private static final String DB_NAME = "lesson.db";
    private static final Object LOCK = new Object();

    public static LessonDatabase getInstance(Context context) {
        synchronized (LOCK) {
            if (lessonDatabase == null) {
                lessonDatabase = Room.databaseBuilder(context, LessonDatabase.class, DB_NAME).fallbackToDestructiveMigration().build();
            }
        }
        return lessonDatabase;
    }

    public abstract LessonDao lessonDao();
}
