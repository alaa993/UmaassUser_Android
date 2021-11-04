package net.umaass_user.app.ui;

import android.os.Bundle;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import net.umaass_user.app.R;
import net.umaass_user.app.data.Repository;
import net.umaass_user.app.data.interfaces.CallBack;
import net.umaass_user.app.data.remote.models.Api;
import net.umaass_user.app.data.remote.models.WorkingHoursItem;
import net.umaass_user.app.data.remote.utils.RequestException;
import net.umaass_user.app.ui.base.BaseActivity;
import net.umaass_user.app.ui.components.DayRangeTimeView;

import java.util.ArrayList;
import java.util.List;

public class ActivityProviderWorkingHours extends BaseActivity {

    private DayRangeTimeView vSunday;
    private DayRangeTimeView vMonday;
    private DayRangeTimeView vTuesday;
    private DayRangeTimeView vWednesday;
    private DayRangeTimeView vThursday;
    private DayRangeTimeView vFriday;
    private DayRangeTimeView vSaturday;
    private List<DayRangeTimeView> rangeTimeViewList = new ArrayList<>();

    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_working_hours);
        id = getIntent().getStringExtra("id");
        readView();
        functionView();
        initViewModel();
    }

    @Override
    public void readView() {
        super.readView();
        vSunday = findViewById(R.id.vSunday);
        vMonday = findViewById(R.id.vMonday);
        vTuesday = findViewById(R.id.vTuesday);
        vWednesday = findViewById(R.id.vWednesday);
        vThursday = findViewById(R.id.vThursday);
        vFriday = findViewById(R.id.vFriday);
        vSaturday = findViewById(R.id.vSaturday);

        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    @Override
    public void functionView() {
        super.functionView();
        rangeTimeViewList.add(vSunday);
        rangeTimeViewList.add(vMonday);
        rangeTimeViewList.add(vTuesday);
        rangeTimeViewList.add(vWednesday);
        rangeTimeViewList.add(vThursday);
        rangeTimeViewList.add(vFriday);
        rangeTimeViewList.add(vSaturday);

        getData();
    }

    private void getData() {
        showLoading();
        Repository.getInstance().getWorkingHours(id, new CallBack<Api<List<WorkingHoursItem>>>() {
            @Override
            public void onSuccess(Api<List<WorkingHoursItem>> listApi) {
                super.onSuccess(listApi);
                hideLoading();
                if (listApi != null && listApi.getData() != null) {
                    for (int i = 0; i < listApi.getData().size(); i++) {
                        WorkingHoursItem datum = listApi.getData().get(i);
                        DayRangeTimeView timeView = rangeTimeViewList.get(datum.getDay());
                        timeView.setEndText(datum.getEnd());
                        timeView.setStartText(datum.getStart());
                    }
                }
            }

            @Override
            public void onFail(RequestException e) {
                super.onFail(e);
                hideLoading();
            }
        });
    }
}
