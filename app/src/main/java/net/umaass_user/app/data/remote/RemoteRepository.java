package net.umaass_user.app.data.remote;


import com.google.gson.Gson;

import net.umaass_user.app.application.G;
import net.umaass_user.app.application.Preference;
import net.umaass_user.app.data.interfaces.CallBack;
import net.umaass_user.app.data.remote.models.Api;
import net.umaass_user.app.data.remote.models.Appointment;
import net.umaass_user.app.data.remote.models.AppointmentDetail;
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
import net.umaass_user.app.data.remote.service.ApiConnection;
import net.umaass_user.app.data.remote.service.ServiceApi;
import net.umaass_user.app.data.remote.utils.APIError;
import net.umaass_user.app.data.remote.utils.ErrorUtils;
import net.umaass_user.app.data.remote.utils.RequestException;
import net.umaass_user.app.data.remote.utils.RetryableCallback;
import net.umaass_user.app.models.ChangeLanguageModel;
import net.umaass_user.app.models.NewBook;
import net.umaass_user.app.models.NewIndustry;
import net.umaass_user.app.models.NewIndustryForSend;
import net.umaass_user.app.models.NewService;
import net.umaass_user.app.models.NewStaff;
import net.umaass_user.app.models.UpdateWorkingHours;
import net.umaass_user.app.models.WorkingHour;

import java.io.File;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;

public class RemoteRepository implements RemoteSource {

    private static RemoteRepository INSTANCE;
    private ServiceApi service;
    private ApiConnection apiConnection;
    private ApiConnection apiLongConnection;

    public static RemoteRepository getInstance() {
        if (INSTANCE == null) {
            synchronized (RemoteRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new RemoteRepository();
                }
            }
        }
        return INSTANCE;
    }

    private RemoteRepository() {
        apiConnection = new ApiConnection(5, 30, 30);
        apiLongConnection = new ApiConnection(5, 60 * 10, 60 * 10);
    }


    private <T> RetryableCallback<T> makeCallBack(final CallBack<T> callBack) {
        return makeCallBack(0, 0, callBack);
    }

    private <T> RetryableCallback<T> makeCallBack(int maxRetry, int maxTimerRetry, final CallBack<T> callBack) {
        return new RetryableCallback<T>(maxRetry, maxTimerRetry) {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                super.onResponse(call, response);
                if ((response.code() == 200 || response.code() == 201) && response.body() != null && response.isSuccessful()) {
                    callBack.onSuccess(response.body());
                } else {
                    if (response.code() == 401) {
                        Preference.logOut();
                    }
                    APIError apiError = ErrorUtils.parseError(apiConnection.getRetrofit(), response);
                    try {
                        callBack.onFail(new RequestException(apiError.getMessage(), response.code()));
                    } catch (Exception e) {
                        e.printStackTrace();
                        callBack.onFail(new RequestException(response.message(), response.code()));
                    }
                    //  Crashlytics.logException(new Exception("code : " + response.code() + " Error : " + apiError.message()));
                }
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                super.onFailure(call, t);
                if (t instanceof SocketTimeoutException) {
                } else {
                    //  Crashlytics.logException(t);
                }
                callBack.onFail(new RequestException(t.getMessage(), 0));

            }
        };
    }

    private void handleError(int code, String m) {
        String message = "خطایی رخ داد";
        switch (code) {
            case 400:
                message = "درخواست ناصحیح";
                break;
            case 401:
                message = "مجاز نیست";
                break;
            case 403:
                message = "تحریم";
                break;
            case 404:
                message = "پیدا نشد";
                break;
        }
        G.toast(message);
    }


    @Override
    public void getLogin(String access_token,String phoneNumber, CallBack<Api<Login>> callBack) {
        apiConnection.getService()
                     .getLogin(access_token,phoneNumber)
                     .enqueue(makeCallBack(callBack));
    }

    @Override
    public void register(String access_token, String name, String email, String birthday, String gender,String phoneNumber, CallBack<Api<Register>> callBack) {
        apiConnection.getService().register(access_token,
                                            name,
                                            email,
                                            birthday,
                                            gender,phoneNumber).enqueue(makeCallBack(callBack));
    }

    @Override
    public void aboutUs(String lang,CallBack<Api<String>> callBack) {
        apiConnection.getService().aboutUs(lang).enqueue(makeCallBack(callBack));
    }


    @Override
    public void getAllIndustries(boolean mine, CallBack<Api<List<Industry>>> callBack) {
        apiConnection.getService().getAllIndustries(mine).enqueue(makeCallBack(callBack));
    }

    @Override
    public void getAllCategories(String lang,CallBack<Api<List<Category>>> callBack) {
        apiConnection.getService().getAllCategories(lang).enqueue(makeCallBack(callBack));
    }

    @Override
    public void getAllAppointments(int page_number, String status, String start_date, String end_date, CallBack<Api<List<Appointment>>> callBack) {
        apiConnection.getService().getAllAppointments(status, start_date, end_date, page_number).enqueue(makeCallBack(callBack));
    }

    @Override
    public void getProfile(CallBack<Api<Profile>> callBack) {
        apiConnection.getService().getProfile().enqueue(makeCallBack(callBack));
    }

    @Override
    public void createStaff(String industry_id, String name, String phone, String email, String role, List<Integer> permissions, CallBack<Api<DefualtResponse>> callBack) {
        List<NewStaff> newServices = new ArrayList<>();
        NewStaff newStaff = new NewStaff();
        newStaff.setIndustryId(industry_id);
        newStaff.setName(name);
        newStaff.setPhone(phone);
        newStaff.setEmail(email);
        newStaff.setPermissions(permissions);
        newStaff.setRole(role);
        newServices.add(newStaff);
        apiConnection.getService().createStaff(newServices).enqueue(makeCallBack(callBack));
    }

    @Override
    public void deleteStaff(String staff_id, CallBack<Api<DefualtResponse>> callBack) {
        apiConnection.getService().deleteStaff(staff_id).enqueue(makeCallBack(callBack));
    }

    @Override
    public void updateStaff(String staff_id, String userName, String phone, String email, String role, List<Integer> permissions, CallBack<Api<DefualtResponse>> callBack) {
        NewStaff newStaff = new NewStaff();
        newStaff.setName(userName);
        newStaff.setPhone(phone);
        newStaff.setEmail(email);
        newStaff.setPermissions(permissions);
        newStaff.setRole(role);
        apiConnection.getService().updateStaff(staff_id, newStaff).enqueue(makeCallBack(callBack));
    }

    @Override
    public void uploadAvatar(String imagePath_, CallBack<Api<DefualtResponse>> callBack) {
        MultipartBody.Part body = null;
        if (imagePath_ != null) {
            File file = new File(imagePath_);
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            // MultipartBody.Part is used to send also the actual file name
            body = MultipartBody.Part.createFormData("avatar", file.getName(), requestFile);
        }
        //  RequestBody fname = RequestBody.create(MediaType.parse("multipart/form-data"), body);
        apiLongConnection.getService().uploadAvatar(body).enqueue(makeCallBack(callBack));
    }

    @Override
    public void createService(String industry_id, String title, String duration, String price, String notes_for_the_customer, CallBack<Api<List<ServiceResult>>> callBack) {
        List<NewService> newServices = new ArrayList<>();
        NewService newService = new NewService();
        newService.setDuration(duration);
        newService.setIndustryId(industry_id);
        newService.setNotesForTheCustomer(notes_for_the_customer);
        newService.setPrice(price);
        newService.setTitle(title);
        newServices.add(newService);
        apiConnection.getService().createService(newServices).enqueue(makeCallBack(callBack));
    }

    @Override
    public void updateService(String service_id, String title, String duration, String price, String notes_for_the_customer, CallBack<Api<DefualtResponse>> callBack) {
        apiConnection.getService().updateService(service_id, title, duration, price, notes_for_the_customer).enqueue(makeCallBack(callBack));
    }

    @Override
    public void deleteService(String service_id, CallBack<Api<DefualtResponse>> callBack) {
        apiConnection.getService().deleteService(service_id).enqueue(makeCallBack(callBack));
    }

    @Override
    public void createIndustry(NewIndustry newIndustry, CallBack<Api<IndustryResult>> callBack) {
        List<WorkingHour> workingHours = new ArrayList<>();

        for (WorkingHoursItem working_hour : newIndustry.working_hours) {
            WorkingHour workingHour = new WorkingHour();
            workingHour.setDay(working_hour.getDay());
            List<String> strings = new ArrayList<>();
            strings.add(working_hour.getStart());
            strings.add(working_hour.getEnd());
            workingHour.setTime(strings);
            workingHours.add(workingHour);
        }
        NewIndustryForSend newIndustryForSend = new NewIndustryForSend();
        newIndustryForSend.category_id = newIndustry.category_id;
        newIndustryForSend.address = newIndustry.address;
        newIndustryForSend.admin_password = newIndustry.admin_password;
        newIndustryForSend.coworker_password = newIndustry.coworker_password;
        newIndustryForSend.assistant_password = newIndustry.assistant_password;
        newIndustryForSend.description = newIndustry.description;
        newIndustryForSend.phone = newIndustry.phone;
        newIndustryForSend.working_hours = workingHours;
        newIndustryForSend.title = newIndustry.title;
        String w = new Gson().toJson(workingHours);
        G.log("workingHours", "workingHours :" + w);
        apiConnection.getService().createIndustry(newIndustryForSend).enqueue(makeCallBack(callBack));
    }

    @Override
    public void uploadImageIndustry(String industry_id_, String manner_, String image_, CallBack<Api<DefualtResponse>> callBack) {
        MultipartBody.Part body = null;
        if (image_ != null) {
            File file = new File(image_);
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            body = MultipartBody.Part.createFormData("image", file.getName(), requestFile);
        }
        RequestBody industry_id = RequestBody.create(MediaType.parse("multipart/form-data"), industry_id_);
        RequestBody manner = RequestBody.create(MediaType.parse("multipart/form-data"), manner_);
        apiLongConnection.getService().uploadImageIndustry(industry_id, manner, body).enqueue(makeCallBack(callBack));
    }

    @Override
    public void deleteImageIndustry(String id, CallBack<Api<DefualtResponse>> callBack) {
        apiConnection.getService().deleteImageIndustry(id).enqueue(makeCallBack(callBack));
    }


    @Override
    public void updateProfile(String name, String email, String desc, String birthday, String gender, String language, String device_type, String device_id, String device_token, CallBack<Api<DefualtResponse>> callBack) {
        apiConnection.getService().updateProfile(name, email, desc, birthday, gender, language,
                                                 device_type, device_id, device_token, null, null, null).enqueue(makeCallBack(callBack));
    }

    @Override
    public void showIndustry(String industry_id, CallBack<Api<ShowIndustry>> callBack) {
        apiConnection.getService().showIndustry(industry_id).enqueue(makeCallBack(callBack));
    }

    @Override
    public void updateWorkingHours(String industry_id, List<WorkingHoursItem> items, CallBack<Api<DefualtResponse>> callBack) {
        List<WorkingHour> workingHours = new ArrayList<>();

        for (WorkingHoursItem working_hour : items) {
            WorkingHour workingHour = new WorkingHour();
            workingHour.setDay(working_hour.getDay());
            List<String> strings = new ArrayList<>();
            strings.add(working_hour.getStart());
            strings.add(working_hour.getEnd());
            workingHour.setTime(strings);
            workingHours.add(workingHour);
        }
        UpdateWorkingHours updateWorkingHours = new UpdateWorkingHours();
        updateWorkingHours.setWorking_hours(workingHours);
        apiConnection.getService().updateWorkingHours(industry_id, updateWorkingHours).enqueue(makeCallBack(callBack));
    }

    @Override
    public void getSuggestionsAppointment(String industry_id, CallBack<Api<List<Suggestion>>> callBack) {
        apiConnection.getService().getSuggestionsAppointment(industry_id).enqueue(makeCallBack(callBack));
    }

    @Override
    public void updateAppointment(String appointment_id, NewBook newBook, CallBack<Api<AppointmentDetail>> callBack) {
        apiLongConnection.getService().updateAppointment(appointment_id, newBook).enqueue(makeCallBack(callBack));
    }

    @Override
    public void getAppointmentDetail(String appointment_id, CallBack<Api<AppointmentDetail>> callBack) {
        apiConnection.getService().getAppointmentDetail(appointment_id).enqueue(makeCallBack(callBack));
    }

    @Override
    public void deleteAppointment(String appointment_id, CallBack<Api<DefualtResponse>> callBack) {
        apiConnection.getService().deleteAppointment(appointment_id).enqueue(makeCallBack(callBack));
    }

    @Override
    public void getAllProvider(String category_id, String favorites, String lat, String lng, String distance, String city, String countryId, String searchText, int page_number, CallBack<Api<List<Provider>>> callBack) {
        boolean b = false;
        try {
            b = distance != null && !distance.isEmpty() && Integer.parseInt(distance) > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        apiConnection.getService().getAllProvider(category_id, favorites, lat, lng, b, distance, city, countryId, searchText, page_number).enqueue(makeCallBack(callBack));
    }

    @Override
    public void getCitys(String id, CallBack<Api<List<City>>> callBack) {
        apiConnection.getService().getCitys(id).enqueue(makeCallBack(callBack));
    }

    @Override
    public void favProvider(String provider_id, CallBack<Api<DefualtResponse>> callBack) {
        apiConnection.getService().favProvider(provider_id).enqueue(makeCallBack(callBack));
    }

    @Override
    public void unFavProvider(String provider_id, CallBack<Api<DefualtResponse>> callBack) {
        apiConnection.getService().unFavProvider(provider_id).enqueue(makeCallBack(callBack));
    }

    @Override
    public void getAllAppointment(String status, int page_number, CallBack<Api<List<Appointment>>> callBack) {
        apiConnection.getService().getAllAppointment(status, page_number).enqueue(makeCallBack(callBack));
    }

    @Override
    public void getProviderDetail(String provider_id, CallBack<Api<ProviderDetail>> callBack) {
        apiConnection.getService().getProviderDetail(provider_id).enqueue(makeCallBack(callBack));
    }

    @Override
    public void makeAppointment(String service_id, String staff_id, String client_name, String client_phone, String client_age, String client_gender, String from_to, String description, CallBack<Api<AppointmentDetail>> callBack) {
        apiConnection.getService().makeAppointment(service_id, staff_id, client_name, client_phone, client_age, client_gender, from_to, description).enqueue(makeCallBack(callBack));
    }

    @Override
    public void makeAppointment(NewBook newBook, CallBack<Api<AppointmentDetail>> callBack) {
        apiConnection.getService().makeAppointment(newBook).enqueue(makeCallBack(callBack));
    }

    @Override
    public void addComment(String appointment_id, float rate, String content, CallBack<Api<DefualtResponse>> callBack) {
        apiConnection.getService().addComment(appointment_id, (int) rate, content).enqueue(makeCallBack(callBack));
    }

    @Override
    public void getComments(String provider_id, int page, CallBack<Api<List<Comment>>> callBack) {
        apiConnection.getService().getComments(provider_id, page).enqueue(makeCallBack(callBack));
    }

    @Override
    public void skipComment(CallBack<Api<DefualtResponse>> callBack) {
        apiConnection.getService().skipComment().enqueue(makeCallBack(callBack));
    }

    @Override
    public void getWorkingHours(String industry_id, CallBack<Api<List<WorkingHoursItem>>> callBack) {
        apiConnection.getService().getWorkingHours(industry_id).enqueue(makeCallBack(callBack));
    }

    @Override
    public void getRules(String lang,CallBack<Api<String>> callBack) {
        apiConnection.getService().getRules(lang).enqueue(makeCallBack(callBack));
    }

    @Override
    public void getSlider(CallBack<Api<List<Slide>>> callBack) {
        apiConnection.getService().getSlider().enqueue(makeCallBack(callBack));
    }

    @Override
    public void changeLanguage(ChangeLanguageModel model, CallBack<Api<DefualtResponse>> callBack) {
        apiLongConnection.getService().changeLanguage(model).enqueue(makeCallBack(callBack));
    }

    @Override
    public void getCountries(CallBack<Api<List<Country>>> callBack) {
        apiConnection.getService().getCountries().enqueue(makeCallBack(callBack));
    }

    @Override
    public void suggestDoctor(SuggestModel model, CallBack<DefualtResponse> callBack) {
        apiConnection.getService().suggest( model).enqueue(makeCallBack(callBack));
    }

    @Override
    public void getNotifications(String lang, CallBack<Api<List<NotificationsModel>>> callBack) {
        apiConnection.getService().getNotifications(lang).enqueue(makeCallBack(callBack));
    }

    @Override
    public void getUnreadNotifications(CallBack<Api<List<NotificationsModel>>> callBack) {
        apiConnection.getService().getUnreadNotifications("unread").enqueue(makeCallBack(callBack));
    }

    @Override
    public void changeToReadNotifications(String id, CallBack<Api<DefualtResponse>> callBack) {
        apiConnection.getService().changeToReadNotifications(id).enqueue(makeCallBack(callBack));
    }
}
