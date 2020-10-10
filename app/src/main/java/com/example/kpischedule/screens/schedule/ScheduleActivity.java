package com.example.kpischedule.screens.schedule;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.kpischedule.R;
import com.example.kpischedule.adapters.ScheduleAdapter;
import com.example.kpischedule.pojo.DayResponse;
import com.example.kpischedule.pojo.Lesson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Objects;


public class ScheduleActivity extends AppCompatActivity implements ScheduleView {

    private RecyclerView recyclerView;
    private ProgressBar progressBarSchedule;

    private ScheduleAdapter scheduleAdapter;

    private String groupName = "";

    private int week = 1;
    private int remainder;
    private int daysBetween;

    private Calendar calFWeek;
    private Calendar calToday;

    private ArrayList<Lesson> lessons = new ArrayList<>();

    private SnapHelper snapHelper;

    private LinearLayoutManager linearLayoutManager;

    private SchedulePresenter schedulePresenter;

    //Создаем меню в ActionBar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }


    //Обрабатываем нажатия на пункт меню
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
                Objects.requireNonNull(recyclerView.getLayoutManager()).smoothScrollToPosition(recyclerView, null, linearLayoutManager.findLastVisibleItemPosition() + 1);
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

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Создаем стрелку "Назад"
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        schedulePresenter = new SchedulePresenter(this);

        recyclerView = findViewById(R.id.recyclerViewLessons);
        progressBarSchedule = findViewById(R.id.progressBarSchedule);

        //Даты для определения дня недели и сегоднешней даты
        //В calFWeek заносим стартовую дату, а в calToday - сегодняшнюю
        calFWeek = new GregorianCalendar(2020, Calendar.AUGUST, 31);
        calToday = GregorianCalendar.getInstance();
        calToday.set(Calendar.HOUR_OF_DAY, 0);
        calToday.set(Calendar.MINUTE, 0);
        calToday.set(Calendar.SECOND, 0);
        calToday.set(Calendar.MILLISECOND, 0);

        //Находим какой сегодня день недели под RecyclerView
        //также здесь определяется какая сейчас неделя
        findReminder();

        //Получаем название группы, для которой ищутся пары
        if (getIntent().getStringExtra("group") != null) {
            groupName = Objects.requireNonNull(getIntent().getStringExtra("group")).toLowerCase();
        }

        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        scheduleAdapter = new ScheduleAdapter();

        //При прокрутке останавливает ровно на элементе
        snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(scheduleAdapter);

        //Загружаем дату
        showSchedule(week, true);
    }

    private void showSchedule(final int week, final boolean fromStart) {
        schedulePresenter.loadData(fromStart, groupName);

        if (week == 1) {
            Objects.requireNonNull(getSupportActionBar()).setTitle(getResources().getString(R.string.first_week) + " " + groupName.split(" ")[0].toUpperCase());
        } else {
            Objects.requireNonNull(getSupportActionBar()).setTitle(getResources().getString(R.string.second_week) + " " + groupName.split(" ")[0].toUpperCase());
        }
    }

    //При успешной загрузке данных из Presenter
    public void onSuccess(DayResponse dayResponse, boolean fromStart) {
        progressBarSchedule.setVisibility(View.VISIBLE);

        lessons.clear();
        lessons.addAll(dayResponse.getLessons());

        scheduleAdapter.setLessons(lessons, week);
        scheduleAdapter.notifyDataSetChanged();

        progressBarSchedule.setVisibility(View.INVISIBLE);

        if (fromStart) {
            Objects.requireNonNull(recyclerView.getLayoutManager()).scrollToPosition(remainder);
        }
    }

    //Расчитать разницу в днях между заданной датой и сегодня
    private long daysBetween(Date d1, Date d2) {
        return ((d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
    }

    //Находим какой сегодня день недели под RecyclerView
    //также здесь определяется какая сейчас неделя
    private void findReminder() {
        daysBetween = (int) daysBetween(calFWeek.getTime(), calToday.getTime());
        remainder = daysBetween % 14;

        if (remainder < 6) {
            week = 1;
        } else if (remainder > 6 && remainder < 13) {
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

    //Избегаем утечку данных
    @Override
    protected void onDestroy() {
        schedulePresenter.disposeDisposable();
        super.onDestroy();
    }
}
