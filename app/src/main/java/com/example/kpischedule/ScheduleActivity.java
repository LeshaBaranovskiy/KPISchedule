package com.example.kpischedule;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.kpischedule.adapters.ScheduleAdapter;
import com.example.kpischedule.api.ApiFactoryDays;
import com.example.kpischedule.api.ApiServiceDays;
import com.example.kpischedule.pojo.DayResponse;
import com.example.kpischedule.pojo.Lesson;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Objects;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static java.time.temporal.ChronoUnit.DAYS;

public class ScheduleActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProgressBar progressBarSchedule;

    private ScheduleAdapter scheduleAdapter;

    private String groupName = "";
    private int week = 1;

//    private LocalDate dateFWeek;
//    private LocalDate dateToday;

    private Disposable disposable;

    private ApiFactoryDays apiFactoryDays;
    private ApiServiceDays apiServiceDays;

    private ArrayList<Lesson> lessons = new ArrayList<>();

    private SnapHelper snapHelper;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.first_week:
                if (week == 1) {
                    break;
                } else {
                    week = 1;
                    showSchedule(week);
                    Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.first_week);
                    break;
                }
            case R.id.second_week:
                if (week == 2) {
                    break;
                } else {
                    week = 2;
                    showSchedule(week);
                    Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.second_week);
                    break;
                }
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        if (getIntent().getStringExtra("group") != null) {
            groupName = getIntent().getStringExtra("group").toLowerCase();
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
//        long lll = DAYS.between(dateFWeek, dateToday);
//        calendarToday = new GregorianCalendar();
//        calendar1Week.set(2020, 8, 31);
//        calendarToday.set(Calendar.DAY_OF_MONTH, 0);
//
//        Log.i("inforrr",  daysBetween(calendarToday.getTime(), calendar1Week.getTime()) + "");
//        Log.i("inforrr",  calendarToday.getTime().toString());
//        Log.i("inforrr",  calendar1Week.getTime().toString());

        Objects.requireNonNull(getSupportActionBar()).setTitle("Перший тиждень");
        recyclerView = findViewById(R.id.recyclerViewLessons);

        progressBarSchedule = findViewById(R.id.progressBarSchedule);

        apiFactoryDays = ApiFactoryDays.getInstance();
        apiServiceDays = apiFactoryDays.getApiServiceDays();

        scheduleAdapter = new ScheduleAdapter();

        snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(scheduleAdapter);

        showSchedule(week);
    }

    private int daysBetween(Date d1, Date d2) {
        return (int)( (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
    }

    private void showSchedule(final int week) {
            disposable = apiServiceDays.getDaysList(groupName)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<DayResponse>() {
                        @Override
                        public void accept(DayResponse dayResponse) throws Exception {
                            progressBarSchedule.setVisibility(View.VISIBLE);
                            lessons.clear();
                            lessons.addAll(dayResponse.getLessons());
                            scheduleAdapter.notifyDataSetChanged();
                            scheduleAdapter.setLessons(lessons, week);
                            progressBarSchedule.setVisibility(View.INVISIBLE);
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            Log.i("thro", Objects.requireNonNull(throwable.getMessage()));
                        }
                    });
    }

    @Override
    protected void onDestroy() {
        disposable.dispose();
        super.onDestroy();
    }
}
