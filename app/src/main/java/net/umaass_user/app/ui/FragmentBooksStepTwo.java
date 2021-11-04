package net.umaass_user.app.ui;

import android.text.Html;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatRadioButton;

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
import net.umaass_user.app.data.remote.models.ProviderDetail;
import net.umaass_user.app.data.remote.utils.RequestException;
import net.umaass_user.app.interfac.ItemClickListener;
import net.umaass_user.app.interfac.ListItem;
import net.umaass_user.app.models.CommonItem;
import net.umaass_user.app.models.NewBook;
import net.umaass_user.app.ui.base.BaseFragment;
import net.umaass_user.app.ui.dialog.DialogList;
import net.umaass_user.app.utils.Utils;
import net.umaass_user.app.utils.permission.ActivityUtils;

import java.util.ArrayList;
import java.util.List;

public class FragmentBooksStepTwo extends BaseFragment implements BlockingStep {

    private EditText edtPhone;
    private EditText edtName;
    private EditText edtAge;
    private EditText edtGender;
    private EditText edtService;
    private EditText edtDesc;
    private AppCompatCheckBox chkRules;
    private NewBook newBook;
    private TextView txtRules;
    private AppCompatRadioButton radioMe;
    private String providerId;
    private String industryId;
    private View layHide;
    private View layRule;

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
        return R.layout.fragment_books_step_two;
    }

    @Override
    public void readView() {
        super.readView();
        edtPhone = baseView.findViewById(R.id.edtPhone);
        edtName = baseView.findViewById(R.id.edtName);
        edtAge = baseView.findViewById(R.id.edtAge);
        layRule = baseView.findViewById(R.id.layRule);
        edtGender = baseView.findViewById(R.id.edtGender);
        edtService = baseView.findViewById(R.id.edtService);
        edtDesc = baseView.findViewById(R.id.edtDesc);
        chkRules = baseView.findViewById(R.id.chkRules);
        txtRules = baseView.findViewById(R.id.txtRules);
        radioMe = baseView.findViewById(R.id.radioMe);
        layHide = baseView.findViewById(R.id.layHide);

    }

    private String genderId;
    private String serviceId;

    @Override
    public void functionView() {
        super.functionView();
        boolean b = Preference.isAcceptRules();
        edtAge.setVisibility(View.GONE);
        chkRules.setChecked(true);
        layRule.setVisibility(View.GONE);
        if (isEdit) {
            genderId = appointmentDetail.getClientGender() + "";
            edtGender.setText(appointmentDetail.getClientGender() == 0 ? Utils.getString(R.string.female) : Utils.getString(R.string.male));
            edtAge.setText(String.valueOf(appointmentDetail.getClientAge()));
            edtPhone.setText(appointmentDetail.getClientPhone());
            edtDesc.setText(appointmentDetail.getDescription());
            edtName.setText(appointmentDetail.getClientName());
            edtService.setText(appointmentDetail.getService().getTitle());
            serviceId = appointmentDetail.getService().getId() + "";
        }

        chkRules.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Preference.setAcceptRules(b);
            }
        });
        radioMe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                onCheckChange(b);
            }
        });
        edtService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getService();
            }
        });
        edtGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<CommonItem> commonItems = new ArrayList<>();
                commonItems.add(new CommonItem("1", Utils.getString(R.string.male)));
                commonItems.add(new CommonItem("0", Utils.getString(R.string.female)));
                DialogList dialogList = new DialogList(ActivityUtils.getTopActivity(), false);
                dialogList.setTitle(Utils.getString(R.string.choose_gender));
                dialogList.clearAndPut(commonItems);
                dialogList.setListener(new ItemClickListener<ListItem>() {
                    @Override
                    public void onClick(ListItem item) {
                        genderId = item.getItemId();
                        edtGender.setError(null);
                        edtGender.setText(item.getItemName());
                    }
                });
                dialogList.show();
            }
        });
        edtGender.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return true;
            }
        });
        edtService.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return true;
            }
        });
        //   getRules();
        onCheckChange(true);
    }

    private void onCheckChange(boolean checked) {
        if (checked) {
            edtName.setText(Preference.getFirstName());
            edtPhone.setText(Preference.getPhone());
            edtAge.setText(String.valueOf(Preference.getAge()));
            genderId = String.valueOf(Preference.getGender());
            edtGender.setText(genderId);
            layHide.setVisibility(View.GONE);
        } else {
            edtName.setText("");
            edtPhone.setText("");
            edtAge.setText("");
            edtGender.setText("");
            layHide.setVisibility(View.VISIBLE);
        }

    }

    private void getRules() {
        Repository.getInstance().getRules(Preference.getLanguage(), new CallBack<Api<String>>() {
            @Override
            public void onSuccess(Api<String> stringApi) {
                super.onSuccess(stringApi);
                if (stringApi != null) {
                    txtRules.setText(Html.fromHtml(stringApi.getData()));
                }
            }
        });
    }

    private void getService() {
        showLoading();
        Repository.getInstance().getProviderDetail(providerId, new CallBack<Api<ProviderDetail>>() {
            @Override
            public void onSuccess(Api<ProviderDetail> providerDetailApi) {
                super.onSuccess(providerDetailApi);
                hideLoading();
                DialogList dialogList = new DialogList(ActivityUtils.getTopActivity(), false);
                dialogList.setTitle(Utils.getString(R.string.choose_service));
                dialogList.clearAndPut(providerDetailApi.getData().getServices());
                dialogList.setListener(new ItemClickListener<ListItem>() {
                    @Override
                    public void onClick(ListItem item) {
                        serviceId = item.getItemId();
                        edtService.setError(null);
                        edtService.setText(item.getItemName());
                    }
                });
                dialogList.show();
            }

            @Override
            public void onFail(RequestException e) {
                super.onFail(e);
                hideLoading();
            }
        });
    }

    private void getData() {

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
        boolean b = Utils.isEmptyEditText(edtPhone, edtName, edtGender, edtService);
        if (b) {
            return new VerificationError("");
        }
        if (!chkRules.isChecked()) {
            G.toast(Utils.getString(R.string.please_check_the_rules));
            return new VerificationError(Utils.getString(R.string.rules));
        }
        newBook.client_name = edtName.getText().toString();
        newBook.client_phone = edtPhone.getText().toString();
        newBook.client_age = edtAge.getText().toString();
        newBook.client_gender = genderId;
        newBook.service_id = serviceId;
        newBook.description = edtDesc.getText().toString();
        return null;
    }

    @Override
    public void onSelected() {
        G.log("Booking", "Step 2");
    }

    @Override
    public void onError(@NonNull VerificationError error) {

    }
}
