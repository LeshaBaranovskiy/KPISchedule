package com.example.kpischedule.model.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.kpischedule.pojo.Lesson;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface LessonDao {
    @Query("SELECT * FROM lessons")
    Single<List<Lesson>> getAllLessons();

    @Insert
    Completable insertLesson(Lesson lesson);

    @Query("DELETE FROM lessons")
    Completable deleteAllLessons();

    @Query("SELECT * FROM lessons WHERE lessonName = :lessonName AND lessonType = :lessonType")
    Flowable<Lesson> getLessonByName(String lessonName, String lessonType);

    @Query("UPDATE lessons SET zoom = :newZoom WHERE lessonId = :lessonId")
    Completable updateZoom(int lessonId, String newZoom);
}
