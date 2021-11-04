package net.umaass_user.app.ui;


import android.graphics.drawable.Drawable;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;
import com.wdullaer.materialdatetimepicker.time.Timepoint;

import net.umaass_user.app.R;
import net.umaass_user.app.application.G;
import net.umaass_user.app.data.Repository;
import net.umaass_user.app.data.interfaces.CallBack;
import net.umaass_user.app.data.remote.models.Api;
import net.umaass_user.app.data.remote.models.AppointmentDetail;
import net.umaass_user.app.data.remote.models.WorkingHoursItem;
import net.umaass_user.app.data.remote.utils.RequestException;
import net.umaass_user.app.models.NewBook;
import net.umaass_user.app.ui.base.BaseFragment;
import net.umaass_user.app.utils.Utils;

import org.threeten.bp.DayOfWeek;
import org.threeten.bp.LocalDate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class FragmentBooksStepOne extends BaseFragment implements BlockingStep, TimePickerDialog.OnTimeSetListener {

    private String selectedDate;
    private String selectedTime;
    private MaterialCalendarView calendarView;
    private NewBook newBook;
    private String providerId;
    private String industryId;
    private List<DayOfWeek> dayValid = new ArrayList<>();
    private HashMap<Integer, Integer[]> maxTimes = new HashMap<>();
    private HashMap<Integer, Integer[]> minTimes = new HashMap<>();

    private AppointmentDetail appointmentDetail;
    private boolean isEdit;

    public void setAppointmentDetail(AppointmentDetail appointmentDetail) {
        this.appointmentDetail = appointmentDetail;
    }

    public void setEdit(boolean edit) {
        isEdit = edit;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public void setIndustryId(String industryId) {
        this.industryId = industryId;
    }

    public void setNewBook(NewBook newBook) {
        this.newBook = newBook;
    }

    @Override
    public int getViewLayout() {
        return R.layout.fragment_books_step_one;
    }


    @Override
    public void readView() {
        super.readView();
        calendarView = baseView.findViewById(R.id.calendarView);
    }

    private LocalDate minDate;

    @Override
    public void functionView() {
        super.functionView();


        // checkValid();
    }

    private void checkValid() {

        Date input = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(input);
        minDate = LocalDate.of(cal.get(Calendar.YEAR),
                               cal.get(Calendar.MONTH) + 1,
                               cal.get(Calendar.DAY_OF_MONTH));

        calendarView.state().edit()
                    .setMinimumDate(minDate)
                    .setCalendarDisplayMode(CalendarMode.MONTHS)
                    .setFirstDayOfWeek(DayOfWeek.MONDAY)
                    .isCacheCalendarPositionEnabled(true)
                    .commit();

        // calendarView.setSelectedDate(minDate);
        calendarView.setCurrentDate(minDate);
        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                selectedDate = date.getYear() + "-" + date.getMonth() + "-" + date.getDay();
                openTimeDialog(date.getDate().getDayOfWeek().getValue());
            }
        });

        DayViewDecorator dis = new DayViewDecorator() {
            @Override
            public boolean shouldDecorate(CalendarDay day) {
                boolean dis = !dayValid.contains(day.getDate().getDayOfWeek());
                return dis;
            }

            @Override
            public void decorate(DayViewFacade view) {
                view.setDaysDisabled(true);
            }
        };

        DayViewDecorator ena = new DayViewDecorator() {
            @Override
            public boolean shouldDecorate(CalendarDay day) {
                boolean en = dayValid.contains(day.getDate().getDayOfWeek());
                return day.getDate().isAfter(minDate) && en;
            }
// 1 --> 0 . 2 --> 1

            @Override
            public void decorate(DayViewFacade view) {
                view.setDaysDisabled(false);
                /*Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.date_selector);
                view.setBackgroundDrawable(drawable);*/
                Drawable drawableSelection = ContextCompat.getDrawable(G.getInstance(), R.drawable.date_selector);
                if (drawableSelection != null) {
                    view.setSelectionDrawable(drawableSelection);
                }
            }
        };
        calendarView.addDecorators(dis, ena);
    }

    private DayOfWeek day(int i) {
        switch (i) {
            case 0:
                return DayOfWeek.SUNDAY;
            case 1:
                return DayOfWeek.MONDAY;
            case 2:
                return DayOfWeek.TUESDAY;
            case 3:
                return DayOfWeek.WEDNESDAY;
            case 4:
                return DayOfWeek.THURSDAY;
            case 5:
                return DayOfWeek.FRIDAY;
            case 6:
                return DayOfWeek.SATURDAY;
        }
        return DayOfWeek.SUNDAY;
    }

    private void openTimeDialog(int dayOfWeek) {
        Calendar now = Calendar.getInstance();
        TimePickerDialog dpd = TimePickerDialog.newInstance(
                this,
                now.get(Calendar.HOUR_OF_DAY), // Initial year selection
                now.get(Calendar.MINUTE), // Initial month selection
                true); // Inital day selection

        Integer[] max = maxTimes.get(dayOfWeek);
        Integer[] min = minTimes.get(dayOfWeek);
        if (max != null && min != null) {
            Timepoint maxTimePoint = new Timepoint(max[0], max[1], max[2]);
            Timepoint minTimePoint = new Timepoint(min[0], min[1], min[2]);
            if (maxTimePoint.compareTo(minTimePoint) > 0) {
                dpd.setMaxTime(maxTimePoint);
                dpd.setMinTime(minTimePoint);
            }
        }
        dpd.show(getChildFragmentManager(), "Timepickerdialog");
    }


    private void getData() {
        showLoading();
        Repository.getInstance().getWorkingHours(industryId, new CallBack<Api<List<WorkingHoursItem>>>() {
            @Override
            public void onSuccess(Api<List<WorkingHoursItem>> listApi) {
                super.onSuccess(listApi);
                hideLoading();
                if (listApi != null && listApi.getData() != null) {
                    for (int i = 0; i < listApi.getData().size(); i++) {
                        WorkingHoursItem datum = listApi.getData().get(i);
                        String[] max = datum.getEnd().split("\\:");
                        if (max.length > 2) {
                            maxTimes.put(datum.getDay(), new Integer[]{Integer.valueOf(max[0]),
                                                                       Integer.valueOf(max[1]),
                                                                       Integer.valueOf(max[2])});
                        }
                        String[] min = datum.getStart().split("\\:");
                        if (min.length > 2) {
                            minTimes.put(datum.getDay(), new Integer[]{Integer.valueOf(min[0]),
                                                                       Integer.valueOf(min[1]),
                                                                       Integer.valueOf(min[2])});
                        }
                        Log.i("dayValid", "day : " + datum.getDay());
                        dayValid.add(day(datum.getDay()));
                    }
                    checkValid();
                }
            }

            @Override
            public void onFail(RequestException e) {
                super.onFail(e);
                hideLoading();
            }
        });
    }

    @Override
    public void onNextClicked(StepperLayout.OnNextClickedCallback callback) {
        callback.goToNextStep();
    }

    @Override
    public void onCompleteClicked(StepperLayout.OnCompleteClickedCallback callback) {
        callback.complete();
    }

    @Override
    public void onBackClicked(StepperLayout.OnBackClickedCallback callback) {
        callback.goToPrevStep();
    }

    @Nullable
    @Override
    public VerificationError verifyStep() {
        if (selectedDate == null || selectedTime == null) {
            return new VerificationError(Utils.getString(R.string.please_select_date_and_time));
        }
        newBook.from_to = selectedDate + " " + selectedTime;
        return null;
    }

    @Override
    public void onSelected() {
        G.log("Booking", "Step 1");
        getData();
    }

    @Override
    public void onError(@NonNull VerificationError error) {
        G.toast(error.getErrorMessage());
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        String time = hourOfDay + ":" + minute;
        selectedTime = time;
    }
}
