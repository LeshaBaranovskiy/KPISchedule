package com.example.kpischedule;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.telecom.CallScreeningService;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kpischedule.adapters.ScheduleAdapter;
import com.example.kpischedule.api.ApiFactoryGroups;
import com.example.kpischedule.api.ApiServiceGroups;
import com.example.kpischedule.pojo.GroupsListResponse;
import com.example.kpischedule.pojo.Lesson;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BooleanSupplier;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private Button buttonShow;
    private ProgressBar progressBar;

    private TextView textViewChoice1;
    private TextView textViewChoice2;
    private TextView textViewChoice3;
    private TextView textViewChoice4;

    private Disposable disposable;
    private Disposable disposable1;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private String group = "";

    private String groupName = "";
    private int totalCount = 0;
    private int groupCount = 0;

    private ArrayList<String> familiar = new ArrayList<>();

    private ApiFactoryGroups apiFactoryGroups;
    private ApiServiceGroups apiServiceGroups;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.editText);
        buttonShow = findViewById(R.id.buttonShow);
        progressBar = findViewById(R.id.progressBar);

        textViewChoice1 = findViewById(R.id.textViewChoice1);
        textViewChoice2 = findViewById(R.id.textViewChoice2);
        textViewChoice3 = findViewById(R.id.textViewChoice3);
        textViewChoice4 = findViewById(R.id.textViewChoice4);


        apiFactoryGroups = ApiFactoryGroups.getInstance();
        apiServiceGroups = apiFactoryGroups.getApiServiceGroups();

        disposable1 = apiServiceGroups.getGroupsList(String.format("{\"offset\":%s}", groupCount))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<GroupsListResponse>() {
                    @Override
                    public void accept(GroupsListResponse groupsListResponse) throws Exception {
                        totalCount = Integer.parseInt(groupsListResponse.getMeta().getTotalCount());
                        groupCount = 0;
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("thro", Objects.requireNonNull(throwable.getMessage()));
                    }
                });
        compositeDisposable.add(disposable1);
    }

    public void showSchedule(View view) {
        groupName = editText.getText().toString().toLowerCase();

        if (!groupName.isEmpty()) {
            progressBar.setVisibility(View.VISIBLE);

            while (groupCount < totalCount) {
                progressBar.setVisibility(View.VISIBLE);

                disposable = apiServiceGroups.getGroupsList(String.format("{\"offset\":%s}", groupCount))
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<GroupsListResponse>() {
                            @Override
                            public void accept(GroupsListResponse groupsListResponse) throws Exception {
                                for (int i = 0; i < groupsListResponse.getGroups().size(); i++) {
                                    String currentName = groupsListResponse.getGroups().get(i).getGroupFullName();
                                    if (groupName.equals(currentName.split(" ")[0])) {
                                        familiar.add(currentName);
                                    }
                                }
                                if (Integer.parseInt(groupsListResponse.getMeta().getTotalCount()) < groupsListResponse.getMeta().getOffset() + 100) {
                                    findGroup();
                                    familiar.clear();
                                }
                            }

                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                Log.e("thro", Objects.requireNonNull(throwable.getMessage()));
                            }
                        });
                compositeDisposable.add(disposable);
                groupCount = groupCount + 100;
                progressBar.setVisibility(View.INVISIBLE);
            }
            groupCount = 0;
        } else {
            Toast.makeText(this, R.string.toast_write_group, Toast.LENGTH_SHORT).show();
        }
    }

    public void findGroup() {
        if (!familiar.isEmpty()) {
            switch (familiar.size()) {
                case 1:
                    group = familiar.get(0);
                    redirectToSchedule();
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
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
    }

    public void chooseGroup(View view) {
        TextView textView = findViewById(view.getId());
        group = textView.getText().toString();
        textViewChoice1.setText("");
        textViewChoice2.setText("");
        textViewChoice3.setText("");
        textViewChoice4.setText("");
        redirectToSchedule();
    }

    public void redirectToSchedule() {
        Intent intent = new Intent(this, ScheduleActivity.class);
        intent.putExtra("group", group);
        startActivity(intent);
    }
}
