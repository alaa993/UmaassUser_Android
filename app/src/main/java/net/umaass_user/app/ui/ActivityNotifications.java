package net.umaass_user.app.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import net.umaass_user.app.R;
import net.umaass_user.app.application.Preference;
import net.umaass_user.app.data.Repository;
import net.umaass_user.app.data.interfaces.CallBack;
import net.umaass_user.app.data.remote.models.Api;
import net.umaass_user.app.data.remote.models.DefualtResponse;
import net.umaass_user.app.data.remote.models.NotificationsModel;
import net.umaass_user.app.data.remote.utils.RequestException;
import net.umaass_user.app.ui.adapter.AdapterNotifications;
import net.umaass_user.app.ui.base.BaseActivity;
import net.umaass_user.app.ui.components.EmptyView;
import net.umaass_user.app.utils.EndlessRecyclerOnScrollListener;

import java.util.ArrayList;
import java.util.List;

public class ActivityNotifications extends BaseActivity {

    String id;
    RecyclerView recyclerView;
    EmptyView emptyView;
    SwipeRefreshLayout swipe;
    AdapterNotifications adapterNotifications;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        readView();
        functionView();
        initViewModel();
    }

    @Override
    public void readView() {
        super.readView();
        swipe = findViewById(R.id.swipe);
        recyclerView = findViewById(R.id.recyclerView);
        emptyView = findViewById(R.id.emptyView);
    }

    @Override
    public void functionView() {
        super.functionView();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapterNotifications = new AdapterNotifications();
        recyclerView.setAdapter(adapterNotifications);
        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                getData(currentPage);
            }
        });
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData(1);
            }
        });
        getData(1);

    }

    private void getData(int page) {
        if (swipe != null && page == 1) {
            adapterNotifications.clearAndPut(null);
            swipe.setRefreshing(true);
        }
        emptyView.setVisibility(View.INVISIBLE);
        Repository.getInstance().getNotifications(Preference.getLanguage(), new CallBack<Api<List<NotificationsModel>>>() {
            @Override
            public void onSuccess(Api<List<NotificationsModel>> listApi) {
                super.onSuccess(listApi);
                swipe.setRefreshing(false);
                List<NotificationsModel> lists = new ArrayList<>();
                for (int i = 0; i < listApi.getData().size(); i++) {
                    if (listApi.getData().get(i).getApp().equals("Customer")){
                        lists.add(listApi.getData().get(i));
                    }
                }
                if (lists != null && lists.size() > 0) {
                    adapterNotifications.clearAndPut(lists);
                }
                if (adapterNotifications.getItemCount() == 0) {
                    emptyView.setVisibility(View.VISIBLE);
                }
                for (int i = 0; i < lists.size(); i++) {
                    changeToReadNotifications(lists.get(i).getId());
                }
            }

            @Override
            public void onFail(RequestException e) {
                super.onFail(e);
                swipe.setRefreshing(false);
            }
        });
    }

    private void changeToReadNotifications(String id) {
        Repository.getInstance().changeToReadNotifications(id, new CallBack<Api<DefualtResponse>>() {
            @Override
            public void onSuccess(Api<DefualtResponse> listApi) {
                super.onSuccess(listApi);
                swipe.setRefreshing(false);

            }

            @Override
            public void onFail(RequestException e) {
                super.onFail(e);
                swipe.setRefreshing(false);
            }
        });
    }
}
