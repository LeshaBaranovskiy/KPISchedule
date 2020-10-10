package com.example.kpischedule.adapters;

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

        //Очищаем массивы с парами на каждый день
        mondayWeek.clear();
        tuesdayWeek.clear();
        wednesdayWeek.clear();
        thursdayWeek.clear();
        fridayWeek.clear();
        saturdayWeek.clear();
        sortedLessonsWeek.clear();

        //В зависимости от дня недели заносим опредленные пары в опредленные дни
        for (Lesson lesson : lessons) {
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
        holder.textViewWeekDay.setText(lessons.get(0).getDayName());

        //Обнуляем прошлые значения
        holder.textView1LessonName.setText("");
        holder.textView1TeacherName.setText("");
        holder.textView1Auditory.setText("");
        holder.textView1TypeLesson.setText("");
        holder.textViewRating1.setText("");
        holder.textView2LessonName.setText("");
        holder.textView2TeacherName.setText("");
        holder.textView2Auditory.setText("");
        holder.textViewRating2.setText("");
        holder.textView2TypeLesson.setText("");
        holder.textView3LessonName.setText("");
        holder.textView3TeacherName.setText("");
        holder.textView3Auditory.setText("");
        holder.textViewRating3.setText("");
        holder.textView3TypeLesson.setText("");
        holder.textView4LessonName.setText("");
        holder.textView4TeacherName.setText("");
        holder.textView4Auditory.setText("");
        holder.textViewRating4.setText("");
        holder.textView4TypeLesson.setText("");
        holder.textView5LessonName.setText("");
        holder.textView5TeacherName.setText("");
        holder.textView5Auditory.setText("");
        holder.textViewRating5.setText("");
        holder.textView5TypeLesson.setText("");

        List<Lesson> day = sortedLessonsWeek.get(position);

        //Заполняем recyclerView
        for (Lesson lesson1 : day) {
            switch (Integer.parseInt(lesson1.getLessonNumber())) {
                case 1:
                    holder.textView1LessonName.setText(lesson1.getLessonFullName());
                    holder.textView1TeacherName.setText(lesson1.getTeacherName());
                    holder.textView1Auditory.setText(lesson1.getLessonRoom());
                    holder.textView1TypeLesson.setText(lesson1.getLessonType());
                    if (lesson1.getTeachers().size() > 0) {
                        holder.textViewRating1.setText("  Р-г: " + lesson1.getTeachers().get(0).getTeacherRating());
                    }
                    break;
                case 2:
                    holder.textView2LessonName.setText(lesson1.getLessonFullName());
                    holder.textView2TeacherName.setText(lesson1.getTeacherName());
                    holder.textView2Auditory.setText(lesson1.getLessonRoom());
                    holder.textView2TypeLesson.setText(lesson1.getLessonType());
                    if (lesson1.getTeachers().size() > 0) {
                        holder.textViewRating2.setText("  Р-г: " + lesson1.getTeachers().get(0).getTeacherRating());
                    }
                    break;
                case 3:
                    holder.textView3LessonName.setText(lesson1.getLessonFullName());
                    holder.textView3TeacherName.setText(lesson1.getTeacherName());
                    holder.textView3Auditory.setText(lesson1.getLessonRoom());
                    holder.textView3TypeLesson.setText(lesson1.getLessonType());
                    if (lesson1.getTeachers().size() > 0) {
                        holder.textViewRating3.setText("  Р-г: " + lesson1.getTeachers().get(0).getTeacherRating());
                    }
                    break;
                case 4:
                    holder.textView4LessonName.setText(lesson1.getLessonFullName());
                    holder.textView4TeacherName.setText(lesson1.getTeacherName());
                    holder.textView4Auditory.setText(lesson1.getLessonRoom());
                    holder.textView4TypeLesson.setText(lesson1.getLessonType());
                    if (lesson1.getTeachers().size() > 0) {
                        holder.textViewRating4.setText("  Р-г: " + lesson1.getTeachers().get(0).getTeacherRating());
                    }
                    break;
                case 5:
                    holder.textView5LessonName.setText(lesson1.getLessonFullName());
                    holder.textView5TeacherName.setText(lesson1.getTeacherName());
                    holder.textView5Auditory.setText(lesson1.getLessonRoom());
                    holder.textView5TypeLesson.setText(lesson1.getLessonType());
                    if (lesson1.getTeachers().size() > 0) {
                        holder.textViewRating5.setText("  Р-г: " + lesson1.getTeachers().get(0).getTeacherRating());
                    }
                    break;
            }
        }

        switch (position) {
            case 0:
                holder.textViewWeekDay.setText("Понеділок");
                break;
            case 1:
                holder.textViewWeekDay.setText("Вівторок");
                break;
            case 2:
                holder.textViewWeekDay.setText("Середа");
                break;
            case 3:
                holder.textViewWeekDay.setText("Четвер");
                break;
            case 4:
                holder.textViewWeekDay.setText("П'ятниця");
                break;
            case 5:
                holder.textViewWeekDay.setText("Субота");
                break;
        }

    }

    @Override
    public int getItemCount() {
        return sortedLessonsWeek.size();
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

        private TextView textViewRating1;
        private TextView textViewRating2;
        private TextView textViewRating3;
        private TextView textViewRating4;
        private TextView textViewRating5;

        private TextView textView1TypeLesson;
        private TextView textView2TypeLesson;
        private TextView textView3TypeLesson;
        private TextView textView4TypeLesson;
        private TextView textView5TypeLesson;

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

            textViewRating1 = itemView.findViewById(R.id.textViewRating1);
            textViewRating2 = itemView.findViewById(R.id.textViewRating2);
            textViewRating3 = itemView.findViewById(R.id.textViewRating3);
            textViewRating4 = itemView.findViewById(R.id.textViewRating4);
            textViewRating5 = itemView.findViewById(R.id.textViewRating5);

            textView1TypeLesson = itemView.findViewById(R.id.textView1TypeLesson);
            textView2TypeLesson = itemView.findViewById(R.id.textView2TypeLesson);
            textView3TypeLesson = itemView.findViewById(R.id.textView3TypeLesson);
            textView4TypeLesson = itemView.findViewById(R.id.textView4TypeLesson);
            textView5TypeLesson = itemView.findViewById(R.id.textView5TypeLesson);
        }
    }
}
