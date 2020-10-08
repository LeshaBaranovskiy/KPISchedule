package com.example.kpischedule.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kpischedule.R;
import com.example.kpischedule.pojo.Lesson;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder> {

    private List<Lesson> lessons = new ArrayList<>();

    private List<List<Lesson>> sortedLessonsWeek = new ArrayList<>();

    private List<Lesson> mondayWeek = new ArrayList<>();
    private List<Lesson> tuesdayWeek = new ArrayList<>();
    private List<Lesson> wednesdayWeek = new ArrayList<>();
    private List<Lesson> thursdayWeek = new ArrayList<>();
    private List<Lesson> fridayWeek = new ArrayList<>();
    private List<Lesson> saturdayWeek = new ArrayList<>();

    public List<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(List<Lesson> lessons, int week) {
        this.lessons = lessons;

        mondayWeek.clear();
        tuesdayWeek.clear();
        thursdayWeek.clear();
        fridayWeek.clear();
        saturdayWeek.clear();
        sortedLessonsWeek.clear();

        for (Lesson lesson: lessons) {
            if (week == 1) {
                if (Integer.parseInt(lesson.getLessonWeek()) == 1) {
                    switch (Integer.parseInt(lesson.getDayNumber())) {
                        case 1:
                            mondayWeek.add(lesson);
                            break;
                        case 2:
                            tuesdayWeek.add(lesson);
                            break;
                        case 3:
                            wednesdayWeek.add(lesson);
                            break;
                        case 4:
                            thursdayWeek.add(lesson);
                            break;
                        case 5:
                            fridayWeek.add(lesson);
                            break;
                        case 6:
                            saturdayWeek.add(lesson);
                            break;
                    }
                }
            } else {
            if (Integer.parseInt(lesson.getLessonWeek()) == 2) {
                    switch (Integer.parseInt(lesson.getDayNumber())) {
                        case 1:
                            mondayWeek.add(lesson);
                            break;
                        case 2:
                            tuesdayWeek.add(lesson);
                            break;
                        case 3:
                            wednesdayWeek.add(lesson);
                            break;
                        case 4:
                            thursdayWeek.add(lesson);
                            break;
                        case 5:
                            fridayWeek.add(lesson);
                            break;
                        case 6:
                            saturdayWeek.add(lesson);
                            break;
                    }
                }
            }
        }
        sortedLessonsWeek.add(mondayWeek);
        sortedLessonsWeek.add(tuesdayWeek);
        sortedLessonsWeek.add(wednesdayWeek);
        sortedLessonsWeek.add(thursdayWeek);
        sortedLessonsWeek.add(fridayWeek);
        sortedLessonsWeek.add(saturdayWeek);

        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ScheduleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.day_item, parent, false);
        return new ScheduleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleViewHolder holder, int position) {

            switch (position) {
                case 0:
                    holder.textViewWeekDay.setText("Понеділок");
                    if (mondayWeek.size() > 0) {
                        fillFields(mondayWeek, holder);
                    }
                    break;
                case 1:
                    holder.textViewWeekDay.setText("Вівторок");
                    if (tuesdayWeek.size() > 0) {
                        fillFields(tuesdayWeek, holder);
                    }
                    break;
                case 2:
                    holder.textViewWeekDay.setText("Середа");
                    if (wednesdayWeek.size() > 0) {
                        fillFields(wednesdayWeek, holder);
                    }
                    break;
                case 3:
                    holder.textViewWeekDay.setText("Четвер");
                    if (thursdayWeek.size() > 0) {
                        fillFields(thursdayWeek, holder);
                    }
                    break;
                case 4:
                    holder.textViewWeekDay.setText("Пятниця");
                    if (fridayWeek.size() > 0) {
                        fillFields(fridayWeek, holder);
                    }
                    break;
                case 5:
                    holder.textViewWeekDay.setText("Субота");
                    if (saturdayWeek.size() > 0) {
                        fillFields(saturdayWeek, holder);
                    }
                    break;
            }

    }

    @Override
    public int getItemCount() {
        return sortedLessonsWeek.size();
    }

    private void fillFields (List<Lesson> lessons, ScheduleViewHolder holder) {
        ArrayList<Integer> integers = new ArrayList<>();
        integers.add(1);
        integers.add(2);
        integers.add(3);
        integers.add(4);
        integers.add(5);

        for (Lesson lesson1: lessons) {
                integers.remove(Integer.valueOf(lesson1.getLessonNumber()));
                switch (Integer.parseInt(lesson1.getLessonNumber())) {
                    case 1:
                        holder.textView1LessonName.setText(lesson1.getLessonFullName());
                        holder.textView1TeacherName.setText(lesson1.getTeacherName());
                        holder.textView1Auditory.setText(lesson1.getLessonRoom());
                        break;
                    case 2:
                        holder.textView2LessonName.setText(lesson1.getLessonFullName());
                        holder.textView2TeacherName.setText(lesson1.getTeacherName());
                        holder.textView2Auditory.setText(lesson1.getLessonRoom());
                        break;
                    case 3:
                        holder.textView3LessonName.setText(lesson1.getLessonFullName());
                        holder.textView3TeacherName.setText(lesson1.getTeacherName());
                        holder.textView3Auditory.setText(lesson1.getLessonRoom());
                        break;
                    case 4:
                        holder.textView4LessonName.setText(lesson1.getLessonFullName());
                        holder.textView4TeacherName.setText(lesson1.getTeacherName());
                        holder.textView4Auditory.setText(lesson1.getLessonRoom());
                        break;
                    case 5:
                        holder.textView5LessonName.setText(lesson1.getLessonFullName());
                        holder.textView5TeacherName.setText(lesson1.getTeacherName());
                        holder.textView5Auditory.setText(lesson1.getLessonRoom());
                        break;
                }
        }


        for (int i : integers) {
            switch (i) {
                case 1:
                    holder.textView1LessonName.setText("");
                    holder.textView1TeacherName.setText("");
                    holder.textView1Auditory.setText("");
                    break;
                case 2:
                    holder.textView2LessonName.setText("");
                    holder.textView2TeacherName.setText("");
                    holder.textView2Auditory.setText("");
                    break;
                case 3:
                    holder.textView3LessonName.setText("");
                    holder.textView3TeacherName.setText("");
                    holder.textView3Auditory.setText("");
                    break;
                case 4:
                    holder.textView4LessonName.setText("");
                    holder.textView4TeacherName.setText("");
                    holder.textView4Auditory.setText("");
                    break;
                case 5:
                    holder.textView5LessonName.setText("");
                    holder.textView5TeacherName.setText("");
                    holder.textView5Auditory.setText("");
                    break;
            }
        }
        integers.clear();

    }

    class ScheduleViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewWeekDay;

        private TextView textView1LessonName;
        private TextView textView2LessonName;
        private TextView textView3LessonName;
        private TextView textView4LessonName;
        private TextView textView5LessonName;

        private TextView textView1TeacherName;
        private TextView textView2TeacherName;
        private TextView textView3TeacherName;
        private TextView textView4TeacherName;
        private TextView textView5TeacherName;

        private TextView textView1Auditory;
        private TextView textView2Auditory;
        private TextView textView3Auditory;
        private TextView textView4Auditory;
        private TextView textView5Auditory;

        public ScheduleViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewWeekDay = itemView.findViewById(R.id.textViewWeekDay);

            textView1Auditory = itemView.findViewById(R.id.textView1Auditory);
            textView2Auditory = itemView.findViewById(R.id.textView2Auditory);
            textView3Auditory = itemView.findViewById(R.id.textView3Auditory);
            textView4Auditory = itemView.findViewById(R.id.textView4Auditory);
            textView5Auditory = itemView.findViewById(R.id.textView5Auditory);

            textView1LessonName = itemView.findViewById(R.id.textView1LessonName);
            textView2LessonName = itemView.findViewById(R.id.textView2LessonName);
            textView3LessonName = itemView.findViewById(R.id.textView3LessonName);
            textView4LessonName = itemView.findViewById(R.id.textView4LessonName);
            textView5LessonName = itemView.findViewById(R.id.textView5LessonName);

            textView1TeacherName = itemView.findViewById(R.id.textView1TeacherName);
            textView2TeacherName = itemView.findViewById(R.id.textView2TeacherName);
            textView3TeacherName = itemView.findViewById(R.id.textView3TeacherName);
            textView4TeacherName = itemView.findViewById(R.id.textView4TeacherName);
            textView5TeacherName = itemView.findViewById(R.id.textView5TeacherName);
        }
    }
}
