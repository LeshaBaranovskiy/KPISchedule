package com.example.kpischedule.screens.choose;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kpischedule.R;
import com.example.kpischedule.screens.schedule.ScheduleActivity;

import java.util.ArrayList;

public class ChooseGroupActivity extends AppCompatActivity implements ChooseGroupView {

    private static final String USER_GROUP = "group";

    private ChooseGroupPresenter presenter;

    private EditText editText;
    private ProgressBar progressBar;

    private TextView textViewChoice1;
    private TextView textViewChoice2;
    private TextView textViewChoice3;
    private TextView textViewChoice4;

    private SharedPreferences sharedPreferencesGroup;
    private SharedPreferences.Editor editor;

    private String group = "";

    private String groupName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        presenter = new ChooseGroupPresenter(this);

        editText = findViewById(R.id.editText);
        progressBar = findViewById(R.id.progressBar);

        textViewChoice1 = findViewById(R.id.textViewChoice1);
        textViewChoice2 = findViewById(R.id.textViewChoice2);
        textViewChoice3 = findViewById(R.id.textViewChoice3);
        textViewChoice4 = findViewById(R.id.textViewChoice4);

        //Тут хранится группа, которую пользователь последний раз вводил
        sharedPreferencesGroup = getSharedPreferences(USER_GROUP, MODE_PRIVATE);

        //Если есть данные о группе, которую пользователь раньше вводил, то сразу перевести пользователя
        //в следующую активность
        if (sharedPreferencesGroup.getString("group", "").length() > 0) {
            redirectToSchedule(sharedPreferencesGroup.getString("group", ""));
        }
        //Загрузка общего количества груп
        presenter.loadTotal();
    }

    public void showSchedule(View view) {
        groupName = editText.getText().toString().toLowerCase();

        //Загрузка общего количества груп
        presenter.loadTotal();

        progressBar.setVisibility(View.VISIBLE);
        if (!groupName.isEmpty()) {
            //Поиск похожих группы по JSON
            presenter.loadGroupByName(groupName);
        } else {
            Toast.makeText(this, R.string.toast_write_group, Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    //Получаем группу/группы из нашего Presenter
    //и в зависимости от их количества даем выбор или стразу переходим в следующую активность
    public void findGroup(ArrayList<String> familiar) {
        if (!familiar.isEmpty()) {
            switch (familiar.size()) {
                case 1:
                    group = familiar.get(0);
                    redirectToSchedule(group);
                    addSharedPreferencesGroup(group);
                    break;
                case 2:
                    textViewChoice1.setText(familiar.get(0).toUpperCase());
                    textViewChoice2.setText(familiar.get(1).toUpperCase());
                    break;
                case 3:
                    textViewChoice1.setText(familiar.get(0).toUpperCase());
                    textViewChoice2.setText(familiar.get(1).toUpperCase());
                    textViewChoice3.setText(familiar.get(2).toUpperCase());
                    break;
                case 4:
                    textViewChoice1.setText(familiar.get(0).toUpperCase());
                    textViewChoice2.setText(familiar.get(1).toUpperCase());
                    textViewChoice3.setText(familiar.get(2).toUpperCase());
                    textViewChoice4.setText(familiar.get(3).toUpperCase());
                    break;
            }
        } else {
            Toast.makeText(this, R.string.toast_group_not_found, Toast.LENGTH_SHORT).show();
        }
        progressBar.setVisibility(View.INVISIBLE);
    }

    //Избейгаем утечки данных
    @Override
    protected void onDestroy() {
        presenter.disposeDisposable();
        super.onDestroy();
    }

    //Обрабатываем выбор группы пользователя
    public void chooseGroup(View view) {
        TextView textView = findViewById(view.getId());
        group = textView.getText().toString();
        addSharedPreferencesGroup(group);
        textViewChoice1.setText("");
        textViewChoice2.setText("");
        textViewChoice3.setText("");
        textViewChoice4.setText("");
        redirectToSchedule(group);
    }

    //Переход в следующую активность с уже выбраной группой
    public void redirectToSchedule(String group) {
        Intent intent = new Intent(this, ScheduleActivity.class);
        intent.putExtra("group", group);
        startActivity(intent);
    }

    //Добавляем группу в sharedPreferences для быстрого захода в следующий раз
    private void addSharedPreferencesGroup(String group) {
        editor = sharedPreferencesGroup.edit();
        editor.putString("group", group).apply();
    }

    //Ошибки при получении даных в Presenter
    public void showToast(String toastMessage) {
        Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show();
    }

    public void setEnSetInv() {
        progressBar.setVisibility(View.INVISIBLE);
    }
}
