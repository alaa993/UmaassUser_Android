package net.umaass_user.app.utils.permission.helper;


import java.util.List;

import net.umaass_user.app.utils.permission.LogUtils;
import net.umaass_user.app.utils.permission.PermissionConstants;
import net.umaass_user.app.utils.permission.PermissionUtils;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2018/01/06
 *     desc  : helper about permission
 * </pre>
 */
public class PermissionHelper {

    public static void requestCamera(final OnPermissionGrantedListener listener) {
        request(listener, PermissionConstants.CAMERA);
    }

    public static void requestMicrophone(final OnPermissionGrantedListener listener) {
        request(listener, PermissionConstants.MICROPHONE);
    }

    public static void requestMicrophoneAndStorage(final OnPermissionGrantedListener listener) {
        request(listener, PermissionConstants.MICROPHONE, PermissionConstants.STORAGE);
    }

    public static void requestCameraAndStorage(final OnPermissionGrantedListener listener) {
        request(listener, PermissionConstants.CAMERA, PermissionConstants.STORAGE);
    }

    public static void requestStorage(final OnPermissionGrantedListener listener) {
        request(listener, PermissionConstants.STORAGE);
    }  public static void requestStorage(final OnPermissionGrantedListener listener,final OnPermissionDeniedListener deniedListener) {
        request(listener,deniedListener, PermissionConstants.STORAGE);
    }

    public static void requestStorageAndContact(final OnPermissionGrantedListener listener) {
        request(listener, PermissionConstants.STORAGE, PermissionConstants.CONTACTS);
    }

    public static void requestPhone(final OnPermissionGrantedListener listener) {
        request(listener, PermissionConstants.PHONE);
    }


    public static void requestCallAndSms(final OnPermissionGrantedListener listener) {
        request(listener, PermissionConstants.PHONE, PermissionConstants.SMS);
    }

    public static void requestPhone(final OnPermissionGrantedListener grantedListener,
                                    final OnPermissionDeniedListener deniedListener) {
        request(grantedListener, deniedListener, PermissionConstants.PHONE);
    }

    public static void requestSms(final OnPermissionGrantedListener listener) {
        request(listener, PermissionConstants.SMS);
    }

    public static void requestContact(final OnPermissionGrantedListener listener) {
        request(listener, PermissionConstants.CONTACTS);
    }

    public static void requestLocation(final OnPermissionGrantedListener listener) {
        request(listener, PermissionConstants.LOCATION);
    }

    private static void request(final OnPermissionGrantedListener grantedListener,
                                final @PermissionConstants.Permission String... permissions) {
        request(grantedListener, null, permissions);
    }

    private static void request(final OnPermissionGrantedListener grantedListener,
                                final OnPermissionDeniedListener deniedListener,
                                final @PermissionConstants.Permission String... permissions) {
        PermissionUtils.permission(permissions)
                .rationale(new PermissionUtils.OnRationaleListener() {
                    @Override
                    public void rationale(ShouldRequest shouldRequest) {
                        net.umaass_user.app.utils.permission.helper.DialogHelper.showRationaleDialog(shouldRequest);
                    }
                })
                .callback(new PermissionUtils.FullCallback() {
                    @Override
                    public void onGranted(List<String> permissionsGranted) {
                        if (grantedListener != null) {
                            grantedListener.onPermissionGranted();
                        }
                        LogUtils.d(permissionsGranted);
                    }

                    @Override
                    public void onDenied(List<String> permissionsDeniedForever, List<String> permissionsDenied) {
                        if (!permissionsDeniedForever.isEmpty()) {
                            net.umaass_user.app.utils.permission.helper.DialogHelper.showOpenAppSettingDialog();
                        }
                        if (deniedListener != null) {
                            deniedListener.onPermissionDenied();
                        }
                        LogUtils.d(permissionsDeniedForever, permissionsDenied);
                    }
                })
                .request();
    }

    public interface OnPermissionGrantedListener {
        void onPermissionGranted();
    }

    public interface OnPermissionDeniedListener {
        void onPermissionDenied();
    }
}
