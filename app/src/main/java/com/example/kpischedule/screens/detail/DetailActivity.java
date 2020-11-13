package com.example.kpischedule.screens.detail;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kpischedule.R;
import com.example.kpischedule.model.db.LessonDatabase;
import com.example.kpischedule.pojo.Lesson;

import org.w3c.dom.Text;

public class DetailActivity extends AppCompatActivity implements DetailView {

    private LessonDatabase database;

    private String name;
    private String type;

    private TextView textViewLesson;
    private TextView textViewTeacher;
    private TextView textViewType;
    private TextView textViewLocation;
    private TextView textViewShowZoom;

    private EditText editTextZoomAdd;

    private ImageView imageViewEdit;
    private ImageView imageViewSave;

    private DetailPresenter detailPresenter;

    private Lesson currentLesson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        textViewLesson = findViewById(R.id.textViewLessonDetail);
        textViewTeacher = findViewById(R.id.textViewTeacherDetail);
        textViewType = findViewById(R.id.textViewTypeDetail);
        textViewLocation = findViewById(R.id.textViewLocationDetail);
        textViewShowZoom = findViewById(R.id.textViewZoomReady);

        imageViewEdit = findViewById(R.id.imageViewEdit);
        imageViewSave = findViewById(R.id.imageViewSave);

        detailPresenter = new DetailPresenter(this);

        editTextZoomAdd = findViewById(R.id.editTextZoomInsert);

        detailPresenter = new DetailPresenter(this);

        name = getIntent().getStringExtra("lesson");
        type = getIntent().getStringExtra("type");

        detailPresenter.getLessonByName(name, type);
    }

    @Override
    public LessonDatabase getDatabaseContext() {
        database = LessonDatabase.getInstance(getApplicationContext());
        return database;
    }

    @Override
    public void findLesson(Lesson lesson) {
        textViewLesson.setText(lesson.getLessonFullName());
        textViewTeacher.setText(lesson.getTeacherName());
        textViewType.setText(lesson.getLessonType());
        textViewLocation.setText(lesson.getLessonRoom());

        currentLesson = lesson;

        if (lesson.getZoom() == null) {
            saveTemplate();
        } else {
            editTemplate();
            String zoomLink = lesson.getZoom();
            textViewShowZoom.setText(zoomLink);
        }
    }

    public void saveTemplate() {
        imageViewSave.setVisibility(View.VISIBLE);
        editTextZoomAdd.setVisibility(View.VISIBLE);
        imageViewEdit.setVisibility(View.INVISIBLE);
        textViewShowZoom.setVisibility(View.INVISIBLE);
    }

    public void editTemplate() {
        imageViewSave.setVisibility(View.INVISIBLE);
        editTextZoomAdd.setVisibility(View.INVISIBLE);
        imageViewEdit.setVisibility(View.VISIBLE);
        textViewShowZoom.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        detailPresenter.disposeDisposable();
    }

    public void saveZoom(View view) {
        String editTextValue = editTextZoomAdd.getText().toString();
        if (!editTextValue.isEmpty()) {
            currentLesson.setZoom(editTextValue);
            detailPresenter.updateWithNewZoom(Integer.parseInt(currentLesson.getLessonId()), editTextValue);
        }
    }

    public void editZoom(View view) {
        String currentLink = textViewShowZoom.getText().toString();
        saveTemplate();
        editTextZoomAdd.setText(currentLink);
    }
}
