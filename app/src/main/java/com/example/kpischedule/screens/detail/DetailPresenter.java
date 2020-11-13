package com.example.kpischedule.screens.detail;

import android.util.Log;

import com.example.kpischedule.model.db.LessonDatabase;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class DetailPresenter {

    private DetailView detailView;

    private LessonDatabase database;

    private Disposable disposable;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public DetailPresenter(DetailView detailView) {
        this.detailView = detailView;
        database = detailView.getDatabaseContext();
    }

    public void getLessonByName(String name, String type) {
        disposable = database.lessonDao().getLessonByName(name, type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(lesson -> detailView.findLesson(lesson),
                        throwable -> Log.i("ttt", throwable.getMessage()));
        compositeDisposable.add(disposable);
    }

    public void updateWithNewZoom(int lessonId, String newZoom) {
        compositeDisposable.add(
                database.lessonDao().updateZoom(lessonId, newZoom)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe()
        );
    }

    public void disposeDisposable() {
        disposable.dispose();
    }
}
