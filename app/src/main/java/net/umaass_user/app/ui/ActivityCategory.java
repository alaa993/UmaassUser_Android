package net.umaass_user.app.ui;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.URLUtil;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;

import net.umaass_user.app.R;
import net.umaass_user.app.application.Preference;
import net.umaass_user.app.data.Repository;
import net.umaass_user.app.data.interfaces.CallBack;
import net.umaass_user.app.data.remote.models.Api;
import net.umaass_user.app.data.remote.models.Category;
import net.umaass_user.app.data.remote.models.Slide;
import net.umaass_user.app.data.remote.utils.RequestException;
import net.umaass_user.app.interfac.ItemClickListener;
import net.umaass_user.app.ui.adapter.AdapterCategory2;
import net.umaass_user.app.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;


public class ActivityCategory extends BaseActivity {

    RecyclerView recyclerView;
    AdapterCategory2 adapterCategory;

    ImageSlider imageSlider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        readView();
        functionView();
        initViewModel();
        getData();
        getSlides();
    }

    @Override
    public void readView() {
        recyclerView = findViewById(R.id.recyclerView);
        imageSlider = findViewById(R.id.imageSlider);
    }

    @Override
    public void functionView() {
        Preference.setCurrentCategoryId("");
        adapterCategory = new AdapterCategory2(this);
        adapterCategory.setListener(new ItemClickListener<Category>() {
            @Override
            public void onClick(Category item) {
                Preference.setCurrentCategoryId(item.getId());
                startActivity(new Intent(ActivityCategory.this, ActivityMain.class));
                // finish();
            }
        });
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapterCategory);
        recyclerView.setNestedScrollingEnabled(false);

        /*AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);*/


    }


    private void getData() {
        showLoading();
        Repository.getInstance().getAllCategories(Preference.getLanguage(), new CallBack<Api<List<Category>>>() {
            @Override
            public void onSuccess(Api<List<Category>> listApi) {
                super.onSuccess(listApi);
                hideLoading();
                adapterCategory.clearAndPut(listApi.getData());
            }

            @Override
            public void onFail(RequestException e) {
                super.onFail(e);
                hideLoading();
            }
        });

    }

    List<Slide> slideList;

    private void getSlides() {
        Repository.getInstance().getSlider(new CallBack<Api<List<Slide>>>() {
            @Override
            public void onSuccess(Api<List<Slide>> listApi) {
                super.onSuccess(listApi);
                if (listApi != null && listApi.getData() != null) {
                    List<SlideModel> slideModels = new ArrayList<>();
                    slideList = listApi.getData();
                    for (Slide s : listApi.getData()) {
                        slideModels.add(new SlideModel(s.getImage().getUrlMd()));
                    }
                    imageSlider.setImageList(slideModels, false);
                    imageSlider.setItemClickListener(new com.denzcoskun.imageslider.interfaces.ItemClickListener() {
                        @Override
                        public void onItemSelected(int i) {
                            try {
                                String url = slideList.get(i).getUrl();
                                if (url != null && URLUtil.isNetworkUrl(url)) {
                                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    });
                    imageSlider.startSliding(2000);
                }

            }

            @Override
            public void onFail(RequestException e) {
                super.onFail(e);
            }
        });
    }


}
