package net.umaass_user.app.application;


import java.util.UUID;

public class Preference {

    public static boolean isLogin() {
        return getToken() != null;
    }

    public static void setValidLogin(boolean valid) {
        G.sharedPref.edit().putBoolean("isValidLogin", valid).apply();
    }

    public static boolean isValidLogin() {
        return G.sharedPref.getBoolean("isValidLogin", false);
    }

    public static void setIdUser(String idUser) {
        G.sharedPref.edit().putString("idUser", idUser).apply();
    }

    public static void setCurrentCategoryId(String CategoryId) {
        G.sharedPref.edit().putString("CategoryId", CategoryId).apply();
    }

    public static String getCurrentCategoryId() {
        return G.sharedPref.getString("CategoryId", null);
    }

    public static void setCurrentDate(String CurrentDate) {
        G.sharedPref.edit().putString("CurrentDate", CurrentDate).apply();
    }

    public static String getCurrentDate() {
        return G.sharedPref.getString("CurrentDate", null);
    }

    public static void setCurrentTime(String CurrentTime) {
        G.sharedPref.edit().putString("CurrentTime", CurrentTime).apply();
    }

    public static String getCurrentTime() {
        return G.sharedPref.getString("CurrentTime", null);
    }

    public static String getIdUser() {
        return G.sharedPref.getString("idUser", null);
    }

    public static String getPhone() {
        return G.sharedPref.getString("phone", null);
    }

    public static void setToken(String token) {
        G.sharedPref.edit().putString("token", token).apply();
    }

    public static void setActiveIndustryId(String id) {
        G.sharedPref.edit().putString("ActiveIndustry", id).apply();
    }

    public static String getActiveIndustryId() {
        return G.sharedPref.getString("ActiveIndustry", null);
    }

    public static String getEmail() {
        return G.sharedPref.getString("email", null);
    }

    public static String getImage() {
        return G.sharedPref.getString("image", "image");
    }

    public static String getToken() {
        return G.sharedPref.getString("token", null);
    }

    public static String getFirstName() {
        return G.sharedPref.getString("firstName", null);
    }

    public static int getIdLanguage() {
        return G.sharedPref.getInt("idLanguage", 1);
    }

    public static void setFirstName(String firstName) {
        G.sharedPref.edit().putString("firstName", firstName).apply();
    }

    public static void setLastName(String lastName) {
        G.sharedPref.edit().putString("lastName", lastName).apply();
    }

    public static void setImage(String image) {
        G.sharedPref.edit().putString("image", image).apply();
    }


    public static void setEmail(String email) {
        G.sharedPref.edit().putString("email", email).apply();
    }

    public static void setPhone(String phone) {
        G.sharedPref.edit().putString("phone", phone).apply();
    }

    public static void setDeviceId(String deviceId) {
        G.sharedPref.edit().putString("deviceId", deviceId).apply();
    }

    public static void setAcceptRules(boolean rules) {
        G.sharedPref.edit().putBoolean("AcceptRules", rules).apply();
    }

    public static boolean isAcceptRules() {
        return G.sharedPref.getBoolean("AcceptRules", false);
    }

    public static String getDeviceId() {
        String idDevice = G.sharedPref.getString("deviceId", null);
        if (idDevice == null) {
            setDeviceId(UUID.randomUUID().toString());
        }
        return G.sharedPref.getString("deviceId", UUID.randomUUID().toString());
    }


    public static void logOut() {
        G.sharedPref.edit().clear().apply();
    }

    public static void setAge(int age) {
        G.sharedPref.edit().putInt("age", age).apply();
    }

    public static int getAge() {
        return G.sharedPref.getInt("age", 30);
    }

    public static void setGender(int gender) {
        G.sharedPref.edit().putInt("gender", gender).apply();
    }

    public static int getGender() {
        return G.sharedPref.getInt("gender", 1);
    }

    public static void setLanguage(String lan) {
        G.sharedPref.edit().putString("language", lan).apply();
    }

    public static String getLanguageDefalt() {
        return G.sharedPref.getString("LanguageDefalt", "");
    }

    public static void setLanguageDefalt(String lan) {
        G.sharedPref.edit().putString("LanguageDefalt", lan).apply();
    }

    public static String getLanguage() {
        return G.sharedPref.getString("language", "en");
    }
}
