package net.umaass_user.app.ui.dialog;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import net.umaass_user.app.R;


import net.umaass_user.app.utils.Utils;
import net.umaass_user.app.utils.LayoutHelper;
import net.umaass_user.app.data.remote.models.City;
import net.umaass_user.app.ui.base.BaseDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


public class CityDialog extends BaseDialog {

    private RecyclerView rv_citys;
    List<City> cityList = new ArrayList<>();
    public Stack<List<City>> cityListStack = new Stack<>();
    public Stack<Integer> lastCityIdStack = new Stack<>();
    CityDialogAdapter cityDialogAdapter = new CityDialogAdapter();



    @Override
    public int getViewLayout() {
        return R.layout.dialog_city;
    }

    @Override
    public void readView() {
        super.readView();
        rv_citys = baseView.findViewById(R.id.rv_citys);
    }

    @Override
    public void functionView() {
        super.functionView();
        rv_citys.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_citys.setAdapter(cityDialogAdapter);
    }

    @Override
    public void initViewModel() {
        getDataFromServer();
    }


    private void removeItem() {
        if (cityListStack.size() > 0) {
            lastCityIdStack.pop();
            cityList.clear();
            cityList.addAll(cityListStack.pop());
            cityDialogAdapter.notifyDataSetChanged();
        }
    }


    private void getDataFromServer() {

    }


    public class CityDialogAdapter extends RecyclerView.Adapter<CityDialogAdapter.MyViewHolder> {


        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            AppCompatTextView textView = new AppCompatTextView(getContext());
            textView.setLayoutParams(LayoutHelper.createLinear(LayoutHelper.MATCH_PARENT,
                    LayoutHelper.WRAP_CONTENT, Gravity.RIGHT));
            textView.setPadding(Utils.dp(3), Utils.dp(5), Utils.dp(3), Utils.dp(5));
            textView.setTextColor(getResources().getColor(R.color.secondaryPrimaryText));
            textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
            return new CityDialogAdapter.MyViewHolder(textView);
        }

        @Override
        public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
            holder.bind(cityList.get(position), position);

        }

        @Override
        public int getItemCount() {
            return cityList.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            City item;
            AppCompatTextView textView;

            MyViewHolder(View view) {
                super(view);
                textView = (AppCompatTextView) view;
            }

            void bind(City item, final int position) {
                this.item = item;
                textView.setText(item.name);
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        lastCityIdStack.add(cityList.get(position).id);
                        List<City> temp_categories = new ArrayList<>(cityList);
                        cityListStack.push(temp_categories);
                        if (temp_categories.get(position).children != null) {
                            cityList.clear();
                            cityList.addAll(temp_categories.get(position).children);
                            cityDialogAdapter.notifyDataSetChanged();
                        } else {
                            btnPoss.performClick();
                        }
                    }
                });
            }

            public City getItem() {
                return item;
            }

        }

    }
}

