package net.umaass_user.app.ui;


import android.os.Bundle;
import android.view.View;

import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

import net.umaass_user.app.R;
import net.umaass_user.app.application.G;
import net.umaass_user.app.data.Repository;
import net.umaass_user.app.data.interfaces.CallBack;
import net.umaass_user.app.data.remote.models.Api;
import net.umaass_user.app.data.remote.models.AppointmentDetail;
import net.umaass_user.app.data.remote.utils.RequestException;
import net.umaass_user.app.ui.adapter.MyStepperAdapter;
import net.umaass_user.app.ui.base.BaseActivity;
import net.umaass_user.app.utils.Utils;

public class ActivityBook extends BaseActivity {

    StepperLayout stepperLayout;
    String providerId;
    String industryId;
    AppointmentDetail appointmentDetail;
    boolean isEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        readView();
        functionView();
    }

    @Override
    public void readView() {
        super.readView();
        stepperLayout = findViewById(R.id.stepperLayout);
    }

    MyStepperAdapter myStepperAdapter;

    @Override
    public void functionView() {
        super.functionView();
        providerId = getIntent().getStringExtra("idProvider");
        industryId = getIntent().getStringExtra("idIndustry");
        isEdit = getIntent().getBooleanExtra("isEdit", false);
        if (isEdit) {
            appointmentDetail = getIntent().getParcelableExtra("AppointmentDetail");
        }
        myStepperAdapter = new MyStepperAdapter(getSupportFragmentManager(), this);
        myStepperAdapter.setProviderId(providerId);
        myStepperAdapter.setIndustryId(industryId);
        myStepperAdapter.setEdit(isEdit);
        myStepperAdapter.setAppointmentDetail(appointmentDetail);
        stepperLayout.setAdapter(myStepperAdapter);
        stepperLayout.setListener(new StepperLayout.StepperListener() {
            @Override
            public void onCompleted(View completeButton) {
                if (isEdit) {
                    update();
                } else {
                    save();
                }

            }

            @Override
            public void onError(VerificationError verificationError) {

            }

            @Override
            public void onStepSelected(int newStepPosition) {

            }

            @Override
            public void onReturn() {

            }
        });
    }

    private void update() {
        showLoading();
        Repository.getInstance().updateAppointment(appointmentDetail.getId() + "",
                                                   myStepperAdapter.getNewBook(), new CallBack<Api<AppointmentDetail>>() {
                    @Override
                    public void onSuccess(Api<AppointmentDetail> appointmentDetailApi) {
                        super.onSuccess(appointmentDetailApi);
                        hideLoading();
                        G.toast(Utils.getString(R.string.saved));
                        G.changeAppointment = true;
                        finish();
                    }

                    @Override
                    public void onFail(RequestException e) {
                        super.onFail(e);
                        hideLoading();
                        if (e.getCode() == 422) {
                            G.toast(e.getMessage());
                        } else {
                            G.toast(Utils.getString(R.string.try_again));
                        }

                    }
                });
    }

    private void save() {
        showLoading();
        Repository.getInstance().makeAppointment(myStepperAdapter.getNewBook(), new CallBack<Api<AppointmentDetail>>() {
            @Override
            public void onSuccess(Api<AppointmentDetail> appointmentDetailApi) {
                super.onSuccess(appointmentDetailApi);
                hideLoading();
                G.toast(Utils.getString(R.string.saved));
                G.changeAppointment = true;
                finish();
            }

            @Override
            public void onFail(RequestException e) {
                super.onFail(e);
                hideLoading();
                if (e.getCode() == 422) {
                    G.toast(e.getMessage());
                } else {
                    G.toast(Utils.getString(R.string.try_again));
                }

            }
        });
    }


}
