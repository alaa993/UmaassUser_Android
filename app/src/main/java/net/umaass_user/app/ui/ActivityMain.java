package net.umaass_user.app.ui;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;

import net.umaass_user.app.R;
import net.umaass_user.app.application.G;
import net.umaass_user.app.application.Preference;
import net.umaass_user.app.ui.base.BaseActivity;
import net.umaass_user.app.ui.base.BaseFragment;
import net.umaass_user.app.ui.login.Authentication;
import net.umaass_user.app.utils.NotificationCenter;
import net.umaass_user.app.utils.Utils;
import net.umaass_user.app.utils.backStackManager.FragNavController;
import net.umaass_user.app.utils.backStackManager.FragmentHistory;
import net.umaass_user.app.utils.permission.helper.PermissionHelper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;

public class ActivityMain extends BaseActivity implements
                                               BaseFragment.FragmentNavigation,
                                               FragNavController.TransactionListener,
                                               AHBottomNavigation.OnTabSelectedListener,
                                               FragNavController.RootFragmentListener,
                                               NotificationCenter.NotificationCenterDelegate {
    String[] TABS;
    private AHBottomNavigation bottomNavigation;
    private FragNavController mNavController;
    private FragmentHistory fragmentHistory;
    private int[] mTabIconsSelected = {
            R.drawable.ic_home_black_24dp,
            R.drawable.ic_map_black_24dp,
            R.drawable.ic_person_black_24dp};

    CheckVersion checkVersion = new CheckVersion();
    private String latestVersion = "1.0";
    private String currentVersion = "1.0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        readView();
        functionView();

        fragmentHistory = new FragmentHistory();

        mNavController = FragNavController.newBuilder(savedInstanceState, getSupportFragmentManager(), R.id.root)
                                          .transactionListener(this)
                                          .rootFragmentListener(this, TABS.length)
                                          .build();
        checkVersion.execute();
    }

    @Override
    public void readView() {
        super.readView();
        bottomNavigation = findViewById(R.id.bottom_navigation);
        AdView  mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    @Override
    public void functionView() {
        super.functionView();
        setupBottomNav();
    }

    private void setupBottomNav() {

        TABS = getResources().getStringArray(R.array.tab_name);
        for (int i = 0; i < TABS.length; i++) {
            bottomNavigation.addItem(new AHBottomNavigationItem(TABS[i], mTabIconsSelected[i]));
        }
        bottomNavigation.setUseElevation(true);
        bottomNavigation.setDefaultBackgroundColor(Utils.getColor(R.color.card_color));
        bottomNavigation.setInactiveColor(Utils.getColor(R.color.icon_color));
        bottomNavigation.setAccentColor(Utils.getColor(R.color.colorPrimary));
        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
        bottomNavigation.setColoredModeColors(Utils.getColor(R.color.white), Color.BLACK);
        bottomNavigation.setBehaviorTranslationEnabled(false);
        bottomNavigation.setOnTabSelectedListener(this);
    }

    private void switchTab(int position) {
        mNavController.switchTab(position);
    }

    @Override
    public void pushFragment(Fragment fragment) {
        if (mNavController != null) {
            mNavController.pushFragment(fragment);
        }
    }

    @Override
    public Fragment getRootFragment(int index) {
        switch (index) {
            case FragNavController.TAB1:
                return new FragmentHome();
            case FragNavController.TAB2:
                if (Preference.isLogin()) {
                    return new FragmentMap();
                } else {
                    login();
                }
            case FragNavController.TAB3:
                if (Preference.isLogin()) {
                    return new FragmentProfile();
                } else {
                    login();
                }
        }
        throw new IllegalStateException("Need to send an index that we know");
    }

    @Override
    public void onTabTransaction(Fragment fragment, int index) {
        BaseFragment baseFragment = null;
        if (fragment instanceof BaseFragment) {
            baseFragment = (BaseFragment) fragment;
            baseFragment.onPop();
        }
    }

    private void login() {
        startActivity(new Intent(ActivityMain.this, ActivityWelcome.class));
    }

    @Override
    public void onFragmentTransaction(Fragment fragment, FragNavController.TransactionType transactionType) {
        BaseFragment baseFragment = null;
        if (fragment instanceof BaseFragment) {
            baseFragment = (BaseFragment) fragment;
        }
        switch (transactionType) {
            case POP:
                if (baseFragment != null) {
                    baseFragment.onPop();
                }
                break;
            case REPLACE:
                break;
            case PUSH:
                if (baseFragment != null) {
                    baseFragment.onPush();
                }

                break;
        }
    }

    @Override
    public boolean onTabSelected(int position, boolean wasSelected) {
        if (wasSelected) {
            mNavController.clearStack();
            switchTab(position);
        } else {
            fragmentHistory.push(position);
            switchTab(position);
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        if (mNavController.getCurrentFrag() instanceof BaseFragment) {
            BaseFragment baseFragment = (BaseFragment) mNavController.getCurrentFrag();
            if (baseFragment.backPress()) {
                checkBack();
            }
        } else {
            checkBack();
        }
    }

    private void checkBack() {
        if (!mNavController.isRootFragment()) {
            mNavController.popFragment();
            BaseFragment baseFragment = (BaseFragment) mNavController.getCurrentFrag();
            baseFragment.backPress();
        } else {
            if (fragmentHistory.isEmpty()) {
                super.onBackPressed();
            } else {
                if (fragmentHistory.getStackSize() > 1) {
                    int position = fragmentHistory.popPrevious();
                    switchTab(position);
                    updateTabSelection(position);
                } else {
                    switchTab(TABS.length - 1);
                    updateTabSelection(TABS.length - 1);
                    fragmentHistory.emptyStack();
                }
            }

        }
    }

    private void updateTabSelection(int currentTab) {
        bottomNavigation.setOnTabSelectedListener(null);
        bottomNavigation.setCurrentItem(currentTab);
        bottomNavigation.setOnTabSelectedListener(this);
    }

    private void init() {
        PermissionHelper.requestLocation(new PermissionHelper.OnPermissionGrantedListener() {
            @Override
            public void onPermissionGranted() {
                if (SmartLocation.with(ActivityMain.this).location().state().locationServicesEnabled()) {
                    SmartLocation.with(ActivityMain.this).location().state().isAnyProviderAvailable();
                }
                if (SmartLocation.with(ActivityMain.this).location().state().isGpsAvailable()) {
                    SmartLocation.with(ActivityMain.this)
                                 .location()
                                 .oneFix()
                                 .start(new OnLocationUpdatedListener() {
                                     @Override
                                     public void onLocationUpdated(Location location) {
                                         NotificationCenter.getInstance().postNotificationName(NotificationCenter.ChangeLocation, location);
                                     }
                                 });
                } else {
                    buildAlertMessageNoGps();
                }

            }
        });


    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(Utils.getString(R.string.gps_off_message))
               .setCancelable(false)
               .setPositiveButton(Utils.getString(R.string.yes), new DialogInterface.OnClickListener() {
                   public void onClick(final DialogInterface dialog, final int id) {
                       //  startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                       enableLocationSettings();
                   }
               })
               .setNegativeButton(Utils.getString(R.string.no), new DialogInterface.OnClickListener() {
                   public void onClick(final DialogInterface dialog, final int id) {
                       dialog.cancel();
                   }
               });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    protected void enableLocationSettings() {
        LocationRequest locationRequest = LocationRequest.create()
                                                         .setInterval(1000)
                                                         .setFastestInterval(1000)
                                                         .setNumUpdates(1)
                                                         .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        LocationServices
                .getSettingsClient(this)
                .checkLocationSettings(builder.build())
                .addOnSuccessListener(this, (LocationSettingsResponse response) -> {
                    // startUpdatingLocation(...);
                })
                .addOnFailureListener(this, ex -> {
                    if (ex instanceof ResolvableApiException) {
                        // Location settings are NOT satisfied,  but this can be fixed  by showing the user a dialog.
                        try {
                            // Show the dialog by calling startResolutionForResult(),  and check the result in onActivityResult().
                            ResolvableApiException resolvable = (ResolvableApiException) ex;
                            resolvable.startResolutionForResult(ActivityMain.this, 100);
                        } catch (IntentSender.SendIntentException sendEx) {
                            // Ignore the error.
                        }
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (100 == requestCode) {
            if (Activity.RESULT_OK == resultCode) {
                //user clicked OK, you can startUpdatingLocation(...);
                init();
            } else {
                G.toast(Utils.getString(R.string.gps_is_off));
                NotificationCenter.getInstance().addObserver(this, NotificationCenter.StopLocation);
                //user clicked cancel: informUserImportanceOfLocationAndPresentRequestAgain();
            }
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.StopLocation);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.StartLocation);
    }

    @Override
    protected void onPause() {
        super.onPause();
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.StopLocation);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.StartLocation);
    }

    @Override
    public void didReceivedNotification(int id, Object... args) {
        if (id == NotificationCenter.StartLocation) {
            init();
        } else if (id == NotificationCenter.StopLocation) {
            SmartLocation.with(this).location().stop();
        }
    }


    public class CheckVersion extends AsyncTask<String, String, String> {
        private String newVersion;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }
        @Override
        protected String doInBackground(String... strings) {

            try {
                Document document = Jsoup.connect("https://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName())
                        .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                        .referrer("https://www.google.com")
                        .get();
                if (document != null) {
                    Log.d("VersionChecker", "document");
                    Elements element = document.getElementsContainingOwnText("Current Version");
                    for (Element ele : element) {
                        if (ele.siblingElements() != null){
                            Elements sibElements = ele.siblingElements();
                            for (Element sibElement : sibElements) {
                                Log.d("VersionChecker", "sibElements" + "------>" + sibElement.text());
                                newVersion = sibElement.text();
                            }

                        }

                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return newVersion;
        }
        @Override
        protected void onPostExecute(String newVersion) {
            super.onPostExecute(newVersion);
            latestVersion = newVersion;
            compareVersion();

        }
    }

    @SuppressLint("ResourceAsColor")
    private void compareVersion() {
        try {
            PackageInfo pInfo = this.getPackageManager().getPackageInfo(this.getPackageName(),0);
            currentVersion = pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (versionCompare(latestVersion, currentVersion) >= 1) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(getString(R.string.warning));
            builder.setCancelable(false);
            builder.setMessage(getString(R.string.new_version_available));
            builder.setPositiveButton("UPDATE", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse("https://play.google.com/store/apps/details?id=" +getApplicationContext().getPackageName()));
                    startActivity(i);
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();
            // dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(R.color.menu_color);


        }
    }

    int versionCompare(String str1 ,String str2 ) {
        Log.d("VersionCompare", "======>" + str1 + "======>" + str2);
        if (str1.equals(str2)) {
            return -1;
        } else {
            String[] vals1 = str1.split("\\.");
            String[] vals2 = str2.split("\\.");
            int i = 0;
            while (i < vals1.length && i < vals2.length && vals1[i].equals(vals2[i])) {
                i++;
            }
            if (i < vals1.length && i < vals2.length) {
                int diff = Integer.valueOf(vals1[i]).compareTo(Integer.valueOf(vals2[i]));
                return Integer.signum(diff);
            }
            return Integer.signum(vals1.length - vals2.length);
        }
    }
}
