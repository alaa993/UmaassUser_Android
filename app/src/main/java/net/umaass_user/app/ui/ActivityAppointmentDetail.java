package net.umaass_user.app.ui;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.squareup.picasso.Picasso;

import net.umaass_user.app.R;
import net.umaass_user.app.application.G;
import net.umaass_user.app.application.Preference;
import net.umaass_user.app.data.Repository;
import net.umaass_user.app.data.interfaces.CallBack;
import net.umaass_user.app.data.remote.models.Api;
import net.umaass_user.app.data.remote.models.AppointmentDetail;
import net.umaass_user.app.data.remote.models.Avatar;
import net.umaass_user.app.data.remote.models.DefualtResponse;
import net.umaass_user.app.data.remote.utils.RequestException;
import net.umaass_user.app.ui.base.BaseActivity;
import net.umaass_user.app.ui.components.RoundCornerButton;
import net.umaass_user.app.utils.CircleImageView;
import net.umaass_user.app.utils.NotificationCenter;
import net.umaass_user.app.utils.Utils;
import net.umaass_user.app.utils.permission.ActivityUtils;

import java.util.StringTokenizer;

public class ActivityAppointmentDetail extends BaseActivity implements NotificationCenter.NotificationCenterDelegate {

    RoundCornerButton btnBook;
    RoundCornerButton btnEdit;
    TextView txtDrName;
    TextView txtExpertise;
    TextView txtProvidePhone;
    TextView txtAddress;
    TextView txtStatus;
    TextView txtName;
    TextView txtPhone;
    TextView txtAge;
    TextView txtGender;
    TextView txtDesc;
    TextView txtDate;
    TextView txtTime;
    CircleImageView imgProfile;
    String id;

    AppointmentDetail detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_detail);
        readView();
        functionView();
        initViewModel();
    }

    @Override
    public void readView() {
        super.readView();
        btnBook = findViewById(R.id.btnBook);
        txtDrName = findViewById(R.id.txtDrName);
        txtExpertise = findViewById(R.id.txtExpertise);
        txtProvidePhone = findViewById(R.id.txtProvidePhone);
        txtAddress = findViewById(R.id.txtAddress);
        imgProfile = findViewById(R.id.imgProfile);
        txtStatus = findViewById(R.id.txtStatus);
        txtName = findViewById(R.id.txtName);
        txtPhone = findViewById(R.id.txtPhone);
        txtAge = findViewById(R.id.txtAge);
        txtGender = findViewById(R.id.txtGender);
        txtDesc = findViewById(R.id.txtDesc);
        txtDate = findViewById(R.id.txtDate);
        txtTime = findViewById(R.id.txtTime);
        btnEdit = findViewById(R.id.btnEdit);

        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    @Override
    public void functionView() {
        super.functionView();

        btnEdit.setVisibility(View.GONE);
        btnBook.setVisibility(View.GONE);
        txtAge.setVisibility(View.GONE);
        id = getIntent().getStringExtra("id");
        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog();
            }
        });
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (detail != null) {
                    Intent intent = new Intent(ActivityAppointmentDetail.this, ActivityBook.class);
                    intent.putExtra("idProvider", String.valueOf(detail.getStaff().getId()));
                    intent.putExtra("idIndustry", String.valueOf(detail.getIndustry().getId()));
                    intent.putExtra("isEdit", true);
                    intent.putExtra("AppointmentDetail", detail);
                    startActivity(intent);
                }

            }
        });
        txtProvidePhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + txtProvidePhone.getText()));
                startActivity(intent);
            }
        });
    }

    private void getData() {
        showLoading();
        Repository.getInstance().getAppointmentDetail(id, new CallBack<Api<AppointmentDetail>>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onSuccess(Api<AppointmentDetail> appointmentDetailApi) {
                super.onSuccess(appointmentDetailApi);
                hideLoading();
                detail = appointmentDetailApi.getData();
                txtDrName.setText(detail.getStaff().getName());
                txtExpertise.setText(detail.getIndustry().getCategory().getName());
                txtProvidePhone.setText(detail.getIndustry().getPhone());
                txtAddress.setText(detail.getIndustry().getAddress());
                txtStatus.setText(detail.getStatus());
                btnEdit.setVisibility(detail.getStatus().equals("pending") ? View.VISIBLE : View.GONE);
                btnBook.setVisibility(
                        (detail.getStatus().equals("no-show") ||
                         detail.getStatus().equals("done"))
                        ? View.GONE : View.VISIBLE);
                txtName.setText("Name : " + detail.getClientName());
                txtPhone.setText("Phone : " + detail.getClientPhone());
                txtAge.setText("Age : " + detail.getClientAge());
                txtGender.setText("Gender : " + (detail.getClientGender() == 1 ? Utils.getString(R.string.male) : Utils.getString(R.string.female)));

                String date = detail.getStartTime() == null ||
                              detail.getStartTime().isEmpty() ? detail.getFromTo() : detail.getStartTime();
                StringTokenizer tk = new StringTokenizer(date);
                if (tk.hasMoreTokens()) {
                    txtDate.setText(tk.nextToken());
                }
                if (tk.hasMoreTokens()) {
                    txtTime.setText(tk.nextToken());
                }
                txtDesc.setText(detail.getDescription());

                Avatar avatar = detail.getStaff().getAvatar();
                if (avatar != null) {
                    Preference.setImage(avatar.getUrlMd());
                    Picasso.get()
                           .load(avatar.getUrlMd())
                           .placeholder(R.drawable.profile)
                           .error(R.drawable.profile)
                           .into(imgProfile);
                }
            }

            @Override
            public void onFail(RequestException e) {
                super.onFail(e);
                hideLoading();
                G.toast(Utils.getString(R.string.try_again));
                finish();
            }
        });
    }

    private void dialog() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(ActivityUtils.getTopActivity());
        builder1.setMessage(Utils.getString(R.string.sure_for_cancel));
        builder1.setCancelable(true);
        builder1.setPositiveButton(
                Utils.getString(R.string.yes),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        cancel();
                    }
                });

        builder1.setNegativeButton(
                Utils.getString(R.string.no),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }


    private void cancel() {
        showLoading();
        Repository.getInstance().deleteAppointment(id, new CallBack<Api<DefualtResponse>>() {
            @Override
            public void onSuccess(Api<DefualtResponse> defualtResponseApi) {
                super.onSuccess(defualtResponseApi);
                hideLoading();
                G.toast(Utils.getString(R.string.saved));
                G.changeAppointment = true;
                finish();
            }

            @Override
            public void onFail(RequestException e) {
                super.onFail(e);
                hideLoading();
                G.toast(Utils.getString(R.string.try_again));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.UpdateAppointment);
    }

    @Override
    protected void onPause() {
        super.onPause();
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.UpdateAppointment);
    }

    @Override
    public void didReceivedNotification(int id, Object... args) {
        if (id == NotificationCenter.UpdateAppointment) {
            getData();
        }
    }
}
