package net.umaass_user.app.ui.dialog;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jaygoo.widget.OnRangeChangedListener;
import com.jaygoo.widget.RangeSeekBar;

import net.umaass_user.app.R;
import net.umaass_user.app.application.G;
import net.umaass_user.app.application.Preference;
import net.umaass_user.app.data.Repository;
import net.umaass_user.app.data.interfaces.CallBack;
import net.umaass_user.app.data.remote.models.Api;
import net.umaass_user.app.data.remote.models.Category;
import net.umaass_user.app.data.remote.models.City;
import net.umaass_user.app.data.remote.models.Country;
import net.umaass_user.app.data.remote.utils.RequestException;
import net.umaass_user.app.interfac.ItemClickListener;
import net.umaass_user.app.interfac.ListItem;
import net.umaass_user.app.ui.adapter.AdapterCategory;
import net.umaass_user.app.ui.base.BaseDialog;
import net.umaass_user.app.ui.components.RoundCornerButton;
import net.umaass_user.app.utils.SwitchButton;
import net.umaass_user.app.utils.Utils;
import net.umaass_user.app.utils.permission.ActivityUtils;

import java.util.ArrayList;
import java.util.List;

public class SearchDialog extends BaseDialog {

    RecyclerView rv_gender, rv_place;
    RangeSeekBar seekbar_distance;
    AppCompatTextView txt_distance;
    AppCompatTextView cityName, countryName, categoryName;
    SwitchButton switch_button;
    LinearLayout ll_grade, ll_specialty, ll_city, ll_country, ll_category;
    FilterListener filterListener;
    RoundCornerButton btnDone;
    RoundCornerButton btnClear;
    ProgressBar progressBar;
    String countryId = "";
    String categoryId = "";

    private Filter filter = new Filter();

    public void setFilter(Filter filter) {
        if (filter != null) {
            this.filter = filter;
        }
    }

    public void setFilterListener(FilterListener filterListener) {
        this.filterListener = filterListener;
    }

    @Override
    public int getViewLayout() {
        return R.layout.dialog_search;
    }

    @Override
    public void readView() {
        super.readView();
        rv_gender = baseView.findViewById(R.id.rv_gender);
        rv_place = baseView.findViewById(R.id.rv_place);
        txt_distance = baseView.findViewById(R.id.txt_distance);
        seekbar_distance = baseView.findViewById(R.id.seekbar_distance);
        switch_button = baseView.findViewById(R.id.switch_button);
        ll_grade = baseView.findViewById(R.id.ll_grade);
        ll_specialty = baseView.findViewById(R.id.ll_specialty);
        ll_city = baseView.findViewById(R.id.ll_city);
        btnDone = baseView.findViewById(R.id.btnDone);
        btnClear = baseView.findViewById(R.id.btnClear);
        progressBar = baseView.findViewById(R.id.progressBar);
        cityName = baseView.findViewById(R.id.cityName);
        countryName = baseView.findViewById(R.id.countryName);
        ll_category = baseView.findViewById(R.id.ll_category);
        categoryName = baseView.findViewById(R.id.categoryName);
        ll_country = baseView.findViewById(R.id.ll_country);
    }

    @Override
    public void functionView() {
        super.functionView();
        hideLoading();
        ll_grade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GradeDialog gradeDialog = new GradeDialog();
                gradeDialog.show(getChildFragmentManager(), "gradeDialog");
            }
        });

        ll_specialty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CityDialog cityDialog = new CityDialog();
                cityDialog.show(getChildFragmentManager(), "specialtyDialog");
            }
        });

        ll_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  CityDialog cityDialog = new CityDialog();
                cityDialog.show(getChildFragmentManager(), "cityDialog");*/
                if (countryId.equals("")) {
                    G.toast(getString(R.string.select_country_first));
                    return;
                }
                openDialogCity();
            }
        });

        ll_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogCategory();
            }
        });

        ll_country.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogCountry();
            }
        });
        seekbar_distance.setVisibility(View.INVISIBLE);
        seekbar_distance.setRange(500, 20000);
        switch_button.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                changeChecked(isChecked);

            }
        });
        seekbar_distance.setOnRangeChangedListener(new OnRangeChangedListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onRangeChanged(RangeSeekBar view, float leftValue, float rightValue, boolean isFromUser) {
                int value = (int) leftValue;
                if (value < 1000)
                    txt_distance.setText(value + " " + Utils.getString(R.string.meter));
                else {
                    int kValue = value / 1000;
                    int mValue = (value % 1000) / 10;
                    txt_distance.setText(String.format(Utils.getCurrentLocale(getContext()),
                                                       "%d.%d " + Utils.getString(R.string.kilometer), kValue, mValue));
                }
                filter.distance = value;

            }

            @Override
            public void onStartTrackingTouch(RangeSeekBar view, boolean isLeft) {

            }

            @Override
            public void onStopTrackingTouch(RangeSeekBar view, boolean isLeft) {

            }
        });

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (filterListener != null) {
                    filterListener.onFilter(filter);
                }
                dismiss();
            }
        });
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnClear = null;
                if (filterListener != null) {
                    filterListener.onFilter(null);
                    Preference.setCurrentCategoryId("");
                    filter.distance = -1;
                    filter.countryId = null;
                    filter.cityId = null;
                    countryId = "";
                }
                dismiss();
            }
        });

        AdapterCategory adapterGender = new AdapterCategory(getActivity());
        AdapterCategory adapterPlace = new AdapterCategory(getActivity());


        List<Category> genders = new ArrayList<>();
        genders.add(new Category(Utils.getString(R.string.male), "1"));
        genders.add(new Category(Utils.getString(R.string.female), "0"));

        adapterGender.enableChoosingMode();
        adapterPlace.enableChoosingMode();

        adapterGender.clearAndPut(genders);

        List<Category> places = new ArrayList<>();
        places.add(new Category("Clinic", "1"));
        places.add(new Category("PoliClinic", "0"));

        adapterPlace.clearAndPut(places);

        LinearLayoutManager categoryManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rv_gender.setLayoutManager(categoryManager);
        rv_gender.setAdapter(adapterGender);


        LinearLayoutManager categoryManager2 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rv_place.setLayoutManager(categoryManager2);
        rv_place.setAdapter(adapterPlace);

        if (filter != null) {
            if (filter.distance != -1) {
                switch_button.setChecked(true);
                seekbar_distance.setVisibility(View.VISIBLE);
                seekbar_distance.setValue(filter.distance);
            }
            if (filter.cityName != null) {
                cityName.setText(filter.cityName);
            }
            if (filter.countryName != null) {
                countryName.setText(filter.countryName);
            }

            if (filter.categoryName != null) {
                categoryName.setText(filter.categoryName);
            }

        }
    }

    private void openDialogCity() {
        showLoading();
        Repository.getInstance().getCitys(countryId, new CallBack<Api<List<City>>>() {
            @Override
            public void onSuccess(Api<List<City>> listApi) {
                super.onSuccess(listApi);
                hideLoading();
                DialogList dialogList = new DialogList(ActivityUtils.getTopActivity(), true);
                dialogList.setTitle(Utils.getString(R.string.choose_city));
                dialogList.clearAndPut(listApi.getData());
                dialogList.setListener(new ItemClickListener<ListItem>() {
                    @Override
                    public void onClick(ListItem item) {
                        filter.cityId = item.getItemId();
                        filter.cityName = item.getItemName();
                        cityName.setText(item.getItemName());
                    }
                });
                dialogList.show();
            }

            @Override
            public void onFail(RequestException e) {
                super.onFail(e);
                hideLoading();
            }
        });

    }

    private void openDialogCountry() {
        showLoading();
        Repository.getInstance().getCountries(new CallBack<Api<List<Country>>>() {
            @Override
            public void onSuccess(Api<List<Country>> listApi) {
                super.onSuccess(listApi);
                hideLoading();
                DialogList dialogList = new DialogList(ActivityUtils.getTopActivity(), true);
                dialogList.setTitle(Utils.getString(R.string.select_country));
                dialogList.clearAndPut(listApi.getData());
                dialogList.setListener(new ItemClickListener<ListItem>() {
                    @Override
                    public void onClick(ListItem item) {
                        filter.countryId = item.getItemId();
                        countryId = item.getItemId();
                        filter.countryName = item.getItemName();
                        countryName.setText(item.getItemName());
                    }
                });
                dialogList.show();
            }

            @Override
            public void onFail(RequestException e) {
                super.onFail(e);
                hideLoading();
            }
        });

    }

    private void openDialogCategory() {
        showLoading();
        Repository.getInstance().getAllCategories(Preference.getLanguage(), new CallBack<Api<List<Category>>>() {
            @Override
            public void onSuccess(Api<List<Category>> listApi) {
                super.onSuccess(listApi);
                hideLoading();
                DialogList dialogList = new DialogList(ActivityUtils.getTopActivity(), true);
                dialogList.setTitle(Utils.getString(R.string.select_category));
                dialogList.clearAndPut(listApi.getData());
                dialogList.setListener(new ItemClickListener<ListItem>() {
                    @Override
                    public void onClick(ListItem item) {
                        filter.categoryId = item.getItemId();
                        filter.categoryName = item.getItemName();
                        categoryName.setText(item.getItemName());
                        Preference.setCurrentCategoryId(item.getItemId());
                    }
                });
                dialogList.show();
            }

            @Override
            public void onFail(RequestException e) {
                super.onFail(e);
                hideLoading();
            }
        });


    }

    private void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    private void hideLoading() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    private void changeChecked(boolean isChecked) {
        if (isChecked) {
            seekbar_distance.setVisibility(View.VISIBLE);
            seekbar_distance.setValue(500);
            filter.distance = 500;
        } else {
            seekbar_distance.setVisibility(View.INVISIBLE);
            txt_distance.setText(Utils.getString(R.string.unlimit));
            filter.distance = -1;
        }
    }

    public static class Filter {
        public int distance = -1;
        public String cityId = null;
        public String cityName = null;
        public String categoryId = null;
        public String categoryName = null;
        public String countryId = null;
        public String countryName = null;
    }

    public interface FilterListener {
        void onFilter(Filter filter);
    }

}
