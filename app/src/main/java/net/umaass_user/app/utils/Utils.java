package net.umaass_user.app.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.EditText;

import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import net.umaass_user.app.BuildConfig;
import net.umaass_user.app.R;
import net.umaass_user.app.application.G;
import net.umaass_user.app.utils.permission.ActivityUtils;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.regex.Pattern;


public class Utils {


    public static int dp(float value) {
        if (value == 0) {
            return 0;
        }
        return DpToPix(value);
    }

    public static boolean isEmptyEditText(EditText... editTexts) {
        for (EditText editText : editTexts) {
            if (editText.getText() != null) {
                if (editText.getText().length() == 0) {
                    editText.setError(getString(R.string.not_empty));
                    return true;
                }
            }
        }
        return false;
    }

    public static int getRandomColor(String id) {
        ArrayList<String> colorList = new ArrayList<>();
        colorList.add("#E56555");
        colorList.add("#F28C48");
        colorList.add("#5094D2");
        colorList.add("#8E85EE");
        colorList.add("#F2749A");
        colorList.add("#E58544");
        colorList.add("#76C74D");
        colorList.add("#5FBED5");
        int i = id != null ? id.hashCode() : 0;
        if (i > 0 && i < colorList.size()) {
            return Color.parseColor(colorList.get(i));
        }
        return Color.parseColor(colorList.get(Math.abs(i % colorList.size())));
    }


    public static int DpToPix(float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().getDisplayMetrics());
    }

    public static int SpToPix(float sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, Resources.getSystem().getDisplayMetrics());
    }

    public static int DpToSp(float dp) {
        return (int) (DpToPix(dp) / (float) SpToPix(dp));
    }

    public static String stringCheck(String string) {
        return string != null && !string.equals("null") && !string.equals("") ? string : null;
    }

    public static boolean isListEquals(List<?> l1, List<?> l2) {
        return l1.size() == l2.size() && l1.containsAll(l2) && l2.containsAll(l1);
    }


    public static String toPersianNumber(String string) {
        return net.umaass_user.app.utils.FormatHelper.toPersianNumber(string);
    }

    public static String toEnglishNumber(String string) {
        return net.umaass_user.app.utils.FormatHelper.toEnglishNumber(string);
    }

    public static void logMap(String tag, Map<String, String> params) {
        if (params == null) {
            return;
        }
        for (String key : params.keySet()) {
            String value = params.get(key);
            G.log(tag, key + "=" + value);
        }
    }

    public static void logMap(String tag, ContentValues params) {
        if (params == null) {
            return;
        }
        for (String key : params.keySet()) {
            String value = params.get(key) + "";
            G.log(tag, key + "=" + value);
        }
    }

    public static double raghamAshar(int tedadAshar, double percet) {
        double darsadTakhfif = Double.parseDouble(String.format(Locale.ENGLISH, "%." + tedadAshar + "f", percet));
        return !Double.isNaN(darsadTakhfif) ? darsadTakhfif : 0.00;
    }

    public static String getFileSize(long size) {
        if (size <= 0)
            return "0";
        final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }

    public static void setFillWindowAndTransparetStatusBar(Activity activity) {

        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
        }*/

        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            setWindowFlag(activity, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(activity, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }


    public static void setWindowFlag(Activity activity, final int bits, boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    public static int getRandomColor() {
        Random rnd = new Random();
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }

    public static int getPercent(long downlodedFile, long totalBytes) {
        return (int) (((Double.longBitsToDouble(downlodedFile) / Double.longBitsToDouble(totalBytes))) * 100);
    }

    public static void runOnUIThread(Runnable runnable) {
        runOnUIThread(runnable, 0);
    }


    public static void main(String[] args) {

    }

    public static int getColor(int color) {
        return ContextCompat.getColor(G.getInstance().getApplicationContext(), color);
    }

    public static String getString(int id) {
        return ActivityUtils.getTopActivity().getResources().getString(id);
    }

    public static float getDimPix(int dim) {
        return G.getInstance().getApplicationContext().getResources().getDimensionPixelSize(dim);
    }

    public static float getDim(int dim) {
        return G.getInstance().getApplicationContext().getResources().getDimension(dim);
    }

    public static void runOnUIThread(Runnable runnable, long delay) {
        if (delay == 0) {
            G.HANDLER.post(runnable);
        } else {
            G.HANDLER.postDelayed(runnable, delay);
        }
    }


    public static boolean isTablet() {
        int screenLayout = Resources.getSystem().getConfiguration().screenLayout;
        screenLayout &= Configuration.SCREENLAYOUT_SIZE_MASK;
        switch (screenLayout) {
            case Configuration.SCREENLAYOUT_SIZE_SMALL:
                return false;
            case Configuration.SCREENLAYOUT_SIZE_NORMAL:
                return false;
            case Configuration.SCREENLAYOUT_SIZE_LARGE:
                return true;
            case Configuration.SCREENLAYOUT_SIZE_XLARGE:
                return true;
            default:
                return false;
        }
    }

    public static int getDisplayWidthPx() {
        DisplayMetrics displaymetrics = Resources.getSystem().getDisplayMetrics();
        return displaymetrics.widthPixels;
    }

    public static int getDensity() {
        return Math.max(Utils.dp(Utils.getDisplayWidthPx()) / Utils.getDisplayWidthPx(), 1);
    }

    public static int getDisplayHeightPx() {
        DisplayMetrics displaymetrics = Resources.getSystem().getDisplayMetrics();
        return displaymetrics.heightPixels;
    }


    public static void installApk(Context context, Uri fileUri) {
        File toInstall = new File(fileUri.getPath());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Uri apkUri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider", toInstall);
            Intent intent = new Intent(Intent.ACTION_INSTALL_PACKAGE);
            intent.setData(apkUri);
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } else {
            Uri apkUri = Uri.fromFile(toInstall);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }


    public static String formatToMilitary(long i) {
        return (i <= 9) ? "0" + i : String.valueOf(i);
    }

    public static boolean isValidMobile(String phone) {
        Pattern mobilePattern = Pattern.compile("(\\+98|0)?9\\d{9}");
        return mobilePattern.matcher(phone).matches();
    }

    public static boolean isValidMail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static void openForView(Context context, File file) {


        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Uri uri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider", file);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                String mimType = getMimeType(file.getAbsolutePath());
                intent.setDataAndType(uri, mimType);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                Intent j = Intent.createChooser(intent, "Choose an application to open with:");
                context.startActivity(j);
            } else {
                Uri uri = Uri.fromFile(file);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                String mimType = getMimeType(file.getAbsolutePath());
                intent.setDataAndType(uri, mimType);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Intent j = Intent.createChooser(intent, "Choose an application to open with:");
                context.startActivity(j);
            }
        } catch (Exception e) {
            G.toast("نرم افزاری برای مشاهده فایل پیدا نشد");
        }
    }

    /**
     * alwas check if return value is null
     *
     * @param url
     * @return
     */
    public static String getMimeType(String url) {
        if (url == null) {
            return null;
        }
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if (extension != null && !extension.isEmpty()) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        } else {
            ContentResolver cR = G.getInstance().getContentResolver();
            type = cR.getType(Uri.parse(url));
        }
        if (type == null || type.isEmpty()) {
            String mim = url.substring(url.lastIndexOf(".") + 1, url.length());
            String ss = mim.toLowerCase();
            switch (ss) {
                case "mp3":
                    type = "audio/mpeg";
                    break;
                case "zip":
                    type = "application/zip";
                    break;
                case "jpg":
                    type = "image/jpeg";
                    break;
                case "png":
                    type = "image/png";
                    break;
                case "tiff":
                    type = "image/tif";
                    break;
                case "tif":
                    type = "image/tif";
                    break;
                case "mp4":
                    type = "video/mp4";
                    break;
                case "doc":
                    type = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
                    break;
                case "ppt":
                    type = "application/vnd.ms-powerpoint";
                    break;
                case "xlsx":
                    type = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
                    break;
                case "pdf":
                    type = "application/pdf";
                    break;
                case "txt":
                    type = "text/plain";
                    break;
            }
        }
        return type;
    }

    public static String fullNameFirstLetters(String fullName) {
        if (fullName == null) return "";
        String firstLetters = "";
        String[] fullnameArray = fullName.split(" ");
        int fullnameArrayLength = fullnameArray.length;

        if (fullnameArrayLength == 1) {
            firstLetters = fullnameArray[0].substring(0, 1);
        } else if (fullnameArrayLength >= 2) {
            String s1 = fullnameArray[0].isEmpty() ? "" : fullnameArray[0].substring(0, 1);
            String s2;
            if (fullnameArray[1].isEmpty() && fullnameArrayLength >= 3) {
                s2 = fullnameArray[2].isEmpty() ? "" : fullnameArray[2].substring(0, 1);
            } else {
                s2 = fullnameArray[1].isEmpty() ? "" : fullnameArray[1].substring(0, 1);
            }

            firstLetters = s1 + " " + s2;

        } else {
            firstLetters = " ";
        }
        return firstLetters;
    }


    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                String value = cursor.getString(column_index);
                if (value.startsWith("content://") || !value.startsWith("/") && !value.startsWith("file://")) {
                    return null;
                }
                return value;
            }
        } catch (Exception e) {
            Log.e("d", e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return null;
    }

    @SuppressLint("NewApi")
    public static String getPath(final Uri uri) {
        try {
            final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
            if (isKitKat && DocumentsContract.isDocumentUri(G.getInstance(), uri)) {
                if (isExternalStorageDocument(uri)) {
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];
                    if ("primary".equalsIgnoreCase(type)) {
                        return Environment.getExternalStorageDirectory() + "/" + split[1];
                    }
                } else if (isDownloadsDocument(uri)) {
                    final String id = DocumentsContract.getDocumentId(uri);
                    final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                    return getDataColumn(G.getInstance(), contentUri, null, null);
                } else if (isMediaDocument(uri)) {
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];

                    Uri contentUri = null;
                    switch (type) {
                        case "image":
                            contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                            break;
                        case "video":
                            contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                            break;
                        case "audio":
                            contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                            break;
                    }

                    final String selection = "_id=?";
                    final String[] selectionArgs = new String[]{
                            split[1]
                    };

                    return getDataColumn(G.getInstance(), contentUri, selection, selectionArgs);
                }
            } else if ("content".equalsIgnoreCase(uri.getScheme())) {
                return getDataColumn(G.getInstance(), uri, null, null);
            } else if ("file".equalsIgnoreCase(uri.getScheme())) {
                return uri.getPath();
            }
        } catch (Exception e) {
            Log.e("d", e.getMessage());
        }
        return null;
    }

    public static Locale getCurrentLocale(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return context.getResources().getConfiguration().getLocales().get(0);
        } else {
            //noinspection deprecation
            return context.getResources().getConfiguration().locale;
        }
    }
}
