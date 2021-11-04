package net.umaass_user.app.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import static android.content.Context.MODE_PRIVATE;

/**
 * 1)add your requested permissions in manifest
 * 2) create constructor in class :Permission MyPermissionObject;
 * 3) where do you want use:
 * MyPermissionObject=new Permission(context,Activity,Manifest.permission.WRITE_EXTERNAL_STORAGE, request number);
 * MyPermissionObject.singlePermissionFunction(new Permission.PermissionRequestListener() {
 *
 * @Override public void onGranted() {
 * <p>
 * }
 * @Override public void onDenied() {
 * <p>
 * }
 * });
 * <p>
 * 4) don't forget call onRequestPermissionsResult method in onRequestPermissionsResult in the activity like this:
 * if (MyPermissionObject != null) {permission.onRequestPermissionsResult( requestCode,  permissions,  grantResults); }
 */

public class Permission {

    //===================================== variables =====================================

    public Context ctx;
    private Activity activity;
    private static final String PREFS_NAME = "runtimePreference";
    private String ManifestPermission;
    private int                       MY_PERMISSIONS_REQUEST_CODE;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor  editor;
    public  PermissionRequestListener requestListener;

    //==================================== constructor ====================================
    public Permission(Activity activity, String ManifestPermission, int requestCode) {
        this.ctx = activity.getApplicationContext();
        this.activity = activity;
        this.ManifestPermission = ManifestPermission;
        sharedPref = ctx.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        editor = sharedPref.edit();
        this.MY_PERMISSIONS_REQUEST_CODE = requestCode;
    }

    //===================================== main method ===================================
    public void singlePermissionFunction(PermissionRequestListener requestListener) {
        this.requestListener = requestListener;
        if (checkHasPermission()) {
            requestListener.onGranted();
        } else {
            //------------------------
            if (checkShouldShowRequestPermissionRationale()) {
                showPermissionExplanation();
            } else if (!checkPermissionPreference(ManifestPermission)) {
                requestPermission();
                updatePermissionPreference(ManifestPermission);
            } else {
                showPermissionExplanationForDoNotASkAgain();
            }
            //------------------------
        }
    }//PermissionFunction

    //======================================= check Permission ==============================
    private boolean checkHasPermission() {
        return ContextCompat.checkSelfPermission(ctx, ManifestPermission) == PackageManager.PERMISSION_GRANTED; //check the Permission

    }//check Permission

    private void requestPermission() {

        ActivityCompat.requestPermissions(activity,
                                          new String[]{ManifestPermission},
                                          MY_PERMISSIONS_REQUEST_CODE);

    }//request Permission

    private boolean checkShouldShowRequestPermissionRationale() {
        /*
         To help find situations where the user might need an explanation, Android provides a utiltity method,
          shouldShowRequestPermissionRationale(). This method returns true if the app has requested this permission previously and
          the user denied the request.
          Note: If the user turned down the permission request in the past and chose the Don't ask again option in
          the permission request system dialog, this method returns false. The method also returns false if
          a device policy prohibits the app from having that permission.
         */
        return ActivityCompat.shouldShowRequestPermissionRationale
                (activity, ManifestPermission);
    }

    private void showPermissionExplanation() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setMessage("این برنامه نیاز به مجوز ها دارد لطفا تایید کنید.");
        builder.setTitle("مجوز ها");

        builder.setPositiveButton("باشه", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                requestPermission();
            }
        });
        builder.setNegativeButton("خیر", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }//showPermissionExplanation

    private void showPermissionExplanationForDoNotASkAgain() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setMessage(" برنامه برای ادامه فعالیت خود نیاز به برخی مجوز ها دارد. در ادامه به بخش تنظیمات برنامه، بخش مجوز ها رفته و مجوز ها را فعال کنید.");
        builder.setTitle("مجوز ها");

        builder.setPositiveButton("باشه", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //============
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", ctx.getPackageName(), null);
                intent.setData(uri);
                ctx.startActivity(intent);
                //==========
            }
        });
        builder.setNegativeButton("خیر", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }//

    //=====================================================================================
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_CODE && permissions[0].equals(ManifestPermission)) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                requestListener.onGranted();
            } else {
                requestListener.onDenied();
            }
        }//

    }

    //===================================== shared preferences =============================
    private void updatePermissionPreference(String key) {

        editor.putBoolean(key, true);
        editor.commit();

    }//update Permission

    private boolean checkPermissionPreference(String key) {
        return sharedPref.getBoolean(key, false);
        //second parameter of getBoolean() is for Value to return if this preference does not exist.
        //This value may be null.
    }//check Permission

    //================================= interface =========================================
    public interface PermissionRequestListener {
        void onGranted();

        void onDenied();
    }
    //====================================== END CLASS =======================================
}//end class
