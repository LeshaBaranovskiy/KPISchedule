package com.example.kpischedule.model.db;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.kpischedule.pojo.Lesson;
import com.example.kpischedule.screens.schedule.ScheduleView;

import org.reactivestreams.Subscription;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainViewModel extends AndroidViewModel {
    private static LessonDatabase lessonDatabase;

    public MainViewModel(@NonNull Application application) {
        super(application);
        lessonDatabase = LessonDatabase.getInstance(getApplication());
    }

    public void getAllLessons() {
        lessonDatabase.lessonDao().getAllLessons()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Consumer<List<Lesson>>() {
                @Override
                public void accept(List<Lesson> lessons) throws Exception {

                }
            });
    }

    public void deleteAllLessons() {
        lessonDatabase.lessonDao().deleteAllLessons();
    }

    public void insertLesson(Lesson lesson) {
        lessonDatabase.lessonDao().insertLesson(lesson);
    }


}
