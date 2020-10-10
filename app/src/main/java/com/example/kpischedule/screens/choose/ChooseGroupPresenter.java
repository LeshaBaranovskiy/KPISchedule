package com.example.kpischedule.screens.choose;

import android.util.Log;

import com.example.kpischedule.model.api.ApiFactoryGroups;
import com.example.kpischedule.model.api.ApiServiceGroups;
import com.example.kpischedule.pojo.GroupsListResponse;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ChooseGroupPresenter {

    private ApiFactoryGroups apiFactoryGroups;
    private ApiServiceGroups apiServiceGroups;

    private Disposable disposable;
    private Disposable disposable1;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private ArrayList<String> familiar = new ArrayList<>();

    private int totalCount = 1;
    private int groupCount = 0;

    private ChooseGroupView chooseGroupView;

    public ChooseGroupPresenter(ChooseGroupView chooseGroupView) {
        this.chooseGroupView = chooseGroupView;
    }

    //Загрузка количества групп в КПИ
    public void loadTotal() {
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
                        chooseGroupView.setEnSetInv();
                        Log.i("fff", "1");
                    }
                });
        compositeDisposable.add(disposable1);
    }

    static class Cancel {
        boolean shouldCancel = false;
    }

    //Ведем поиск с помощью RxJava в другом програмном потоке(!!!)
    //название групп
    public void loadGroupByName(final String groupName) {

        final Cancel trigger = new Cancel();
        //Цыкл создан для того, чтобы пройтись по всем запросам JSON
        while (groupCount < totalCount && !trigger.shouldCancel) {
            disposable = apiServiceGroups.getGroupsList(String.format("{\"offset\":%s}", groupCount))
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<GroupsListResponse>() {
                        @Override
                        public void accept(GroupsListResponse groupsListResponse) throws Exception {
                            //На данной странице JSON проходимся по всем группам
                            for (int i = 0; i < groupsListResponse.getGroups().size(); i++) {
                                String currentName = groupsListResponse.getGroups().get(i).getGroupFullName();
                                if (groupName.equals(currentName.split(" ")[0])) {
                                    familiar.add(currentName);
                                }
                            }
                            //Если это была последняя страница, то запускаем findGroup()
                            if (Integer.parseInt(groupsListResponse.getMeta().getTotalCount()) < groupsListResponse.getMeta().getOffset() + 100) {
                                chooseGroupView.findGroup(familiar);
                                familiar.clear();
                            }
                        }

                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            chooseGroupView.setEnSetInv();
                            chooseGroupView.showToast("Відсутній інтернет");
                            trigger.shouldCancel = true;
                            Log.i("fff", "2");
                        }
                    });
            compositeDisposable.add(disposable);
            groupCount = groupCount + 100;
        }
        groupCount = 0;
    }

    //Избегаем утечки данных
    public void disposeDisposable() {
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
    }

}
