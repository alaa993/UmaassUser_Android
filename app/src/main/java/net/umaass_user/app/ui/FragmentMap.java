package net.umaass_user.app.ui;

import android.content.Intent;
import android.view.View;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import net.umaass_user.app.R;
import net.umaass_user.app.application.G;
import net.umaass_user.app.data.Repository;
import net.umaass_user.app.data.interfaces.CallBack;
import net.umaass_user.app.data.remote.models.Api;
import net.umaass_user.app.data.remote.models.Appointment;
import net.umaass_user.app.data.remote.utils.RequestException;
import net.umaass_user.app.interfac.ItemClickListener;
import net.umaass_user.app.ui.adapter.AdapterAppointment;
import net.umaass_user.app.ui.base.BaseFragment;
import net.umaass_user.app.ui.components.EmptyView;
import net.umaass_user.app.utils.EndlessRecyclerOnScrollListener;

import java.util.List;

public class FragmentMap extends BaseFragment {

    Toolbar toolbar;
    private AdapterAppointment adapter;
    private RecyclerView recyclerView;
    private EmptyView emptyView;
    private SwipeRefreshLayout swipe;


    @Override
    public int getViewLayout() {
        return R.layout.fragment_map;
    }

    @Override
    public void readView() {
        super.readView();
        recyclerView = baseView.findViewById(R.id.recyclerView);
        toolbar = baseView.findViewById(R.id.toolbar);
        emptyView = baseView.findViewById(R.id.emptyView);
        swipe = baseView.findViewById(R.id.swipe);
    }


    @Override
    public void functionView() {
        super.functionView();
        adapter = new AdapterAppointment();
        LinearLayoutManager doctorManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(doctorManager);
        recyclerView.setAdapter(adapter);
        adapter.setListener(new ItemClickListener<Appointment>() {
            @Override
            public void onClick(Appointment item) {
                Intent intent = new Intent(getActivity(), ActivityAppointmentDetail.class);
                intent.putExtra("id", String.valueOf(item.getId()));
                startActivity(intent);
            }
        });
        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(doctorManager) {
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
            adapter.clearAndPut(null);
            swipe.setRefreshing(true);
        }
        emptyView.setVisibility(View.INVISIBLE);
        Repository.getInstance().getAllAppointment("", page, new CallBack<Api<List<Appointment>>>() {
            @Override
            public void onSuccess(Api<List<Appointment>> listApi) {
                super.onSuccess(listApi);
                swipe.setRefreshing(false);
                if (listApi.getData() != null && listApi.getData().size() > 0) {
                    adapter.put(listApi.getData());
                }
                if (adapter.getItemCount() == 0) {
                    emptyView.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onFail(RequestException e) {
                super.onFail(e);
                swipe.setRefreshing(false);

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (G.changeAppointment) {
            G.changeAppointment = false;
            getData(1);
        }
    }
}
