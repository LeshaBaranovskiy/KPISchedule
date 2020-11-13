package com.example.kpischedule.screens.schedule;

import android.util.Log;

import com.example.kpischedule.model.api.ApiFactoryDays;
import com.example.kpischedule.model.api.ApiServiceDays;
import com.example.kpischedule.model.db.LessonDatabase;
import com.example.kpischedule.pojo.Lesson;

import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SchedulePresenter {

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private ScheduleView scheduleView;

    private LessonDatabase database;

    public SchedulePresenter(ScheduleView scheduleView) {
        this.scheduleView = scheduleView;
        database = scheduleView.getDatabaseContext();
    }

    //Подгрузка пар заданой группы
    public void loadData(final String groupName) {
        ApiFactoryDays apiFactoryDays = ApiFactoryDays.getInstance();
        ApiServiceDays apiServiceDays = apiFactoryDays.getApiServiceDays();

        compositeDisposable.add(apiServiceDays.getDaysList(groupName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        dayResponse -> scheduleView.onSuccess(dayResponse),
                        throwable -> {}
                        ));
    }

    public void getAllLessons() {
        compositeDisposable.add(database.lessonDao().getAllLessons()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(lessons -> scheduleView.lessonsFromDb(lessons)));
    }

    public void insertLesson(Lesson lesson) {
        database.lessonDao().insertLesson(lesson)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    public void deleteAllLessons() {
        database.lessonDao().deleteAllLessons()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    public void deleteAllLessonsWithFinish() {
        database.lessonDao().deleteAllLessons()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        scheduleView.finishActivity();
                    }

                    @Override
                    public void onComplete() {
                        scheduleView.finishActivity();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    //Избегаем утечки данных
    public void disposeDisposable() {
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
    }
}
