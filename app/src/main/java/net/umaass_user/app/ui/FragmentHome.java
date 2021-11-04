package net.umaass_user.app.ui;

import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import net.umaass_user.app.R;
import net.umaass_user.app.application.G;
import net.umaass_user.app.application.Preference;
import net.umaass_user.app.data.Repository;
import net.umaass_user.app.data.interfaces.CallBack;
import net.umaass_user.app.data.remote.models.Api;
import net.umaass_user.app.data.remote.models.Category;
import net.umaass_user.app.data.remote.models.DefualtResponse;
import net.umaass_user.app.data.remote.models.LastDoneAppt;
import net.umaass_user.app.data.remote.models.Profile;
import net.umaass_user.app.data.remote.models.Provider;
import net.umaass_user.app.data.remote.utils.RequestException;
import net.umaass_user.app.interfac.ItemClickListener;
import net.umaass_user.app.ui.adapter.AdapterCategory;
import net.umaass_user.app.ui.adapter.AdapterDoctor;
import net.umaass_user.app.ui.base.BaseFragment;
import net.umaass_user.app.ui.components.EmptyView;
import net.umaass_user.app.ui.components.RoundCornerButton;
import net.umaass_user.app.ui.dialog.DialogRate;
import net.umaass_user.app.ui.dialog.SearchDialog;
import net.umaass_user.app.ui.login.Authentication;
import net.umaass_user.app.utils.EndlessRecyclerOnScrollListener;
import net.umaass_user.app.utils.NotificationCenter;
import net.umaass_user.app.utils.Utils;
import net.umaass_user.app.utils.permission.ActivityUtils;

import java.util.List;

public class FragmentHome extends BaseFragment implements NotificationCenter.NotificationCenterDelegate {

    private AdapterDoctor adapterDoctor;
    private AdapterCategory adapterCategory;
    private RecyclerView recyclerView;
    private RecyclerView recyclerViewCategory;
    private Toolbar toolbar;
    private EmptyView emptyView;
    private SwipeRefreshLayout swipe;
    private SearchView searchView;
    private RoundCornerButton btnFilter;
    private String categoryId = "";
    private String searchText = "";
    private SearchDialog.Filter filter;
    SearchDialog searchDialog;



    @Override
    public int getViewLayout() {
        return R.layout.fragment_home;
    }

    @Override
    public void readView() {
        super.readView();
        recyclerView = baseView.findViewById(R.id.recyclerView);
        recyclerViewCategory = baseView.findViewById(R.id.recyclerViewCategory);
        toolbar = baseView.findViewById(R.id.toolbar);
        emptyView = baseView.findViewById(R.id.emptyView);
        swipe = baseView.findViewById(R.id.swipe);
        searchView = baseView.findViewById(R.id.searchView);
        btnFilter = baseView.findViewById(R.id.btnFilter);
    }

    @Override
    public void functionView() {
        super.functionView();
        searchDialog = new SearchDialog();
        filter = new SearchDialog.Filter();
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                searchView.setBackgroundColor(Color.TRANSPARENT);
                return false;
            }
        });
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchView.setBackgroundColor(Utils.getColor(R.color.toolbar_color));
            }
        });
        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchDialog searchDialog = new SearchDialog();
                searchDialog.setFilter(filter);
                searchDialog.setFilterListener(new SearchDialog.FilterListener() {
                    @Override
                    public void onFilter(SearchDialog.Filter f) {
                        btnFilter.setIconColorFilter(Utils.getColor(f != null ? R.color.colorAccent : R.color.transparent));
                        if (f == null) {
                            filter.countryId = null;
                            filter.cityId = null;
                            filter.categoryId = null;
                            filter.countryName = null;
                            filter.cityName = null;
                            filter.categoryName = null;
                            Preference.setCurrentCategoryId("");
                        }
                        if (filter != null && filter.distance > 0) {
                            swipe.setRefreshing(true);
                            NotificationCenter.getInstance().postNotificationName(NotificationCenter.StartLocation);
                        } else {
                            NotificationCenter.getInstance().postNotificationName(NotificationCenter.StopLocation);
                            getData(1);
                        }
                    }
                });
                searchDialog.show(getChildFragmentManager(), null);
            }
        });
        adapterDoctor = new AdapterDoctor();
        adapterCategory = new AdapterCategory();
        adapterCategory.enableChoosingMode();

        adapterDoctor.setListener(new ItemClickListener<Provider>() {
            @Override
            public void onClick(Provider item) {
                Intent intent = new Intent(getActivity(), ActivityProviderDetail.class);
                intent.putExtra("id", String.valueOf(item.getId()));
                startActivity(intent);
            }
        });
        adapterDoctor.setBookListener(new ItemClickListener<Provider>() {
            @Override
            public void onClick(Provider item) {
                if (Preference.isLogin()) {

                    /*Intent intent = new Intent(getActivity(), ActivityBook.class);
                    intent.putExtra("idProvider", String.valueOf(item.getId()));
                    intent.putExtra("idIndustry", String.valueOf(item.getIndustryId()));
                    startActivity(intent);*/
                    Intent intent = new Intent(getActivity(), ActivityProviderDetail.class);
                    intent.putExtra("id", String.valueOf(item.getId()));
                    startActivity(intent);
                } else {
                    startActivity(new Intent(getActivity(), Authentication.class));
                }

            }
        });
        adapterDoctor.setFavListener(new ItemClickListener<Provider>() {
            @Override
            public void onClick(Provider item) {
                changeFav(item);
            }
        });
        LinearLayoutManager doctorManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(doctorManager);
        recyclerView.setAdapter(adapterDoctor);
        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(doctorManager) {
            @Override
            public void onLoadMore(int currentPage) {
                getData(currentPage);
            }
        });
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                G.log("Token", "Bearer " + Preference.getToken());
                getCategory();
                getData(1);
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        recyclerViewCategory.setLayoutManager(linearLayoutManager);
        recyclerViewCategory.setAdapter(adapterCategory);
        adapterCategory.setListener(new ItemClickListener<Category>() {
            @Override
            public void onClick(Category item) {
                categoryId = item.getItemId();
                Preference.setCurrentCategoryId(categoryId);
                getData(1);
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchText = newText == null ? "" : newText;
                getData(1);
                return false;
            }
        });
        getData(1);
        getCategory();

    }

    private void changeFav(Provider item) {
        CallBack<Api<DefualtResponse>> apiCallBack = new CallBack<Api<DefualtResponse>>() {
            @Override
            public void onSuccess(Api<DefualtResponse> defualtResponseApi) {
                super.onSuccess(defualtResponseApi);
                if (item.getIsFavorited() == 0) {
                    item.setIsFavorited(1);
                } else {
                    item.setIsFavorited(0);
                }
                G.changeFav = true;
                adapterDoctor.updateItem(item);
            }

            @Override
            public void onFail(RequestException e) {
                super.onFail(e);
            }
        };
        if (item.getIsFavorited() == 0) {
            Repository.getInstance().favProvider(String.valueOf(item.getId()), apiCallBack);
        } else {
            Repository.getInstance().unFavProvider(String.valueOf(item.getId()), apiCallBack);
        }
    }

    private void getData(int page) {
        if (swipe != null && page == 1) {
            adapterDoctor.clearAndPut(null);
            swipe.setRefreshing(true);
        }
        emptyView.setVisibility(View.INVISIBLE);
        String distance = filter == null || filter.distance == -1 ? null : String.valueOf(filter.distance);
        String cityId = filter.cityId == null ? null : String.valueOf(filter.cityId);
        String countryId = filter.countryId == null ? null : String.valueOf(filter.countryId);
        Repository.getInstance().getAllProvider(Preference.getCurrentCategoryId(), "",
                                                location == null ? null : String.valueOf(location.getLatitude()),
                                                location == null ? null : String.valueOf(location.getLongitude()),
                                                distance,
                                                cityId,
                                                countryId,
                                                searchText,
                                                page, new CallBack<Api<List<Provider>>>() {
                    @Override
                    public void onSuccess(Api<List<Provider>> listApi) {
                        super.onSuccess(listApi);
                        swipe.setRefreshing(false);
                        if (listApi.getData() != null && listApi.getData().size() > 0) {
                            adapterDoctor.put(listApi.getData());
                        }
                        Utils.runOnUIThread(new Runnable() {
                            @Override
                            public void run() {
                                if (adapterDoctor != null && adapterDoctor.getItemCount() == 0) {
                                    emptyView.setVisibility(View.VISIBLE);
                                }
                            }
                        }, 500);
                    }

                    @Override
                    public void onFail(RequestException e) {
                        super.onFail(e);
                        swipe.setRefreshing(false);

                    }
                });
    }

    private void getCategory() {
        if (adapterCategory.getItemCount() > 0) {
            return;
        }
        Repository.getInstance().getAllCategories(Preference.getLanguage(),new CallBack<Api<List<Category>>>() {
            @Override
            public void onSuccess(Api<List<Category>> listApi) {
                super.onSuccess(listApi);
                if (Preference.getCurrentCategoryId() != null && !Preference.getCurrentCategoryId().equals("")) {
                    for (int i = 0; i < listApi.getData().size(); i++) {
                        Category category = listApi.getData().get(i);
                        if (category.getId().equals(Preference.getCurrentCategoryId())) {
                            categoryId = Preference.getCurrentCategoryId();
                            adapterCategory.setLastSelectedPosition(i);
                            category.setSelected(true);
                        } else {
                            category.setSelected(false);
                        }
                    }
                }
                adapterCategory.clearAndPut(listApi.getData(), categoryId.equals(""));
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.ChangeLocation);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.StopLocation);
        checkLastRate();
    }

    @Override
    public void onPause() {
        super.onPause();
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.ChangeLocation);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.StopLocation);
    }

    @Override
    public void onPop() {
        super.onPop();
        if (G.changeFav) {
            G.changeFav = false;
            getData(1);
        }

    }

    private Location location;


    private void checkLastRate() {
        Repository.getInstance().getProfile(new CallBack<Api<Profile>>() {
            @Override
            public void onSuccess(Api<Profile> profileApi) {
                super.onSuccess(profileApi);
                if (profileApi != null && profileApi.getData() != null) {
                    LastDoneAppt appt = profileApi.getData().getLastDoneAppt();
                    if (appt != null && appt.getUserCommentingStatus() != null) {
                        if (appt.getUserCommentingStatus().equals("no-comment")) {
                            DialogRate dialogRate = new DialogRate(ActivityUtils.getTopActivity(), appt);
                            dialogRate.show();
                        }
                    }
                }
            }
        });
    }

    @Override
    public void didReceivedNotification(int id, Object... args) {
        if (id == NotificationCenter.ChangeLocation) {
            location = (Location) args[0];
            G.log("Location", "location :" + location.getLatitude() + " - " + location.getLongitude());
            getData(1);
        } else if (id == NotificationCenter.StopLocation) {
            if (filter != null) {
                filter.distance = -1;
            }
            if (swipe != null) {
                swipe.setRefreshing(false);
            }
        }

    }
}
