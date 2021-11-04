package net.umaass_user.app.ui;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import net.umaass_user.app.R;
import net.umaass_user.app.application.G;
import net.umaass_user.app.application.Preference;
import net.umaass_user.app.data.Repository;
import net.umaass_user.app.data.interfaces.CallBack;
import net.umaass_user.app.data.remote.models.Api;
import net.umaass_user.app.data.remote.models.DefualtResponse;
import net.umaass_user.app.data.remote.models.Register;
import net.umaass_user.app.data.remote.utils.RequestException;
import net.umaass_user.app.interfac.ItemClickListener;
import net.umaass_user.app.interfac.ListItem;
import net.umaass_user.app.models.CommonItem;
import net.umaass_user.app.ui.base.BaseActivity;
import net.umaass_user.app.ui.components.RoundCornerButton;
import net.umaass_user.app.ui.dialog.DialogList;
import net.umaass_user.app.utils.CircleImageView;
import net.umaass_user.app.utils.Utils;
import net.umaass_user.app.utils.permission.ActivityUtils;
import net.umaass_user.app.utils.permission.helper.PermissionHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class ActivityRegister extends BaseActivity implements DatePickerDialog.OnDateSetListener {

    EditText edtFirstName;
    EditText edtEmail;
    EditText edtDate;
    EditText edtGender;
    RoundCornerButton btnDone;

    CircleImageView imgProfile;
    private static final int PICK_FROM_FILE = 3;


    String access_token,phoneNumber;
    private String genderId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        access_token = getIntent().getExtras().getString("access_token");
        phoneNumber = getIntent().getExtras().getString("phone");
        readView();
        functionView();
        initViewModel();
    }

    @Override
    public void readView() {
        edtFirstName = findViewById(R.id.edtFirstName);
        edtEmail = findViewById(R.id.edtEmail);
        edtDate = findViewById(R.id.edtDate);
        edtGender = findViewById(R.id.edtGender);
        imgProfile = findViewById(R.id.imgProfile);
        btnDone = findViewById(R.id.btnDone);

        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    @Override
    public void functionView() {
        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PermissionHelper.requestStorage(new PermissionHelper.OnPermissionGrantedListener() {
                    @Override
                    public void onPermissionGranted() {
                        startPickImage();
                    }
                });
            }
        });
        edtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialogDatePicker();
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
        edtDate.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return true;
            }
        });
        edtGender.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return true;
            }
        });
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });
        edtDate.setVisibility(View.GONE);
    }

    private void register() {

        boolean b = check(edtFirstName, edtGender);
        if (b) {
            return;
        }
        String fName = edtFirstName.getText().toString();
        String email = edtEmail.getText().toString();
       // String birthDay = edtDate.getText().toString();
        String birthDay = "2019-01-01";
        String gender = genderId;
        if (email != null && !email.isEmpty()) {
            if (!Utils.isValidMail(email)) {
                edtEmail.setError(Utils.getString(R.string.not_valid));
                return;
            }
        }
        showLoading();
        Repository.getInstance().register(access_token, fName, email, birthDay, gender,phoneNumber, new CallBack<Api<Register>>() {
            @Override
            public void onSuccess(Api<Register> registerApi) {
                super.onSuccess(registerApi);
                hideLoading();
                Preference.setToken(registerApi.getData().getToken());
                Preference.setValidLogin(false);
                if (imagePath != null && !imagePath.isEmpty()) {
                    sendImage();
                } else {
                    successIntent();
                }


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

    private void successIntent() {
        ActivityUtils.finishAllActivitiesExceptNewest();
        Intent intent = new Intent(ActivityRegister.this, ActivityCategory.class);
        startActivity(intent);
        finish();
    }

    private void sendImage() {
        showLoading();
        Repository.getInstance().uploadAvatar(imagePath, new CallBack<Api<DefualtResponse>>() {
            @Override
            public void onSuccess(Api<DefualtResponse> defualtResponseApi) {
                super.onSuccess(defualtResponseApi);
                hideLoading();
                successIntent();
            }

            @Override
            public void onFail(RequestException e) {
                super.onFail(e);
                hideLoading();
                G.toast(Utils.getString(R.string.pls_try_again));
            }
        });
    }

    private void openDialogDatePicker() {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                this,
                now.get(Calendar.YEAR), // Initial year selection
                now.get(Calendar.MONTH), // Initial month selection
                now.get(Calendar.DAY_OF_MONTH) // Inital day selection
                                                           );
        dpd.show(getSupportFragmentManager(), "Datepickerdialog");
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String startTime = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
        edtDate.setText(startTime);
        edtDate.setError(null);
    }

    private boolean check(EditText... editTexts) {
        for (EditText editText : editTexts) {
            if (editText.getText() != null) {
                if (editText.getText().length() == 0) {
                    editText.setError(Utils.getString(R.string.not_empty));
                    return true;
                }
            }
        }
        return false;
    }

    private void startPickImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Complete action using"), PICK_FROM_FILE);

    }

    String imagePath;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE:
                    CropImage.ActivityResult result = CropImage.getActivityResult(data);
                    Uri resultUri = result.getUri();
                    imagePath = resultUri.getPath();
                    imgProfile.setImageURI(resultUri);
                    break;
                case PICK_FROM_FILE:
                    String path = Utils.getPath(data.getData());
                    if (path != null) {
                        File file = new File(path);
                        Uri uri = Uri.fromFile(file);
                        startCropImageActivity(uri);
                    }
                    break;
            }
        }
    }

    private void startCropImageActivity(Uri mImageCaptureUri) {
        CropImage.activity(mImageCaptureUri)
                 .setAutoZoomEnabled(true)
                 .setAspectRatio(1, 1)
                 .setGuidelines(CropImageView.Guidelines.ON)
                 .start(this);
    }
}
