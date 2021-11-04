package net.umaass_user.app.ui;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import net.umaass_user.app.R;
import net.umaass_user.app.application.G;
import net.umaass_user.app.application.Preference;
import net.umaass_user.app.data.Repository;
import net.umaass_user.app.data.interfaces.CallBack;
import net.umaass_user.app.data.remote.models.Api;
import net.umaass_user.app.data.remote.models.Avatar;
import net.umaass_user.app.data.remote.models.DefualtResponse;
import net.umaass_user.app.data.remote.models.Profile;
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


public class ActivityEditProfile extends BaseActivity implements DatePickerDialog.OnDateSetListener {

    EditText edtFirstName;
    EditText edtEmail;
    EditText edtDesc;

    RoundCornerButton btnDone;
    CircleImageView imgProfile;
    EditText edtDate;
    EditText edtGender;
    private String genderId;

    private static final int PICK_FROM_FILE = 3;
    private static final int CHANGE_MOBILE_NUMBER = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        readView();
        functionView();
        initViewModel();
    }

    @Override
    public void readView() {
        super.readView();
        edtFirstName = findViewById(R.id.edtFirstName);
        edtEmail = findViewById(R.id.edtEmail);
        edtDesc = findViewById(R.id.edtDesc);
        imgProfile = findViewById(R.id.imgProfile);
        btnDone = findViewById(R.id.btnDone);

        edtDate = findViewById(R.id.edtDate);
        edtGender = findViewById(R.id.edtGender);

        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    @Override
    public void functionView() {
        super.functionView();

        edtDate.setVisibility(View.GONE);
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
                update();
            }
        });
        edtDesc.setVisibility(View.GONE);
        getProfile();
    }

    private void getProfile() {
        showLoading();
        Repository.getInstance().getProfile(new CallBack<Api<Profile>>() {
            @Override
            public void onSuccess(Api<Profile> profileApi) {
                super.onSuccess(profileApi);
                hideLoading();
                Profile profile = profileApi.getData();
                edtEmail.setText(profile.getEmail());
                edtDesc.setText(profile.getDescription());
                edtFirstName.setText(profile.getName());
                edtDate.setText(profile.getBirthdate());
                edtGender.setText(profile.getGender() == 0 ? Utils.getString(R.string.female) : Utils.getString(R.string.male));
                genderId = String.valueOf(profile.getGender());
                Avatar avatar = profile.getAvatar();
                if (avatar != null) {
                    Picasso.get()
                            .load(avatar.getUrlMd() == null || avatar.getUrlMd().isEmpty() ? "no" : avatar.getUrlMd())
                            .placeholder(R.drawable.profile)
                            .error(R.drawable.profile)
                            .into(imgProfile);
                }
            }

            @Override
            public void onFail(RequestException e) {
                super.onFail(e);
                hideLoading();
            }
        });
    }

    private void sendImage() {
        showLoading();
        Repository.getInstance().uploadAvatar(imagePath, new CallBack<Api<DefualtResponse>>() {
            @Override
            public void onSuccess(Api<DefualtResponse> defualtResponseApi) {
                super.onSuccess(defualtResponseApi);
                hideLoading();
                G.toast(Utils.getString(R.string.saved));
                setResult(Activity.RESULT_OK);
                finish();
            }

            @Override
            public void onFail(RequestException e) {
                super.onFail(e);
                hideLoading();
                G.toast(Utils.getString(R.string.pls_try_again));
            }
        });
    }

    private void update() {
        boolean b = check(edtFirstName, edtGender);
        if (b) {
            return;
        }
        String fName = edtFirstName.getText().toString();
        String email = edtEmail.getText().toString();
        String desc = edtDesc.getText().toString();
        String birthDay = edtDate.getText().toString();
        String gender = genderId;
        if (email != null && !email.isEmpty()) {
            if (!Utils.isValidMail(email)) {
                edtEmail.setError(Utils.getString(R.string.not_valid));
                return;
            }
        }
        showLoading();
        Repository.getInstance().updateProfile(fName, email, desc,
                birthDay,
                gender,
                Preference.getLanguage().toUpperCase(),
                null, null, null,
                new CallBack<Api<DefualtResponse>>() {
                    @Override
                    public void onSuccess(Api<DefualtResponse> defualtResponseApi) {
                        super.onSuccess(defualtResponseApi);
                        hideLoading();
                        G.changeProfile = true;
                        if (imagePath != null && !imagePath.isEmpty()) {
                            sendImage();
                        } else {
                            G.toast(Utils.getString(R.string.saved));
                            setResult(Activity.RESULT_OK);
                            finish();
                        }
                    }

                    @Override
                    public void onFail(RequestException e) {
                        super.onFail(e);
                        hideLoading();
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

                case CHANGE_MOBILE_NUMBER:
                    String phone = data.getStringExtra("phone");
                    if (phone != null) {
                        //  mobile = phone;
                        //  edtPhone.setText(mobile);
                    }
                    break;
            }
        }
    }

    private void startCropImageActivity(Uri mImageCaptureUri) {
        CropImage.activity(mImageCaptureUri)
                .setAutoZoomEnabled(true)
                .setRequestedSize(600, 600)
                .setAspectRatio(1, 1)
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(this);
    }
}
