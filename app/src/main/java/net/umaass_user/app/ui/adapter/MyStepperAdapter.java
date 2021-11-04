package net.umaass_user.app.ui.adapter;

import android.content.Context;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;

import com.stepstone.stepper.Step;
import com.stepstone.stepper.adapter.AbstractFragmentStepAdapter;
import com.stepstone.stepper.viewmodel.StepViewModel;

import net.umaass_user.app.R;
import net.umaass_user.app.data.remote.models.AppointmentDetail;
import net.umaass_user.app.models.NewBook;
import net.umaass_user.app.ui.FragmentBooksStepOne;
import net.umaass_user.app.ui.FragmentBooksStepThree;
import net.umaass_user.app.ui.FragmentBooksStepTwo;
import net.umaass_user.app.utils.Utils;

public class MyStepperAdapter extends AbstractFragmentStepAdapter {

    private NewBook newBook = new NewBook();
    private String providerId;
    private String industryId;
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
        newBook.staff_id = providerId;
    }


    public void setIndustryId(String industryId) {
        this.industryId = industryId;
    }

    public NewBook getNewBook() {
        return newBook;
    }

    public MyStepperAdapter(FragmentManager fm, Context context) {
        super(fm, context);
    }

    @Override
    public Step createStep(int position) {
        switch (position) {
            case 0:
                FragmentBooksStepOne fragmentBooksStepOne = new FragmentBooksStepOne();
                fragmentBooksStepOne.setNewBook(newBook);
                fragmentBooksStepOne.setProviderId(providerId);
                fragmentBooksStepOne.setIndustryId(industryId);
                fragmentBooksStepOne.setEdit(isEdit);
                fragmentBooksStepOne.setAppointmentDetail(appointmentDetail);
                return fragmentBooksStepOne;
            case 1:
                FragmentBooksStepTwo fragmentBooksStepTwo = new FragmentBooksStepTwo();
                fragmentBooksStepTwo.setNewBook(newBook);
                fragmentBooksStepTwo.setProviderId(providerId);
                fragmentBooksStepTwo.setIndustryId(industryId);
                fragmentBooksStepTwo.setEdit(isEdit);
                fragmentBooksStepTwo.setAppointmentDetail(appointmentDetail);
                return fragmentBooksStepTwo;
            case 2:
                FragmentBooksStepThree fragmentBooksStepThree = new FragmentBooksStepThree();
                fragmentBooksStepThree.setNewBook(newBook);
                fragmentBooksStepThree.setProviderId(providerId);
                fragmentBooksStepThree.setIndustryId(industryId);
                fragmentBooksStepThree.setEdit(isEdit);
                fragmentBooksStepThree.setAppointmentDetail(appointmentDetail);
                return fragmentBooksStepThree;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    private String[] titles = {Utils.getString(R.string.date), Utils.getString(R.string.information), Utils.getString(R.string.appointment)};

    @NonNull
    @Override
    public StepViewModel getViewModel(@IntRange(from = 0) int position) {
        //Override this method to set Step title for the Tabs, not necessary for other stepper types
        return new StepViewModel.Builder(context)
                .setTitle(titles[position]) //can be a CharSequence instead
                .create();
    }


}