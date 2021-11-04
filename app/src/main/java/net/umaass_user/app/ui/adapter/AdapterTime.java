package net.umaass_user.app.ui.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.umaass_user.app.R;
import net.umaass_user.app.data.remote.models.Category;
import net.umaass_user.app.interfac.ItemClickListener;

import java.util.ArrayList;
import java.util.List;


public class AdapterTime extends RecyclerView.Adapter<AdapterTime.MyViewHolder> {

    private Context mContext;
    private List<Category> lists = new ArrayList<>();
    private ItemClickListener<Category> listener;
    private int lastSelectedPosition;
    private boolean choosingMode;

    public AdapterTime() {
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_time, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        //  final Category item = lists.get(position);
        //  holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return /*lists == null ? 0 : lists.size()*/10;
    }

    public void clearAndPut(List<Category> items) {
        lists.clear();
        if (items != null) {
            lists.addAll(items);
        }

        notifyDataSetChanged();
    }

    public void put(List<Category> items) {
        if (items != null) {
            lists.addAll(items);
        }
        notifyDataSetChanged();
    }

    public void enableChoosingMode() {
        choosingMode = true;
    }

    public Category getItem(int position) {
        return lists.get(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        Category item;

        MyViewHolder(View view) {
            super(view);
        }

        void bind(Category i) {
            item = i;
            /*CategoryView categoryView = (CategoryView) itemView;
            categoryView.setText(i.getName());
            categoryView.setTextSizeDp(12);

            if (choosingMode){
                if (i.isSelected()){
                    categoryView.setCellColor(R.color.colorPrimaryLight);
                    lastSelectedPosition =  lists.indexOf(i);
                    categoryView.setTextColor(Utils.getColor(R.color.white));
                }else{
                    categoryView.setCellColor(R.color.divider_color);
                    categoryView.setTextColor(Utils.getColor(R.color.colorPrimaryText));
                }
            }else {
                categoryView.setCellColor(R.color.colorPrimaryLight);
                categoryView.setTextColor(Utils.getColor(R.color.white));
            }*/
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (listener != null) {
                listener.onClick(item);

               /* if (choosingMode) {
                    lists.get(lastSelectedPosition).setSelected(false);
                    notifyItemChanged(lastSelectedPosition);

                    lastSelectedPosition = lists.indexOf(item);
                    lists.get(lastSelectedPosition).setSelected(true);
                    notifyItemChanged(lastSelectedPosition);
                }*/


            }
        }
    }


}