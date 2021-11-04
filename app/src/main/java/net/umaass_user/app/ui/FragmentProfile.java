package net.umaass_user.app.ui;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ShareCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.squareup.picasso.Picasso;

import net.umaass_user.app.R;
import net.umaass_user.app.application.G;
import net.umaass_user.app.application.Preference;
import net.umaass_user.app.data.Repository;
import net.umaass_user.app.data.interfaces.CallBack;
import net.umaass_user.app.data.remote.models.Api;
import net.umaass_user.app.data.remote.models.Avatar;
import net.umaass_user.app.data.remote.models.DefualtResponse;
import net.umaass_user.app.data.remote.models.NotificationsModel;
import net.umaass_user.app.data.remote.models.Profile;
import net.umaass_user.app.data.remote.models.Provider;
import net.umaass_user.app.data.remote.utils.RequestException;
import net.umaass_user.app.interfac.ItemClickListener;
import net.umaass_user.app.models.ChangeLanguageModel;
import net.umaass_user.app.ui.adapter.AdapterDoctor;
import net.umaass_user.app.ui.base.BaseFragment;
import net.umaass_user.app.ui.components.EmptyView;
import net.umaass_user.app.utils.CircleImageView;
import net.umaass_user.app.utils.EndlessRecyclerOnScrollListener;
import net.umaass_user.app.utils.LocaleUtils;
import net.umaass_user.app.utils.Utils;
import net.umaass_user.app.utils.permission.ActivityUtils;

import java.util.ArrayList;
import java.util.List;

public class FragmentProfile extends BaseFragment {

    private AdapterDoctor adapterDoctor;
    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private EmptyView emptyView;
    private SwipeRefreshLayout swipe;
    private TextView txtName;
    private TextView txtPhone;
    private TextView txtEmail;
    private CircleImageView imgProfile;
    private TextView btnAboutUs;
    private TextView btnContactUs;
    private TextView btnSetting;
    private TextView btnLogout;
    private TextView btnLanguage;
    private TextView btnShare;
    private TextView tvCount;
    private TextView btnNotify;

    @Override
    public int getViewLayout() {
        return R.layout.fragment_profile;
    }

    @Override
    public void readView() {
        super.readView();
        recyclerView = baseView.findViewById(R.id.recyclerView);
        toolbar = baseView.findViewById(R.id.toolbar);
        emptyView = baseView.findViewById(R.id.emptyView);
        swipe = baseView.findViewById(R.id.swipe);
        txtName = baseView.findViewById(R.id.txtName);
        txtPhone = baseView.findViewById(R.id.txtPhone);
        imgProfile = baseView.findViewById(R.id.imgProfile);
        txtEmail = baseView.findViewById(R.id.txtEmail);

        btnSetting = baseView.findViewById(R.id.btnSetting);
        btnAboutUs = baseView.findViewById(R.id.btnAboutUs);
        btnLogout = baseView.findViewById(R.id.btnLogout);
        btnContactUs = baseView.findViewById(R.id.btnContactUs);
        btnLanguage = baseView.findViewById(R.id.btnLanguage);
        btnShare = baseView.findViewById(R.id.btnShare);
        tvCount = baseView.findViewById(R.id.tvCount);
        btnNotify = baseView.findViewById(R.id.btnNotifications);

    }


    @Override
    public void functionView() {
        super.functionView();

        txtName.setText(Preference.getFirstName());
        txtPhone.setText(Preference.getPhone());
        txtEmail.setText(Preference.getEmail());
        Picasso.get()
               .load(Preference.getImage())
               .placeholder(R.drawable.profile)
               .error(R.drawable.profile)
               .into(imgProfile);

        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(getActivity(), ActivityEditProfile.class), 100);
            }
        });
        btnAboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mfragmentNavigation != null) {
                    mfragmentNavigation.pushFragment(new FragmentAboutUs());
                }
            }
        });

        btnNotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),ActivityNotifications.class);
                startActivity(intent);
            }
        });

        btnContactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mfragmentNavigation != null) {
                    mfragmentNavigation.pushFragment(new FragmentContactUs());
                }
            }
        });

        btnLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLanguageDialog();
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog();
            }
        });
        adapterDoctor = new AdapterDoctor();
        adapterDoctor.setListener(new ItemClickListener<Provider>() {
            @Override
            public void onClick(Provider item) {
                Intent intent = new Intent(getActivity(), ActivityProviderDetail.class);
                intent.putExtra("id", String.valueOf(item.getId()));
                startActivity(intent);
            }
        });
        adapterDoctor.setBookListener(new ItemClickListener<Provider>() {
            @Override
            public void onClick(Provider item) {
                Intent intent = new Intent(getActivity(), ActivityBook.class);
                intent.putExtra("idProvider", String.valueOf(item.getId()));
                intent.putExtra("idIndustry", String.valueOf(item.getIndustryId()));
                startActivity(intent);
            }
        });
        adapterDoctor.setFavListener(new ItemClickListener<Provider>() {
            @Override
            public void onClick(Provider item) {
                changeFav(item);
            }
        });
        LinearLayoutManager doctorManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(doctorManager);
        recyclerView.setAdapter(adapterDoctor);
        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(doctorManager) {
            @Override
            public void onLoadMore(int currentPage) {
                getData(currentPage);
            }
        });
        getData(1);
        getUnreadData();

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData(1);
                getUnreadData();
            }
        });
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                share();
            }
        });
        getProfile();
        getUnreadData();
    }
    private void share() {
        try {
            Activity activity = ActivityUtils.getTopActivity();
            if (activity == null) {
                return;
            }
            StringBuilder builder = new StringBuilder();
            builder.append("Check out " + Utils.getString(R.string.app_name) + " at:\n\n");
            builder.append("http://play.google.com/store/apps/details?id=" + activity.getPackageName());
            ShareCompat.IntentBuilder.from(activity)
                                     .setType("text/plain")
                                     .setChooserTitle("Share to")
                                     .setText(builder)
                                     .startChooser();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getUnreadData() {

        Repository.getInstance().getUnreadNotifications(new CallBack<Api<List<NotificationsModel>>>() {
            @Override
            public void onSuccess(Api<List<NotificationsModel>> listApi) {
                super.onSuccess(listApi);
                List<NotificationsModel> lists = new ArrayList<>();
                for (int i = 0; i < listApi.getData().size(); i++) {
                    if (listApi.getData().get(i).getApp().equals("Customer")){
                        lists.add(listApi.getData().get(i));
                    }
                }
                tvCount.setText(lists.size() + "");
            }

            @Override
            public void onFail(RequestException e) {
                super.onFail(e);
            }
        });
    }


    private void showLanguageDialog() {

        final String[] selectedLanguage = {"English"};
        String[] arrayServiceTypes;
        arrayServiceTypes = new String[4];
        arrayServiceTypes[0] = "English";
        arrayServiceTypes[1] = "عربى";
        arrayServiceTypes[2] = "Kurdî";
        arrayServiceTypes[3] = "Türkçesi";

        AlertDialog.Builder materialBuilder = new AlertDialog.Builder(ActivityUtils.getTopActivity());
        materialBuilder.setSingleChoiceItems(arrayServiceTypes, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                selectedLanguage[0] = arrayServiceTypes[i];
                if (selectedLanguage[0].equals("English")) {
                    Preference.setLanguage("en");
                    Preference.setLanguageDefalt("en");
                }
                if (selectedLanguage[0].equals("عربى")) {
                    Preference.setLanguage("ar");
                    Preference.setLanguageDefalt("ar");
                }
                if (selectedLanguage[0].equals("Kurdî")) {
                    Preference.setLanguage("ku");
                    Preference.setLanguageDefalt("ku");
                }
                if (selectedLanguage[0].equals("Türkçesi")) {
                    Preference.setLanguage("tr");
                    Preference.setLanguageDefalt("tr");
                }
                String androidId = Settings.Secure.getString(G.getInstance().getContentResolver(), Settings.Secure.ANDROID_ID);

                ChangeLanguageModel changeLanguageModel = new ChangeLanguageModel();
                changeLanguageModel.setLanguage(Preference.getLanguage().toUpperCase());
                changeLanguageModel.setDevice_id("customer_" + androidId);
                Repository.getInstance()
                        .changeLanguage(changeLanguageModel, new CallBack<Api<DefualtResponse>>() {
                            @Override
                            public void onSuccess(Api<DefualtResponse> api) {
                                super.onSuccess(api);

                            }

                            @Override
                            public void onFail(RequestException e) {
                                super.onFail(e);

                            }
                        });
                LocaleUtils.setLocale(getContext(), Preference.getLanguage());
                dialogInterface.dismiss();
                if (getActivity() != null) {
                    Intent intent = new Intent(getActivity(), ActivityMain.class);
                    startActivity(intent);
                    getActivity().finish();
                }
            }
        })
                .setTitle(Utils.getString(R.string.choose_language))
                .setCancelable(true)
                .show();

    }

    private void changeFav(Provider item) {
        CallBack<Api<DefualtResponse>> apiCallBack = new CallBack<Api<DefualtResponse>>() {
            @Override
            public void onSuccess(Api<DefualtResponse> defualtResponseApi) {
                super.onSuccess(defualtResponseApi);
                adapterDoctor.removeItem(item);
                if (adapterDoctor.getItemCount() == 0) {
                    emptyView.setVisibility(View.VISIBLE);
                }
                G.changeFav = true;
            }

            @Override
            public void onFail(RequestException e) {
                super.onFail(e);
            }
        };
        Repository.getInstance().unFavProvider(String.valueOf(item.getId()), apiCallBack);
    }

    private void getData(int page) {
        if (swipe != null && page == 1) {
            swipe.setRefreshing(true);
            adapterDoctor.clearAndPut(null);
        }
        emptyView.setVisibility(View.INVISIBLE);
        Repository.getInstance().getAllProvider(Preference.getCurrentCategoryId(), "true",
                                                null, null, null, null, null,null, page, new CallBack<Api<List<Provider>>>() {
                    @Override
                    public void onSuccess(Api<List<Provider>> listApi) {
                        super.onSuccess(listApi);
                        swipe.setRefreshing(false);
                        if (listApi.getData() != null && listApi.getData().size() > 0) {
                            adapterDoctor.put(listApi.getData());
                        }
                        if (adapterDoctor.getItemCount() == 0) {
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


    private void getProfile() {
        Repository.getInstance().getProfile(new CallBack<Api<Profile>>() {
            @Override
            public void onSuccess(Api<Profile> profileApi) {
                super.onSuccess(profileApi);

                Profile profile = profileApi.getData();
                txtName.setText(profile.getName());
                txtPhone.setText(profile.getPhone());
                Avatar avatar = profile.getAvatar();
                if (avatar != null) {
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
    public void onPop() {
        super.onPop();
        if (G.changeProfile) {
            G.changeProfile = false;
            getProfile();
        }
        if (G.changeFav) {
            G.changeFav = false;
            getData(1);
        }
    }

    private void openPopUp(View view) {
        PopupMenu menu = new PopupMenu(ActivityUtils.getTopActivity(), view);
        menu.getMenu().add(1, 1, 1, R.string.log_out);
        menu.getMenu().add(1, 2, 2, R.string.edit_profile);
        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem i) {
                switch (i.getItemId()) {
                    case 1:
                        dialog();
                        break;
                    case 2:
                        startActivity(new Intent(ActivityUtils.getTopActivity(), ActivityEditProfile.class));
                        break;
                }
                return false;
            }
        });
        menu.show();
    }

    private void dialog() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(ActivityUtils.getTopActivity());
        builder1.setMessage(Utils.getString(R.string.are_you_sure_for_exit));
        builder1.setCancelable(true);
        builder1.setPositiveButton(
                Utils.getString(R.string.yes),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        logOut();
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

    private void logOut() {
        Preference.logOut();
        startActivity(new Intent(ActivityUtils.getTopActivity(), ActivityWelcome.class));
        if (getActivity() != null) {
            getActivity().finish();
        }
    }
}
