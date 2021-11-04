package net.umaass_user.app.data;


import android.annotation.SuppressLint;
import android.provider.Settings;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import net.umaass_user.app.application.G;
import net.umaass_user.app.application.Preference;
import net.umaass_user.app.data.interfaces.CallBack;
import net.umaass_user.app.data.remote.RemoteRepository;
import net.umaass_user.app.data.remote.models.Api;
import net.umaass_user.app.data.remote.models.Appointment;
import net.umaass_user.app.data.remote.models.AppointmentDetail;
import net.umaass_user.app.data.remote.models.Avatar;
import net.umaass_user.app.data.remote.models.Category;
import net.umaass_user.app.data.remote.models.City;
import net.umaass_user.app.data.remote.models.Comment;
import net.umaass_user.app.data.remote.models.Country;
import net.umaass_user.app.data.remote.models.DefualtResponse;
import net.umaass_user.app.data.remote.models.Industry;
import net.umaass_user.app.data.remote.models.IndustryResult;
import net.umaass_user.app.data.remote.models.Login;
import net.umaass_user.app.data.remote.models.NotificationsModel;
import net.umaass_user.app.data.remote.models.Profile;
import net.umaass_user.app.data.remote.models.Provider;
import net.umaass_user.app.data.remote.models.ProviderDetail;
import net.umaass_user.app.data.remote.models.Register;
import net.umaass_user.app.data.remote.models.ServiceResult;
import net.umaass_user.app.data.remote.models.ShowIndustry;
import net.umaass_user.app.data.remote.models.Slide;
import net.umaass_user.app.data.remote.models.SuggestModel;
import net.umaass_user.app.data.remote.models.Suggestion;
import net.umaass_user.app.data.remote.models.WorkingHoursItem;
import net.umaass_user.app.data.remote.utils.RequestException;
import net.umaass_user.app.models.ChangeLanguageModel;
import net.umaass_user.app.models.NewBook;
import net.umaass_user.app.models.NewIndustry;

import java.util.List;


public class Repository implements RepositoryMethod {

    private static Repository INSTANCE;
    RemoteRepository remoteRepository;

    public static Repository getInstance() {
        if (INSTANCE == null) {
            synchronized (Repository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new Repository();
                }
            }
        }
        return INSTANCE;
    }

    private Repository() {
        this.remoteRepository = RemoteRepository.getInstance();
        sendToken();
    }

    private void sendToken() {
        if (Preference.isLogin()) {
            FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                @Override
                public void onComplete(@NonNull Task<InstanceIdResult> task) {
                    if (task.isSuccessful() && task.getResult() != null) {
                        @SuppressLint("HardwareIds")
                        String androidId = Settings.Secure.getString(G.getInstance().getContentResolver(), Settings.Secure.ANDROID_ID);
                        String token = task.getResult().getToken();
                        updateProfile(Preference.getFirstName(), Preference.getEmail(), null, null,
                                Preference.getGender() + "",
                                Preference.getLanguage().toUpperCase(),
                                "android",
                                "user_" + androidId, token, new CallBack<Api<DefualtResponse>>() {
                                    @Override
                                    public void onSuccess(Api<DefualtResponse> defualtResponseApi) {
                                        super.onSuccess(defualtResponseApi);
                                    }
                                });
                    }
                }
            });
        }
    }

    @Override
    public void getLogin(String access_token, String phoneNumber, CallBack<Api<Login>> callBack) {
        remoteRepository.getLogin(access_token, phoneNumber, new CallBack<Api<Login>>() {
            @Override
            public void onSuccess(Api<Login> loginApi) {
                super.onSuccess(loginApi);
                sendToken();
                if (callBack != null) {
                    callBack.onSuccess(loginApi);
                }
            }

            @Override
            public void onFail(RequestException e) {
                super.onFail(e);
                if (callBack != null) {
                    callBack.onFail(e);
                }
            }
        });
    }

    @Override
    public void register(String access_token, String name, String email, String birthday, String gender, String phoneNumber, CallBack<Api<Register>> callBack) {
        remoteRepository.register(access_token, name, email, birthday, gender, phoneNumber, new CallBack<Api<Register>>() {
            @Override
            public void onSuccess(Api<Register> registerApi) {
                super.onSuccess(registerApi);
                sendToken();
                if (callBack != null) {
                    callBack.onSuccess(registerApi);
                }
            }

            @Override
            public void onFail(RequestException e) {
                super.onFail(e);
                if (callBack != null) {
                    callBack.onFail(e);
                }
            }
        });
    }

    @Override
    public void aboutUs(String lang, CallBack<Api<String>> callBack) {
        remoteRepository.aboutUs(lang, callBack);
    }

    @Override
    public void getAllIndustries(boolean mine, CallBack<Api<List<Industry>>> callBack) {
        remoteRepository.getAllIndustries(mine, callBack);
    }

    @Override
    public void getAllCategories(String lang, CallBack<Api<List<Category>>> callBack) {
        remoteRepository.getAllCategories(lang, callBack);
    }

    @Override
    public void getAllAppointments(int page_number, String status, String start_date, String end_date, CallBack<Api<List<Appointment>>> callBack) {
        remoteRepository.getAllAppointments(page_number, status, start_date, end_date, callBack);
    }

    @Override
    public void getProfile(CallBack<Api<Profile>> callBack) {
        remoteRepository.getProfile(new CallBack<Api<Profile>>() {
            @Override
            public void onSuccess(Api<Profile> profileApi) {
                super.onSuccess(profileApi);
                Profile profile = profileApi.getData();
                Preference.setPhone(profile.getPhone());
                Preference.setEmail(profile.getEmail());
                Preference.setIdUser(profile.getId() + "");
                Preference.setFirstName(profile.getName());
                Preference.setAge(profile.getAge());
                Preference.setGender(profile.getGender());
                Avatar avatar = profile.getAvatar();
                if (avatar != null) {
                    Preference.setImage(avatar.getUrlMd());
                }
                callBack.onSuccess(profileApi);
            }

            @Override
            public void onFail(RequestException e) {
                super.onFail(e);
                callBack.onFail(e);
            }
        });
    }


    @Override
    public void createStaff(String industry_id, String name, String phone, String email, String role, List<Integer> permissions, CallBack<Api<DefualtResponse>> callBack) {
        remoteRepository.createStaff(industry_id, name, phone, email, role, permissions, callBack);
    }

    @Override
    public void deleteStaff(String staff_id, CallBack<Api<DefualtResponse>> callBack) {
        remoteRepository.deleteStaff(staff_id, callBack);
    }

    @Override
    public void updateStaff(String staff_id, String userName, String phone, String email, String role, List<Integer> permissions, CallBack<Api<DefualtResponse>> callBack) {
        remoteRepository.updateStaff(staff_id, userName, phone, email, role, permissions, callBack);
    }

    @Override
    public void uploadAvatar(String imagePath, CallBack<Api<DefualtResponse>> callBack) {
        remoteRepository.uploadAvatar(imagePath, callBack);
    }

    @Override
    public void createService(String industry_id, String title, String duration, String price, String notes_for_the_customer, CallBack<Api<List<ServiceResult>>> callBack) {
        remoteRepository.createService(industry_id, title, duration, price, notes_for_the_customer, callBack);
    }

    @Override
    public void updateService(String service_id, String title, String duration, String price, String notes_for_the_customer, CallBack<Api<DefualtResponse>> callBack) {
        remoteRepository.updateService(service_id, title, duration, price, notes_for_the_customer, callBack);
    }

    @Override
    public void deleteService(String service_id, CallBack<Api<DefualtResponse>> callBack) {
        remoteRepository.deleteService(service_id, callBack);
    }

    @Override
    public void createIndustry(NewIndustry newIndustry, CallBack<Api<IndustryResult>> callBack) {
        remoteRepository.createIndustry(newIndustry, callBack);
    }

    @Override
    public void uploadImageIndustry(String industry_id, String manner, String image, CallBack<Api<DefualtResponse>> callBack) {
        remoteRepository.uploadImageIndustry(industry_id, manner, image, callBack);
    }

    @Override
    public void deleteImageIndustry(String id, CallBack<Api<DefualtResponse>> callBack) {
        remoteRepository.deleteImageIndustry(id, callBack);
    }


    @Override
    public void updateProfile(String name, String email, String desc, String birthday, String gender, String language, String device_type,
                              String device_id,
                              String device_token, CallBack<Api<DefualtResponse>> callBack) {
        remoteRepository.updateProfile(name, email, desc, birthday, gender, language, device_type, device_id, device_token, callBack);
    }

    @Override
    public void showIndustry(String industry_id, CallBack<Api<ShowIndustry>> callBack) {
        remoteRepository.showIndustry(industry_id, callBack);
    }

    @Override
    public void updateWorkingHours(String industry_id, List<WorkingHoursItem> workingHours, CallBack<Api<DefualtResponse>> callBack) {
        remoteRepository.updateWorkingHours(industry_id, workingHours, callBack);
    }

    @Override
    public void getSuggestionsAppointment(String industry_id, CallBack<Api<List<Suggestion>>> callBack) {
        remoteRepository.getSuggestionsAppointment(industry_id, callBack);
    }

    @Override
    public void updateAppointment(String appointment_id, NewBook newBook, CallBack<Api<AppointmentDetail>> callBack) {
        remoteRepository.updateAppointment(appointment_id, newBook, callBack);
    }

    @Override
    public void getAppointmentDetail(String appointment_id, CallBack<Api<AppointmentDetail>> callBack) {
        remoteRepository.getAppointmentDetail(appointment_id, callBack);
    }

    @Override
    public void deleteAppointment(String appointment_id, CallBack<Api<DefualtResponse>> callBack) {
        remoteRepository.deleteAppointment(appointment_id, callBack);
    }

    @Override
    public void getAllProvider(String category_id, String favorites, String lat, String lng, String distance, String city, String countryId, String searchText, int page_number, CallBack<Api<List<Provider>>> callBack) {
        remoteRepository.getAllProvider(category_id, favorites, lat, lng, distance, city, countryId, searchText, page_number, callBack);
    }

    @Override
    public void getCitys(String id, CallBack<Api<List<City>>> callBack) {
        remoteRepository.getCitys(id, callBack);
    }

    @Override
    public void favProvider(String provider_id, CallBack<Api<DefualtResponse>> callBack) {
        remoteRepository.favProvider(provider_id, callBack);
    }

    @Override
    public void unFavProvider(String provider_id, CallBack<Api<DefualtResponse>> callBack) {
        remoteRepository.unFavProvider(provider_id, callBack);
    }

    @Override
    public void getAllAppointment(String status, int page_number, CallBack<Api<List<Appointment>>> callBack) {
        remoteRepository.getAllAppointment(status, page_number, callBack);
    }

    @Override
    public void getProviderDetail(String provider_id, CallBack<Api<ProviderDetail>> callBack) {
        remoteRepository.getProviderDetail(provider_id, callBack);
    }

    @Override
    public void makeAppointment(String service_id, String staff_id, String client_name, String client_phone, String client_age, String client_gender, String from_to, String description, CallBack<Api<AppointmentDetail>> callBack) {
        remoteRepository.makeAppointment(service_id, staff_id, client_name, client_phone, client_age, client_gender, from_to, description, callBack);
    }

    @Override
    public void makeAppointment(NewBook newBook, CallBack<Api<AppointmentDetail>> callBack) {
        remoteRepository.makeAppointment(newBook, callBack);
    }

    @Override
    public void getWorkingHours(String industry_id, CallBack<Api<List<WorkingHoursItem>>> callBack) {
        remoteRepository.getWorkingHours(industry_id, callBack);
    }

    @Override
    public void getRules(String lang, CallBack<Api<String>> callBack) {
        remoteRepository.getRules(lang, callBack);
    }

    @Override
    public void addComment(String appointment_id, float rate, String content, CallBack<Api<DefualtResponse>> callBack) {
        remoteRepository.addComment(appointment_id, rate, content, callBack);
    }

    @Override
    public void getComments(String provider_id, int page, CallBack<Api<List<Comment>>> callBack) {
        remoteRepository.getComments(provider_id, page, callBack);
    }

    @Override
    public void skipComment(CallBack<Api<DefualtResponse>> callBack) {
        remoteRepository.skipComment(callBack);
    }

    @Override
    public void getSlider(CallBack<Api<List<Slide>>> callBack) {
        remoteRepository.getSlider(callBack);
    }

    @Override
    public void changeLanguage(ChangeLanguageModel model, CallBack<Api<DefualtResponse>> callBack) {
        remoteRepository.changeLanguage(model, callBack);
    }

    @Override
    public void getCountries(CallBack<Api<List<Country>>> callBack) {
        remoteRepository.getCountries(callBack);
    }

    @Override
    public void suggestDoctor(SuggestModel model, CallBack<DefualtResponse> callBack) {
        remoteRepository.suggestDoctor(model, callBack);
    }

    @Override
    public void getNotifications(String lang, CallBack<Api<List<NotificationsModel>>> callBack) {
        remoteRepository.getNotifications(lang, callBack);
    }

    @Override
    public void getUnreadNotifications(CallBack<Api<List<NotificationsModel>>> callBack) {
        remoteRepository.getUnreadNotifications(callBack);
    }

    @Override
    public void changeToReadNotifications(String id, CallBack<Api<DefualtResponse>> callBack) {
        remoteRepository.changeToReadNotifications(id, callBack);
    }
}
