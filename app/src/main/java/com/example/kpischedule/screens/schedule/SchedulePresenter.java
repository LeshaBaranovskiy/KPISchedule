package com.example.kpischedule.screens.schedule;

import com.example.kpischedule.model.api.ApiFactoryDays;
import com.example.kpischedule.model.api.ApiServiceDays;
import com.example.kpischedule.pojo.DayResponse;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class SchedulePresenter {

    private Disposable disposable;

    private ScheduleView scheduleView;

    public SchedulePresenter(ScheduleView scheduleView) {
        this.scheduleView = scheduleView;
    }

    //Подгрузка пар заданой группы
    public void loadData(final boolean fromStart, final String groupName) {
        ApiFactoryDays apiFactoryDays = ApiFactoryDays.getInstance();
        ApiServiceDays apiServiceDays = apiFactoryDays.getApiServiceDays();

        disposable = apiServiceDays.getDaysList(groupName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<DayResponse>() {
                    @Override
                    public void accept(DayResponse dayResponse) throws Exception {
                        scheduleView.onSuccess(dayResponse, fromStart);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }

    //Избегаем утечки данных
    public void disposeDisposable() {
        if (disposable != null) {
            disposable.dispose();
        }
    }
}
