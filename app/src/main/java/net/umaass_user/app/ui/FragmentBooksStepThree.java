package net.umaass_user.app.ui;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;
import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

import net.umaass_user.app.R;
import net.umaass_user.app.application.G;
import net.umaass_user.app.application.Preference;
import net.umaass_user.app.data.Repository;
import net.umaass_user.app.data.interfaces.CallBack;
import net.umaass_user.app.data.remote.models.Api;
import net.umaass_user.app.data.remote.models.AppointmentDetail;
import net.umaass_user.app.data.remote.models.Avatar;
import net.umaass_user.app.data.remote.models.ProviderDetail;
import net.umaass_user.app.data.remote.utils.RequestException;
import net.umaass_user.app.models.NewBook;
import net.umaass_user.app.ui.base.BaseFragment;
import net.umaass_user.app.utils.CircleImageView;
import net.umaass_user.app.utils.Utils;

import java.util.StringTokenizer;

public class FragmentBooksStepThree extends BaseFragment implements BlockingStep {

    private NewBook newBook;
    private String providerId;
    private String industryId;

    private CircleImageView imgProfile;
    private TextView txtProviderName;
    private TextView txtTakhasos;
    private TextView txtDate;
    private TextView txtTime;
    private TextView txtName;
    private TextView txtGender;
    private TextView txtAge;
    private TextView txtPhone;
    private TextView txtDesc;
    private AppointmentDetail appointmentDetail;
    private boolean isEdit;

    public void setAppointmentDetail(AppointmentDetail appointmentDetail) {
        this.appointmentDetail = appointmentDetail;
    }

    public void setEdit(boolean edit) {
        isEdit = edit;
    }


    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }
    public void setIndustryId(String industryId) {
        this.industryId = industryId;
    }


    public void setNewBook(NewBook newBook) {
        this.newBook = newBook;
    }

    @Override
    public int getViewLayout() {
        return R.layout.fragment_books_step_three;
    }

    @Override
    public void readView() {
        super.readView();
        imgProfile = baseView.findViewById(R.id.imgProfile);
        txtProviderName = baseView.findViewById(R.id.txtProviderName);
        txtTakhasos = baseView.findViewById(R.id.txtTakhasos);
        txtDate = baseView.findViewById(R.id.txtDate);
        txtTime = baseView.findViewById(R.id.txtTime);
        txtName = baseView.findViewById(R.id.txtName);
        txtGender = baseView.findViewById(R.id.txtGender);
        txtAge = baseView.findViewById(R.id.txtAge);
        txtPhone = baseView.findViewById(R.id.txtPhone);
        txtDesc = baseView.findViewById(R.id.txtDesc);

        txtAge.setVisibility(View.GONE);
    }


    @Override
    public void functionView() {
        super.functionView();
        getData();
    }

    private void getData() {
        Repository.getInstance().getProviderDetail(providerId, new CallBack<Api<ProviderDetail>>() {
            @Override
            public void onSuccess(Api<ProviderDetail> providerDetailApi) {
                super.onSuccess(providerDetailApi);
                txtProviderName.setText(providerDetailApi.getData().getName());
                txtTakhasos.setText(providerDetailApi.getData().getIndustry().getTitle());
                Avatar avatar = providerDetailApi.getData().getAvatar();
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

    @Override
    public void onNextClicked(StepperLayout.OnNextClickedCallback callback) {
        callback.goToNextStep();
    }

    @Override
    public void onCompleteClicked(StepperLayout.OnCompleteClickedCallback callback) {
        callback.complete();
    }

    @Override
    public void onBackClicked(StepperLayout.OnBackClickedCallback callback) {
        callback.goToPrevStep();
    }

    @Nullable
    @Override
    public VerificationError verifyStep() {
        return null;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onSelected() {
        G.log("Booking", "Step 3");

        txtAge.setText(Utils.getString(R.string.age_) + newBook.client_age);
        txtGender.setText(Utils.getString(R.string.gender_) + (newBook.client_gender.equals("1") ? "Male" : "Female"));
        txtName.setText(Utils.getString(R.string.name_) + newBook.client_name);
        txtPhone.setText(Utils.getString(R.string.phone_) + newBook.client_phone);

        if (newBook.from_to != null) {
            StringTokenizer tk = new StringTokenizer(newBook.from_to);
            txtDate.setText(Utils.getString(R.string.date_) + tk.nextToken());
            txtTime.setText(Utils.getString(R.string.time_) + tk.nextToken());
        }
        txtDesc.setText(newBook.description);
    }

    @Override
    public void onError(@NonNull VerificationError error) {

    }


}
