package net.umaass_user.app.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import net.umaass_user.app.R;
import net.umaass_user.app.application.G;
import net.umaass_user.app.application.Preference;
import net.umaass_user.app.data.Repository;
import net.umaass_user.app.data.interfaces.CallBack;
import net.umaass_user.app.data.remote.models.Api;
import net.umaass_user.app.data.remote.models.Avatar;
import net.umaass_user.app.data.remote.models.GalleryItem;
import net.umaass_user.app.data.remote.models.ProviderDetail;
import net.umaass_user.app.data.remote.utils.RequestException;
import net.umaass_user.app.interfac.ItemClickListener;
import net.umaass_user.app.ui.adapter.AdapterGallery;
import net.umaass_user.app.ui.adapter.AdapterService;
import net.umaass_user.app.ui.base.BaseActivity;
import net.umaass_user.app.ui.components.RoundCornerButton;
import net.umaass_user.app.utils.CircleImageView;
import net.umaass_user.app.utils.Utils;

import java.util.ArrayList;

public class ActivityProviderDetail extends BaseActivity {

    RoundCornerButton btnBook;
    TextView txtDrName;
    AppCompatRatingBar ratingBar;
    TextView txtExpertise;
    TextView txtGallery;
    TextView txtPhone;
    TextView txtAddress;
    TextView txtDesc;
    TextView txtDescTitle;
    TextView btnWork;
    TextView txtBio;
    TextView btnComments;
    TextView btnGallery;
    TextView btnAddress;
    CircleImageView imgProfile;
    RecyclerView recyclerViewGallery;
    RecyclerView recyclerViewService;
    String id;
    private AdapterService adapterService;
    private AdapterGallery adapterGallgery;

    double lat = 0;
    double log = 0;
    String name ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_detail);
        id = getIntent().getStringExtra("id");
        readView();
        functionView();
        initViewModel();
    }

    @Override
    public void readView() {
        super.readView();
        btnBook = findViewById(R.id.btnBook);
        ratingBar = findViewById(R.id.ratingBar);
        txtDrName = findViewById(R.id.txtDrName);
        txtExpertise = findViewById(R.id.txtExpertise);
        txtPhone = findViewById(R.id.txtPhone);
        imgProfile = findViewById(R.id.imgProfile);
        btnGallery = findViewById(R.id.btnGallery);
        btnAddress = findViewById(R.id.btnAddress);
        txtAddress = findViewById(R.id.txtAddress);
        txtDesc = findViewById(R.id.txtDesc);
        txtGallery = findViewById(R.id.txtGallery);
        txtDescTitle = findViewById(R.id.txtDescTitle);
        btnWork = findViewById(R.id.btnWork);
        txtBio = findViewById(R.id.txtBio);
        btnComments = findViewById(R.id.btnComments);
        recyclerViewGallery = findViewById(R.id.recyclerViewGallery);
        recyclerViewService = findViewById(R.id.recyclerViewService);


        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    @Override
    public void functionView() {
        super.functionView();

        adapterService = new AdapterService();
        LinearLayoutManager linearLayoutManagerService = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        recyclerViewService.setLayoutManager(linearLayoutManagerService);
        recyclerViewService.setAdapter(adapterService);

        adapterGallgery = new AdapterGallery();
        adapterGallgery.setListener(new ItemClickListener<GalleryItem>() {
            @Override
            public void onClick(GalleryItem item) {
                if (detail != null) {
                    Intent intent = new Intent(ActivityProviderDetail.this, ActivityShowImage.class);
                    ArrayList<String> images = new ArrayList<>();
                    for (GalleryItem galleryItem : detail.getIndustry().getGallery()) {
                        images.add(galleryItem.getUrlMd());
                    }
                    intent.putExtra("list", images);
                    intent.putExtra("index", images.indexOf(item.getUrlMd()));
                    startActivity(intent);
                }

            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        recyclerViewGallery.setLayoutManager(linearLayoutManager);
        recyclerViewGallery.setAdapter(adapterGallgery);

        btnComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityProviderDetail.this, ActivityComments.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });

        btnAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (detail != null) {
                    Intent intent = new Intent(ActivityProviderDetail.this, ActivityMap.class);
                    intent.putExtra("lat", lat);
                    intent.putExtra("log", log);
                    intent.putExtra("name", name);
                    intent.putExtra("address", detail.getIndustry().getAddress());
                    startActivity(intent);
                }

            }
        });
        btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (detail != null) {
                    Intent intent = new Intent(ActivityProviderDetail.this, ActivityShowImage.class);
                    ArrayList<String> images = new ArrayList<>();
                    for (GalleryItem galleryItem : detail.getIndustry().getGallery()) {
                        images.add(galleryItem.getUrlMd());
                    }
                    intent.putExtra("list", images);
                    intent.putExtra("index", 0);
                    startActivity(intent);
                }
            }
        });
        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (detail != null) {
                    Intent intent = new Intent(ActivityProviderDetail.this, ActivityBook.class);
                    intent.putExtra("idProvider", id);
                    intent.putExtra("idIndustry", String.valueOf(detail.getIndustry().getId()));
                    startActivity(intent);
                }
            }
        });
        btnWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (detail != null) {
                    Intent intent = new Intent(ActivityProviderDetail.this, ActivityProviderWorkingHours.class);
                    intent.putExtra("id", String.valueOf(detail.getIndustry().getId()));
                    startActivity(intent);
                }

            }
        });

        getDate();

        txtPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + txtPhone.getText()));
                startActivity(intent);
            }
        });

    }

    private GoogleMap googleMap;

    private void initilizeMap() {
        if (googleMap == null) {
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            if (mapFragment != null) {
                mapFragment.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(GoogleMap googleMap) {
                        ActivityProviderDetail.this.googleMap = googleMap;
                        LatLng hyderadbad = new LatLng(lat, log);
                        googleMap.addMarker(new MarkerOptions().position(hyderadbad).title("Location"));
                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(hyderadbad, 15));
                        googleMap.getUiSettings().setScrollGesturesEnabled(false);
                        googleMap.getUiSettings().setRotateGesturesEnabled(false);
                        googleMap.getUiSettings().setAllGesturesEnabled(false);
                        googleMap.getUiSettings().setScrollGesturesEnabledDuringRotateOrZoom(false);
                        googleMap.getUiSettings().setMapToolbarEnabled(false);
                        googleMap.getUiSettings().setMyLocationButtonEnabled(false);
                        googleMap.getUiSettings().setZoomControlsEnabled(false);
                        googleMap.getUiSettings().setZoomGesturesEnabled(false);
                        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                            @Override
                            public boolean onMarkerClick(Marker marker) {
                                Intent intent = new Intent(ActivityProviderDetail.this, ActivityMap.class);
                                intent.putExtra("lat", lat);
                                intent.putExtra("log", log);
                                startActivity(intent);
                                return false;
                            }
                        });
                        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                            @Override
                            public void onMapClick(LatLng latLng) {
                                Intent intent = new Intent(ActivityProviderDetail.this, ActivityMap.class);
                                intent.putExtra("lat", lat);
                                intent.putExtra("log", log);
                                startActivity(intent);
                            }
                        });
                    }
                });
            }

        }
    }

    ProviderDetail detail;

    private void getDate() {
        showLoading();
        Repository.getInstance().getProviderDetail(id, new CallBack<Api<ProviderDetail>>() {
            @Override
            public void onSuccess(Api<ProviderDetail> providerDetailApi) {
                super.onSuccess(providerDetailApi);
                hideLoading();
                if (providerDetailApi != null) {
                    detail = providerDetailApi.getData();
                    lat = detail.getIndustry().getLat();
                    log = detail.getIndustry().getLng();
                    name = detail.getName();
                    txtDrName.setText(detail.getName());
                    txtBio.setText(detail.getIndustry().getDescription());
                    txtExpertise.setText(detail.getIndustry().getTitle());
                    txtPhone.setText(detail.getIndustry().getPhone());
                    ratingBar.setRating(detail.getRate());

                    Avatar avatar = detail.getAvatar();
                    if (avatar != null) {
                        Preference.setImage(avatar.getUrlMd());
                        Picasso.get()
                               .load(avatar.getUrlMd())
                               .placeholder(R.drawable.profile)
                               .error(R.drawable.profile)
                               .into(imgProfile);
                    }
                    txtAddress.setText(detail.getIndustry().getAddress());
                    adapterService.put(detail.getServices());
                    //  adapterGallgery.put(detail.getIndustry().getGallery());
                    txtGallery.setVisibility(adapterGallgery.getItemCount() == 0 ? View.GONE : View.VISIBLE);
                    boolean b = detail.getDesciption() == null || detail.getDesciption().isEmpty();
                    txtDesc.setVisibility(b ? View.GONE : View.VISIBLE);
                    txtDescTitle.setVisibility(b ? View.GONE : View.VISIBLE);
                    // initilizeMap();
                }

            }

            @Override
            public void onFail(RequestException e) {
                super.onFail(e);
                hideLoading();
                G.toast(Utils.getString(R.string.try_again));
                finish();
            }
        });
    }


    private void locationShow(double lat, double log) {

        StringBuilder builder = new StringBuilder();
        builder.append("https://maps.googleapis.com/maps/api/staticmap?center=");
        builder.append(lat);
        builder.append(",");
        builder.append(log);
        builder.append("&zoom=6&size=400x400&maptype=roadmap");
        builder.append("&markers=color:blue%7Clabel:S%7C");
        builder.append(lat);
        builder.append(",");
        builder.append(log);
        builder.append("&key=");
        builder.append(Utils.getString(R.string.map_token));

        String u = "https://maps.googleapis.com/maps/api/staticmap?center=Brooklyn+Bridge,New+York,NY&zoom=13&size=600x300&maptype=roadmap\n" +
                   "&markers=color:blue%7Clabel:S%7C40.702147,-74.015794&markers=color:green%7Clabel:G%7C40.711614,-74.012318\n" +
                   "&markers=color:red%7Clabel:C%7C40.718217,-73.998284\n" +
                   "&key=" + Utils.getString(R.string.map_token);

        G.log("map", builder.toString());
      /*  Picasso.get().load(builder.toString())
               .into(imgMap);*/
    }

}
