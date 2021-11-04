package net.umaass_user.app.ui.dialog;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.squareup.picasso.Picasso;

import net.umaass_user.app.R;
import net.umaass_user.app.application.Preference;
import net.umaass_user.app.data.Repository;
import net.umaass_user.app.data.interfaces.CallBack;
import net.umaass_user.app.data.remote.models.Api;
import net.umaass_user.app.data.remote.models.AppointmentDetail;
import net.umaass_user.app.data.remote.models.Avatar;
import net.umaass_user.app.data.remote.models.DefualtResponse;
import net.umaass_user.app.data.remote.models.LastDoneAppt;
import net.umaass_user.app.data.remote.utils.RequestException;
import net.umaass_user.app.ui.components.RoundCornerButton;
import net.umaass_user.app.utils.CircleImageView;

public class DialogRate extends BottomSheetDialog {

    private TextView txtExpertise;
    private TextView txtDrName;
    private EditText edtComment;
    private RoundCornerButton btnSkip;
    private RoundCornerButton btnDone;
    private RatingBar ratingBar;
    private CircleImageView imgProfile;
    private ProgressBar progressBar;
    private RateListener rateListener;
    private LastDoneAppt lastDoneAppt;

    public DialogRate(@NonNull Context context, LastDoneAppt lastDoneAppt) {
        super(context);
        this.lastDoneAppt = lastDoneAppt;
        init(context);
    }

    public void setRateListener(RateListener rateListener) {
        this.rateListener = rateListener;
    }


    private void init(Context context) {
        View contentView = View.inflate(getContext(), R.layout.dialog_rate, null);
        setContentView(contentView);
        imgProfile = contentView.findViewById(R.id.imgProfile);
        txtDrName = contentView.findViewById(R.id.txtDrName);
        txtExpertise = contentView.findViewById(R.id.txtExpertise);
        ratingBar = contentView.findViewById(R.id.ratingBar);
        btnDone = contentView.findViewById(R.id.btnDone);
        edtComment = contentView.findViewById(R.id.edtComment);
        btnSkip = contentView.findViewById(R.id.btnSkip);
        btnDone = contentView.findViewById(R.id.btnDone);
        progressBar = contentView.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cm = edtComment.getText() != null ? edtComment.getText().toString() : "";
                if (rateListener != null) {
                    rateListener.onRate(lastDoneAppt.getId() + "", ratingBar.getRating(), cm);

                }
                rate(lastDoneAppt.getId() + "", ratingBar.getRating(), cm);
                //  dismiss();
            }
        });
        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rateListener != null) {
                    rateListener.onSkip(lastDoneAppt.getId() + "");
                }
                skip();
                // dismiss();
            }
        });
        getData(lastDoneAppt.getId() + "");
    }

    private void getData(String appointmentId) {
        progressBar.setVisibility(View.VISIBLE);
        enable(false);
        Repository.getInstance().getAppointmentDetail(appointmentId, new CallBack<Api<AppointmentDetail>>() {
            @Override
            public void onSuccess(Api<AppointmentDetail> appointmentDetailApi) {
                super.onSuccess(appointmentDetailApi);
                progressBar.setVisibility(View.INVISIBLE);
                enable(true);
                AppointmentDetail detail = appointmentDetailApi.getData();
                txtDrName.setText(detail.getStaff().getName());
                txtExpertise.setText(detail.getIndustry().getCategory().getName());
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

            }
        });
    }

    private void rate(String appointmentId, float rate, String comment) {
        progressBar.setVisibility(View.VISIBLE);
        enable(false);
        Repository.getInstance().addComment(appointmentId, rate, comment, new CallBack<Api<DefualtResponse>>() {
            @Override
            public void onSuccess(Api<DefualtResponse> defualtResponseApi) {
                super.onSuccess(defualtResponseApi);
                dismiss();
            }

            @Override
            public void onFail(RequestException e) {
                super.onFail(e);
                enable(true);
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void enable(boolean b) {
        btnSkip.setEnabled(b);
        btnDone.setEnabled(b);
        edtComment.setEnabled(b);
        ratingBar.setEnabled(b);
    }

    private void skip() {
        progressBar.setVisibility(View.VISIBLE);
        enable(false);
        Repository.getInstance().skipComment(new CallBack<Api<DefualtResponse>>() {
            @Override
            public void onSuccess(Api<DefualtResponse> defualtResponseApi) {
                super.onSuccess(defualtResponseApi);
                dismiss();
            }

            @Override
            public void onFail(RequestException e) {
                super.onFail(e);
                enable(true);
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public void show() {
        super.show();
    }

    public interface RateListener {
        void onRate(String appointmentId, float rate, String comment);

        void onSkip(String appointmentId);
    }
}