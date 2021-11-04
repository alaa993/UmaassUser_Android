package net.umaass_user.app.ui;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import net.umaass_user.app.R;
import net.umaass_user.app.data.Repository;
import net.umaass_user.app.data.interfaces.CallBack;
import net.umaass_user.app.data.remote.models.Api;
import net.umaass_user.app.data.remote.models.Comment;
import net.umaass_user.app.data.remote.utils.RequestException;
import net.umaass_user.app.ui.adapter.AdapterComment;
import net.umaass_user.app.ui.base.BaseActivity;
import net.umaass_user.app.ui.components.EmptyView;
import net.umaass_user.app.utils.EndlessRecyclerOnScrollListener;

import java.util.List;

public class ActivityComments extends BaseActivity {

    String id;
    RecyclerView recyclerView;
    EmptyView emptyView;
    SwipeRefreshLayout swipe;
    AdapterComment adapterComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        id = getIntent().getStringExtra("id");
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

        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    @Override
    public void functionView() {
        super.functionView();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapterComment = new AdapterComment();
        recyclerView.setAdapter(adapterComment);
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
            adapterComment.clearAndPut(null);
            swipe.setRefreshing(true);
        }
        emptyView.setVisibility(View.INVISIBLE);
        Repository.getInstance().getComments(id, page, new CallBack<Api<List<Comment>>>() {
            @Override
            public void onSuccess(Api<List<Comment>> listApi) {
                super.onSuccess(listApi);
                swipe.setRefreshing(false);
                if (listApi.getData() != null && listApi.getData().size() > 0) {
                    adapterComment.put(listApi.getData());
                }
                if (adapterComment.getItemCount() == 0) {
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
}
