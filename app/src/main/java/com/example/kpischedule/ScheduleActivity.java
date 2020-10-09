package com.example.kpischedule;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.AttributeSet;
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
    private int remainder;
    private int daysBetween;

    private Calendar calFWeek;
    private Calendar calToday;

    private Disposable disposable;

    private ApiFactoryDays apiFactoryDays;
    private ApiServiceDays apiServiceDays;

    private ArrayList<Lesson> lessons = new ArrayList<>();

    private SnapHelper snapHelper;

    private LinearLayoutManager linearLayoutManager;

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
                    showSchedule(week, false);
                    break;
                }
            case R.id.second_week:
                if (week == 2) {
                    break;
                } else {
                    week = 2;
                    showSchedule(week, false);
                    break;
                }
            case R.id.next_el:
                recyclerView.getLayoutManager().smoothScrollToPosition(recyclerView, null,linearLayoutManager.findLastVisibleItemPosition() + 1);
                break;
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

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        recyclerView = findViewById(R.id.recyclerViewLessons);
        progressBarSchedule = findViewById(R.id.progressBarSchedule);

        calFWeek = new GregorianCalendar(2020, Calendar.AUGUST, 31);
        calToday = GregorianCalendar.getInstance();
        calToday.set(Calendar.HOUR_OF_DAY, 0);
        calToday.set(Calendar.MINUTE, 0);
        calToday.set(Calendar.SECOND, 0);
        calToday.set(Calendar.MILLISECOND, 0);

        apiFactoryDays = ApiFactoryDays.getInstance();
        apiServiceDays = apiFactoryDays.getApiServiceDays();

        findReminder();

        if (getIntent().getStringExtra("group") != null) {
            groupName = getIntent().getStringExtra("group").toLowerCase();
        }

        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        scheduleAdapter = new ScheduleAdapter();

        snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(scheduleAdapter);

        showSchedule(week, true);
    }

    private long daysBetween(Date d1, Date d2) {
        return ( (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
    }

    private void showSchedule(final int week, final boolean fromStart) {
            disposable = apiServiceDays.getDaysList(groupName)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<DayResponse>() {
                        @Override
                        public void accept(DayResponse dayResponse) throws Exception {
                            progressBarSchedule.setVisibility(View.VISIBLE);
                            lessons.clear();
                            lessons.addAll(dayResponse.getLessons());
                            scheduleAdapter.setLessons(lessons, week);
                            scheduleAdapter.notifyDataSetChanged();
                            progressBarSchedule.setVisibility(View.INVISIBLE);
                            if (fromStart) {
                                recyclerView.getLayoutManager().scrollToPosition(remainder);
                            }
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            Log.i("thro", Objects.requireNonNull(throwable.getMessage()));
                        }
                    });
            if (week == 1) {
                Objects.requireNonNull(getSupportActionBar()).setTitle(getResources().getString(R.string.first_week) + " " + groupName.split(" ")[0].toUpperCase());
            } else {
                Objects.requireNonNull(getSupportActionBar()).setTitle(getResources().getString(R.string.second_week) + " " + groupName.split(" ")[0].toUpperCase());
            }
            if(fromStart) {
                recyclerView.getLayoutManager().scrollToPosition(remainder);
            }
    }

    private void findReminder() {
        daysBetween = (int) daysBetween(calFWeek.getTime(), calToday.getTime());
        remainder = daysBetween % 14;

        if (remainder < 6) {
            week = 1;
        }
        else if (remainder > 6 && remainder < 13) {
            week = 2;
        } else {
            week = 1;
            if (remainder == 6) {
                remainder = 7;
                week = 2;
            }
        }
        if (remainder >= 7) {
            remainder -= 7;
        }
    }

    @Override
    protected void onDestroy() {
        disposable.dispose();
        super.onDestroy();
    }
}
